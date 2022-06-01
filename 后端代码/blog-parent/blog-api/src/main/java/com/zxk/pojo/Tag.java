package com.zxk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 10:51
 * @功能说明: =>文章标签(Tag)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Tag {

    private Long id;
    /**
     * 标签头像
     */
    private String avatar;
    /**
     * 标签名
     */
    private String tagName;

}
