package com.zxk.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2022年 01月 02日 下午 10:17
 * @功能说明: =>注册参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterParam {
    //用户名
    private String account;
    //密码
    private String password;
    //昵称
    private String nickname;
}
