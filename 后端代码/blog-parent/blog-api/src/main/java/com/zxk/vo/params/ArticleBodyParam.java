package com.zxk.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2022年 02月 05日 下午 5:27
 * @功能说明: =>文章内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleBodyParam {

    private String content; // md格式的文章内容

    private String contentHtml; // html格式的文章内容

}
