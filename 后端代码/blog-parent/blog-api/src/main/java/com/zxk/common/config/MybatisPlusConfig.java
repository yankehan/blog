package com.zxk.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 9:32
 * @功能说明: =>MybatisPlus的配置
 */
@Configuration
//扫包，将此包下的接口生成代理实现类，并且注册到spring容器中
//      但是，有些idea不认这个，没有那个点进去的《小叶子》，所以重复在mapper.class上加了@Mapper注解，其实，这一个注解就够了
@MapperScan("com.zxk.mapper")
public class MybatisPlusConfig {

    //使用分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


}
