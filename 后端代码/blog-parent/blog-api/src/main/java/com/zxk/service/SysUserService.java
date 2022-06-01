package com.zxk.service;

import com.zxk.pojo.SysUser;
import com.zxk.vo.result.Result;
import com.zxk.vo.result.UserVo;

/**
 * @author 闫柯含
 * @date 2021年 12月 30日 下午 10:37
 * @功能说明: =>用户service
 */
public interface SysUserService {
    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    SysUser findUserById(Long id);

    /**
     * 根据账号密码进行加密
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Result findUserInfoByToken(String token);

    /**
     * 插入一条用户注册信息
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据用户名查询用户信息
     * @return
     */
    SysUser finUserByAccount(String account);

    /**
     * 根据id查询 用户信息Vo
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);
}
