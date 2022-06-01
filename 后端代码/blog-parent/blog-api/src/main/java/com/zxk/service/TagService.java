package com.zxk.service;

import com.zxk.vo.result.Result;
import com.zxk.vo.result.TagVo;

import java.util.List;

/**
 * @author 闫柯含
 * @date 2021年 12月 30日 下午 9:44
 * @功能说明: =>标签service
 */
public interface TagService {
    /**
     * 通过文章获取标签（一个文章含有多个标签）
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hot(Integer limit);

    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
