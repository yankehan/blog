package com.zxk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 10:48
 * @功能说明: =>用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysUser {

    private Long id;

    private String account;
    /**
     * 是否为管理员
     */
    private Integer admin;
    /**
     * 头像路径
     */
    private String avatar;

    private Long createDate;
    /**
     * 逻辑删除字段
     */
    private Integer deleted;

    private String email;
    /**
     * 最后一次登录时间
     */
    private Long lastLogin;

    private String mobilePhoneNumber;
    /**
     * 昵称
     */
    private String nickname;

    private String password;
    /**
     * 密码加盐
     */
    private String salt;
    /**
     * 状态
     */
    private String status;
}
