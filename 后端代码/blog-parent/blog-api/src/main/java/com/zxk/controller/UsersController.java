package com.zxk.controller;

import com.zxk.service.SysUserService;
import com.zxk.vo.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 闫柯含
 * @date 2022年 01月 02日 下午 8:52
 * @功能说明: =>用户信息
 */
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UsersController {

    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.findUserInfoByToken(token);
    }

}




