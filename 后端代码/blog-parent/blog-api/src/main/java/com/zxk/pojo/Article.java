package com.zxk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 10:45
 * @功能说明: =>文章
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Article {
    private Long id;
    private String title;
    private String summary;
    private Integer commentCounts;
    private Integer viewCounts;
    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     * 类别id
     */
    private Long categoryId;
    /**
     * 置顶
     * 设置了一些常量标识符
     */
    private Integer weight;

    //置顶
    public static final int Article_TOP = 1;
    //不置顶
    public static final int Article_Common = 0;

    /**
     * 创建时间
     */
    private Long createDate;
}
