package com.zxk.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zxk.pojo.SysUser;
import com.zxk.service.LoginService;
import com.zxk.service.SysUserService;
import com.zxk.util.JWTUtil;
import com.zxk.vo.error_code.ErrorCode;
import com.zxk.vo.params.RegisterParam;
import com.zxk.vo.result.Result;
import com.zxk.vo.params.LoginParam;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 闫柯含
 * @date 2022年 01月 02日 下午 8:05
 * @功能说明: =>
 */
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private SysUserService sysUserService;
    //redis
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 给密码加密使用到的盐
     */
    private static final String slat = "*&^zhaxinke!@#";

    @Override
    public Result login(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (account == null || password == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //md5带盐加密
        password = SecureUtil.md5(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，将token存放在redis并返回token
        String token = JWTUtil.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1L, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StrUtil.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtil.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result loginOut(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(RegisterParam registerParam) {
        String account = registerParam.getAccount();
        String password = registerParam.getPassword();
        String nickname = registerParam.getNickname();
        if (account == null || password == null //校验账号密码有效性
                || account.length() < 5 || account.length() > 12
                || password.length() < 5 || password.length() > 12) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //查找该用户名是否已经注册
        SysUser sysUser = sysUserService.finUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        //到这里就可以执行数据库插入操作
        sysUser = new SysUser();
        sysUser.setAccount(account)
                //密码 加盐加密后存入数据库
                .setPassword(SecureUtil.md5(password + slat))
                .setNickname(nickname)
                .setCreateDate(System.currentTimeMillis())
                .setLastLogin(System.currentTimeMillis())
                .setAvatar("/static/img/logo.b3a48c0.png")
                .setAdmin(1)
                .setDeleted(0)
                .setSalt("")
                .setStatus("")
                .setEmail("");
        //存入数据库
        sysUserService.save(sysUser);
        //创建token，放到redis中，并返回给前端
        String token = JWTUtil.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1L,TimeUnit.DAYS);
        return Result.success(token);
    }
}
