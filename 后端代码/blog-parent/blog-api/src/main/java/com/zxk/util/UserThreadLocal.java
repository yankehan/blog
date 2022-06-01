package com.zxk.util;

import com.zxk.pojo.SysUser;

/**
 * @author 闫柯含
 * @date 2022年 01月 29日 下午 8:43
 * @功能说明: => 用于保存用户信息的 ThreadLocal
 * 1. 在拦截器中的 preHandler 中放入
 * 2. 在拦截器中的 afterCompletion 中移除
 * => 一定要移除，否则会有内存泄露的风险
 */
public class UserThreadLocal {

    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    private UserThreadLocal() {
    }

    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    public static SysUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
