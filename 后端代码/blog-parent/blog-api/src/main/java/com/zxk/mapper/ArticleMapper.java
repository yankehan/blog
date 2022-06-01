package com.zxk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxk.pojo.Article;
import com.zxk.vo.result.Archives;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 10:53
 * @功能说明: =>文章mapper
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 首页 文章归档
     * @return
     */
    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
