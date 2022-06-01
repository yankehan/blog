package com.zxk.service;

import com.zxk.vo.params.CommentParam;
import com.zxk.vo.result.Result;

/**
 * @author 闫柯含
 * @date 2022年 01月 30日 下午 5:42
 * @功能说明: =>文章评论
 */
public interface CommentsService {
    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
