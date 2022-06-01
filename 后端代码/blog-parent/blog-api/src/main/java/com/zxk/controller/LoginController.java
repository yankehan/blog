package com.zxk.controller;

import com.zxk.service.LoginService;
import com.zxk.vo.params.RegisterParam;
import com.zxk.vo.result.Result;
import com.zxk.vo.params.LoginParam;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 闫柯含
 * @date 2022年 01月 02日 下午 7:50
 * @功能说明: =>登录功能
 */
@RestController
@AllArgsConstructor
@RequestMapping("login")
@Transactional //添加事务，防止redis没有启动成功，用户却登录或注册
public class LoginController {

    private LoginService loginService;

    /**
     * 用户登录
     * 根据账号密码验证用户身份
     * @param loginParam
     * @return
     */
    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @GetMapping("/out")
    public Result loginOut(@RequestHeader("Authorization") String token){
        return loginService.loginOut(token);
    }

    /**
     * 注册
     * @param registerParam
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterParam registerParam){
        return loginService.register(registerParam);
    }

}
