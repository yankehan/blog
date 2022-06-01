package com.zxk.service;

import com.zxk.pojo.SysUser;
import com.zxk.vo.params.RegisterParam;
import com.zxk.vo.result.Result;
import com.zxk.vo.params.LoginParam;

/**
 * @author 闫柯含
 * @date 2022年 01月 02日 下午 8:03
 * @功能说明: =>登录
 */
public interface LoginService {
    /**
     * 用户登录
     * 根据账号密码验证用户身份
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 检测token的正确性
     * @param token
     * @return 如果正确返回SysUser对象，否则返回null
     */
    SysUser checkToken(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result loginOut(String token);

    /**
     * 用户注册
     * @param registerParam
     * @return
     */
    Result register(RegisterParam registerParam);
}
