package com.zxk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zxk.pojo.Tag;
import com.zxk.vo.result.TagVo;

import java.util.List;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 10:54
 * @功能说明: =>标签mapper
 */
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 通过文章id获取标签（一个文章含有多个标签）
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热标签id
     * @param limit
     * @return
     * 该标签下拥有的文章最多，则被认为是最热标签
     */
    List<Long> findHotTagsId(Integer limit);

    /**
     * 根据标签id查询 Tag 信息=>转换成TagVo形式
     * @param tagIds
     * @return
     */
    List<TagVo> findTagsByTagsID(List<Long> tagIds);

}
