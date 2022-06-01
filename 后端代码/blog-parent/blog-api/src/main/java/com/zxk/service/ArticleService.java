package com.zxk.service;

import com.zxk.vo.params.ArticleParam;
import com.zxk.vo.result.Result;
import com.zxk.vo.params.PageParam;
import com.zxk.vo.result.Archives;

import java.util.List;

/**
 * @author 闫柯含
 * @date 2021年 12月 30日 下午 8:27
 * @功能说明: =>文章service
 */
public interface ArticleService {
    /**
     * 首页 文章列表
     * @param pageParam
     * @return
     */
    public Result listArticle(PageParam pageParam);

    /**
     * 首页 最热文章
     * @param limit
     * @return
     */
    Result hotArticles(int limit);

    /**
     * 首页 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 首页 文章归档
     * @return
     */
    List<Archives> listArchives();

    /**
     * 首页 文章详情
     * @param id
     * @return
     */
    Result findArticleById(Long id);

    /**
     * 文章发布
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);

    /**
     * 文章 编辑文章
     * @param articleParam
     * @return
     */
    Result update(ArticleParam articleParam);
}
