package com.zxk.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxk.mapper.CommentMapper;
import com.zxk.pojo.Comment;
import com.zxk.pojo.SysUser;
import com.zxk.service.CommentsService;
import com.zxk.service.SysUserService;
import com.zxk.util.UserThreadLocal;
import com.zxk.vo.params.CommentParam;
import com.zxk.vo.result.CommentVo;
import com.zxk.vo.result.Result;
import com.zxk.vo.result.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 闫柯含
 * @date 2022年 01月 30日 下午 5:43
 * @功能说明: =>文章评论
 */
@Service
@AllArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private CommentMapper commentMapper;

    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long articleId) {
        /**
         * 1.从comment表中查询 根据文章id 查评论
         * 2.根据作者id 查询评论人的信息
         * 3.判断 如果level =  1 要去查询它有没有子评论
         * 4.如果有子评论 根据评论id 进行查询（parent_id）
         */
        LambdaQueryWrapper<Comment> queueWrapper = new LambdaQueryWrapper<>();
        queueWrapper.eq(Comment::getArticleId, articleId);
        queueWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queueWrapper);
        List<CommentVo> commentVos = copyList(comments);
        return Result.success(commentVos);
    }

    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment()
                .setArticleId(commentParam.getArticleId())
                .setAuthorId(sysUser.getId())
                .setContent(commentParam.getContent())
                .setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        //时间格式化(将时间戳转换为String展示)
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //发布评论的人的信息
        UserVo userVo = sysUserService.findUserVoById(comment.getAuthorId());
        commentVo.setAuthor(userVo);
        //子评论
        if (comment.getLevel() == 1) {
            List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
            commentVo.setChildrens(commentVoList);
        }
        //给谁评论
        if (comment.getLevel() > 1){
            UserVo toUserVo = sysUserService.findUserVoById(comment.getToUid());
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }
}
