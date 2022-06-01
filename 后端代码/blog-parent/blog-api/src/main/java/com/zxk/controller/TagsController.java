package com.zxk.controller;

import com.zxk.service.TagService;
import com.zxk.vo.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 闫柯含
 * @date 2021年 12月 31日 下午 7:51
 * @功能说明: =>标签controller
 */
@RestController
@RequestMapping("tags")
@AllArgsConstructor
public class TagsController {

    private final TagService tagService;

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable Long id){
        return tagService.findDetailById(id);
    }

    /**
     * 热门标签
     * @return
     */
    @GetMapping("/hot")
    public Result hot(){
        int limit=6;
        return tagService.hot(limit);
    }


}
