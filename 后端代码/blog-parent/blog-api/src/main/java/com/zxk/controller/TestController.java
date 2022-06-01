package com.zxk.controller;

import com.zxk.util.UserThreadLocal;
import com.zxk.vo.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 闫柯含
 * @date 2022年 01月 29日 下午 6:24
 * @功能说明: => 测试
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        return Result.success(UserThreadLocal.get());
    }
}
