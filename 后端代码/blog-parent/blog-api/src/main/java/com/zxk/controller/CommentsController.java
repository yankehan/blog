package com.zxk.controller;

import com.zxk.service.CommentsService;
import com.zxk.vo.params.CommentParam;
import com.zxk.vo.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author 闫柯含
 * @date 2022年 01月 30日 下午 5:40
 * @功能说明: =>文章评论
 */
@RestController
@AllArgsConstructor
@RequestMapping("comments")
public class CommentsController {

    private CommentsService commentsService;

    /**
     * 获取评论列表
     * @param articleId 文章id
     * @return
     */
    @GetMapping("article/{id}")
    public Result commentsByArticleId(@PathVariable("id") Long articleId){
        return commentsService.commentsByArticleId(articleId);
    }

    /**
     * 添加评论
     * @param commentParam
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}
