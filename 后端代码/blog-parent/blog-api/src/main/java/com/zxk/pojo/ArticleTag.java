package com.zxk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2022年 02月 05日 下午 5:49
 * @功能说明: => 文章与标签 对应关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
