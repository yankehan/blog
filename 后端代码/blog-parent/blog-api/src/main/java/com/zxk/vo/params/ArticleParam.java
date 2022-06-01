package com.zxk.vo.params;

import com.zxk.vo.result.CategoryVo;
import com.zxk.vo.result.TagVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 闫柯含
 * @date 2022年 02月 05日 下午 5:25
 * @功能说明: => 文章保存到到数据库中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}
