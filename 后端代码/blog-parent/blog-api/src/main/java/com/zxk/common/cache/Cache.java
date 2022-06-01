package com.zxk.common.cache;

import java.lang.annotation.*;

/**
 * @author 闫柯含
 * @since 2022年 05月 05日 下午 9:05
 * 缓存注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    String name() default ""; // 缓存标识
    long expire() default 60 * 1000; // 缓存失效时间
}
