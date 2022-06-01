package com.zxk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxk.mapper.ArticleBodyMapper;
import com.zxk.mapper.ArticleMapper;
import com.zxk.mapper.ArticleTagMapper;
import com.zxk.pojo.Article;
import com.zxk.pojo.ArticleBody;
import com.zxk.pojo.ArticleTag;
import com.zxk.pojo.SysUser;
import com.zxk.service.*;
import com.zxk.util.UserThreadLocal;
import com.zxk.vo.params.ArticleParam;
import com.zxk.vo.params.PageParam;
import com.zxk.vo.result.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 闫柯含
 * @date 2021年 12月 30日 下午 8:29
 * @功能说明: =>文章service 具体实现
 */
@Slf4j
@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    // RabbitMQ
    private RabbitTemplate rabbitTemplate;

    private ArticleMapper articleMapper;

    private ArticleBodyMapper articleBodyMapper;

    private ArticleTagMapper articleTagMapper;

    private TagService tagService;

    private SysUserService sysUserService;

    private CategoryService categoryService;

    private ThreadService threadService;

    @Override
    public Result listArticle(PageParam pageParam) {
        Page<Article> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        IPage<Article> articleIPage =
                this.articleMapper.listArticle(page
                        , pageParam.getCategoryId()
                        , pageParam.getTagId()
                        , pageParam.getYear()
                        , pageParam.getMonth());
        return Result.success(copyList(articleIPage.getRecords(), true, true));
    }

    /*
    重构人：闫柯含
    重构日期：2022年2月8日
    重构原因：SQL语句较 mybatisPlus 简洁，另外由于文章归档功能需要
             必须要使用到MySql数据库的 FROM_UNIXTIME 函数
     */
//    @Override
//    public Result listArticle(PageParam pageParam) {
//        //分页操作
//        Page<Article> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
//        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
//        if (pageParam.getCategoryId() != null) {
//            query.eq(Article::getCategoryId, pageParam.getCategoryId());
//        }
//        ArrayList<Long> articleIdList = new ArrayList<>();
//        if (pageParam.getTagId() != null) {
//            LambdaQueryWrapper<ArticleTag> articleTagQuery = new LambdaQueryWrapper<>();
//            articleTagQuery.select(ArticleTag::getArticleId);
//            articleTagQuery.eq(ArticleTag::getTagId, pageParam.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagQuery);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() == 0) {
//                return Result.success(null);
//            }
//            query.in(Article::getId, articleIdList);
//        }
//        query.orderByDesc(Article::getWeight, Article::getCreateDate);
//        List<Article> records = articleMapper.selectPage(page, query).getRecords();
//        //得到记录后不能直接返回，需要做一定修改
//        List<ArticleVo> list = copyList(records, true, true);
//        return Result.success(list);
//    }

    @Override
    public Result hotArticles(int limit) {
        QueryWrapper<Article> query = new QueryWrapper<>();
        query.orderByDesc("view_counts");
        query.select("id", "title");
        query.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(query);
        List<Map> list = new ArrayList<>();
        for (Article article : articles) {
            HashMap map = new HashMap();
            map.put("id", article.getId());
            map.put("title", article.getTitle());
            list.add(map);
        }
        return Result.success(list);
    }

    @Override
    public Result newArticles(int limit) {
        QueryWrapper<Article> query = new QueryWrapper<>();
        query.orderByDesc("create_date");
        query.select("id", "title");
        query.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(query);
        List<Map> list = new ArrayList<>();
        for (Article article : articles) {
            HashMap map = new HashMap();
            map.put("id", article.getId());
            map.put("title", article.getTitle());
            list.add(map);
        }
        return Result.success(list);
    }

    @Override
    public List<Archives> listArchives() {
        return articleMapper.listArchives();
    }

    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);
        // 当有用户点击文章详情的时候，阅读数量应该+1
        // 但是这里应该，是一个异步操作，优先返回用户文章内容最重要
        threadService.updateArticleView(article.getId());
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get(); // 该操作必须登陆
        // 构建一个文章对象插入数据库
        Article article = new Article();
        article.setAuthorId(sysUser.getId())
                .setCategoryId(Long.valueOf(articleParam.getCategory().getId()))
                .setCreateDate(System.currentTimeMillis())
                .setCommentCounts(0)
                .setSummary(articleParam.getSummary())
                .setTitle(articleParam.getTitle())
                .setViewCounts(0)
                .setWeight(Article.Article_Common);
        // 执行insert后article对象会有一个文章id与数据库中相同
        this.articleMapper.insert(article);
        //tag表(关联该文章)
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                Long articleId = article.getId();
                ArticleTag articleTag = new ArticleTag()
                        .setArticleId(articleId)
                        .setTagId(Long.valueOf(tag.getId()));
                articleTagMapper.insert(articleTag);
            }
        }
        //body表
        ArticleBody articleBody = new ArticleBody()
                .setArticleId(article.getId())
                .setContent(articleParam.getBody().getContent())
                .setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        articleMapper.updateById(new Article()
                .setId(article.getId())
                .setBodyId(articleBody.getId()));
        //处理返回值
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }

    // 编辑文章
    @Override
    public Result update(ArticleParam articleParam) {
        // 1.修改数据库
        Article article = new Article();
        BeanUtils.copyProperties(articleParam, article);
        articleMapper.updateById(article);
        // 2.发送修改缓存任务到rabbitMQ队列
            // 携带数据：文章id
            // 1)需要让 findArticleById::ArticleController::findArticleById::加密后的文章id 这条缓存失效
            // 2)删除list_article::ArticleController::listArticle::  这个没有参数，不能修改，只能删除
        String articleId = article.getId().toString();
            // 参数1：交换机  参数2：路由key，又叫队列名  参数3：消息内容
        rabbitTemplate.convertAndSend(
                "fanout_blog_article"
                , ""
                , articleId
        );
        // 3.返回结果
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }


    /**
     * 将单个 Article 转化为 ArticleVo
     *
     * @param article
     * @param isAuthor 是否加入作者
     * @param isTags   是否加入标签信息
     * @return
     */
    private ArticleVo copy(Article article, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {
        ArticleVo vo = new ArticleVo();
        vo.setId(article.getId().toString());
        BeanUtils.copyProperties(article, vo);
        if (article.getCreateDate() != null) {
            vo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(article.getCreateDate())));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            SysUser user = sysUserService.findUserById(authorId);
            if (user == null) {
                user = new SysUser().setNickname("");
            }
            vo.setAuthor(user.getNickname());
        }
        if (isTags) {
            Long articleId = article.getId();
            List<TagVo> tags = tagService.findTagsByArticleId(articleId);
            vo.setTags(tags);
        }
        if (isBody) {
            ArticleBodyVo articleBodyVo = findArticleBodyById(article.getBodyId());
            vo.setBody(articleBodyVo);
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            vo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return vo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        return new ArticleBodyVo(articleBody.getContent());
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTags) {
        return copyList(records, isAuthor, isTags, false, false);
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            ArticleVo articleVo = copy(article, isAuthor, isTags, isBody, isCategory);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }
}
