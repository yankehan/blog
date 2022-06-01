package com.zxk.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2022年 02月 05日 下午 4:09
 * @功能说明: => 文章评论Vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommentParam {


    private Long articleId;

    private String content;

    private Long parent; // 父评论id

    private Long toUserId; // 给谁回复
}
