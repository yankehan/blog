package com.zxk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2022年 01月 29日 下午 9:53
 * @功能说明: => 文章类别
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
