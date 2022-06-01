package com.zxk.common.aop;

import java.lang.annotation.*;

/**
 * @author 闫柯含
 * @date 2022年 02月 05日 下午 9:45
 * @功能说明: =>日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";

    String operator() default "";
}
