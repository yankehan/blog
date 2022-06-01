package com.zxk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zxk.mapper.TagMapper;
import com.zxk.pojo.Tag;
import com.zxk.service.TagService;
import com.zxk.vo.result.Result;
import com.zxk.vo.result.TagVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 闫柯含
 * @date 2021年 12月 30日 下午 9:46
 * @功能说明: =>标签service 具体实现
 */
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private TagMapper tagMapper;

    /**
     * 通过文章获取标签（一个文章含有多个标签）
     *
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> list = tagMapper.findTagsByArticleId(articleId);
        return copyList(list);
    }

    /**
     * 首页 最热标签
     * @return
     * 该标签下拥有的文章最多，则被认为是最热标签
     */
    @Override
    public Result hot(Integer limit) {
        List<Long> tagId = tagMapper.findHotTagsId(limit);
        List<TagVo> tagVos = tagMapper.findTagsByTagsID(tagId);
        return Result.success(tagVos);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tags = tagMapper.selectList(null);
        return Result.success(tags);
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(tag);
    }

    /**
     * 将多个Tag 转换为 TagVo
     * @param tags
     * @return
     */
    private List<TagVo> copyList(List<Tag> tags){
        List<TagVo> list = new ArrayList<>();
        for (Tag tag : tags) {
            list.add(copy(tag));
        }
        return list;
    }

    /**
     * 将单个Tag 转换为 TagVo
     */
    private TagVo copy(Tag tag) {
        TagVo vo = new TagVo();
        vo.setId(tag.getId().toString());
        BeanUtils.copyProperties(tag, vo);
        return vo;
    }
}
