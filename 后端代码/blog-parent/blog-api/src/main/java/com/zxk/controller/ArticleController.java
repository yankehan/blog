package com.zxk.controller;

import com.zxk.common.aop.LogAnnotation;
import com.zxk.common.cache.Cache;
import com.zxk.service.ArticleService;
import com.zxk.vo.params.ArticleParam;
import com.zxk.vo.result.Result;
import com.zxk.vo.params.PageParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 10:57
 * @功能说明: =>文章Controller + JSON格式交互
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParam
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "文章", operator = "获取文章列表")
    @Cache(expire = 5 * 60 * 1000, name = "list_article")
    public Result listArticle(@RequestBody PageParam pageParam){
        return articleService.listArticle(pageParam);
    }

    /**
     * 首页 最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticles(){
        int limit=5;
        return articleService.hotArticles(limit);
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticles(){
        int limit=5;
        return articleService.newArticles(limit);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return Result.success(articleService.listArchives());
    }

    /**
     * 首页 文章详情
     * @param id
     * @return
     */
    @Cache(expire = 5 * 60 * 1000, name = "findArticleById")
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){
        return articleService.findArticleById(id);
    }

    /**
     * 首页 文章发布
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }

    /**
     * 文章 编辑文章
     * @param articleParam
     * @return
     */
    @PostMapping("updateArticle")
    public Result updateArticle(@RequestBody ArticleParam articleParam){
        return articleService.update(articleParam);
    }

}
