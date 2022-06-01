package com.zxk.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 闫柯含
 * @date 2022年 02月 05日 下午 9:59
 * @功能说明: => 获取 HttpServletRequest 对象
 */
public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest() {
        // RequestContextHolder顾名思义,持有上下文的Request容器
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
