package com.zxk.service;

import com.zxk.vo.result.CategoryVo;
import com.zxk.vo.result.Result;


/**
 * @author 闫柯含
 * @date 2022年 01月 29日 下午 10:20
 * @功能说明: => 文章类别Service
 */
public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
