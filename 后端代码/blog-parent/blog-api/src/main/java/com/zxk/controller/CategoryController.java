package com.zxk.controller;

import com.zxk.service.CategoryService;
import com.zxk.vo.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 闫柯含
 * @date 2022年 02月 05日 下午 4:48
 * @功能说明: => 文章类别
 */
@RestController
@AllArgsConstructor
@RequestMapping("categorys")
public class CategoryController {

    private CategoryService categoryService;

    /**
     * 查询所有文章分类（简略版）
     * @return
     */
    @GetMapping
    public Result listCategory(){
        return categoryService.findAll();
    }

    /**
     * 查询所有文章分类（详细版）
     * @return
     */
    @GetMapping("detail")
    public Result categoryAllDetail(){
        return categoryService.findAllDetail();
    }

    /**
     * 根据ID查询某个具体文章分类
     * @param id
     * @return
     */
    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable Long id){
        return categoryService.categoryDetailById(id);
    }

}
