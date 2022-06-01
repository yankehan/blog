package com.zxk.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zxk.mapper.SysUserMapper;
import com.zxk.pojo.SysUser;
import com.zxk.service.SysUserService;
import com.zxk.util.JWTUtil;
import com.zxk.vo.error_code.ErrorCode;
import com.zxk.vo.result.Result;
import com.zxk.vo.result.UserInfoVo;
import com.zxk.vo.result.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 闫柯含
 * @date 2021年 12月 30日 下午 10:39
 * @功能说明: =>用户service 具体实现
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private SysUserMapper sysUserMapper;

    private RedisTemplate<String,String> redisTemplate;

    @Override
    public SysUser findUserById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public SysUser findUser(String account, String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("id","account","avatar","nickname");
        queryWrapper.eq("account",account);
        queryWrapper.eq("password",password);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserInfoByToken(String token) {
        Map<String, Object> checkResult = JWTUtil.checkToken(token);
        if (checkResult==null){ //如果检验失败，返回未登录
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        //检查用户身份是否过期了(是否在redis中)
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (userJson == null){
            return Result.fail(ErrorCode.SESSION_TIME_OUT.getCode(), ErrorCode.SESSION_TIME_OUT.getMsg());
        }
        SysUser sysUser = JSON.parseObject(userJson,SysUser.class);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(sysUser.getId().toString());
        BeanUtil.copyProperties(sysUser,userInfoVo);
        return Result.success(userInfoVo);
    }

    @Override
    public SysUser finUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper();
        query.eq(SysUser::getAccount, account);
        query.last("limit 1");
        return sysUserMapper.selectOne(query);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(sysUser, userVo);
        return userVo;
    }

    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

}
