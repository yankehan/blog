package com.zxk.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;

/**
 * @author 闫柯含
 * @date 2021/11/1 0001 下午 9:16
 * @功能说明：swagger的配置类
 */
@Configuration
@EnableSwagger2   //开启swagger，最低配开启swagger功能
public class SwaggerConfig {


    //配置了swagger的Docket的bean实例
    //  Swagger在加载的时候会把Docket信息加载到swagger-ui.html上
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("闫柯含") //配置组名
                                        //每个人的组名都是不同的，并且负责分工不同
                                        //因此，需要为每个人设置组名。
                                        //每个Docket对象只有一个组名
                                        //所以，多个组名意味着多个Docket对象注入到spring容器中
                .enable(true) //能否在浏览器访问到,默认是true,发布的时候设置成false
                              //(常用)使用一个变量，只允许dev环境开启
                              //如果当前是开发环境，设置swagger打开。
                .select()
                          //.apis 配置扫描哪些接口
                                //.basePackage 扫描哪些包(常用)
                                //.any 扫描所有接口
                                //.none 不扫描所有接口
                                //.withClassAnnotation() 扫描类上带有该注解的接口
                                //.withMethodAnnotation 扫描方法上带有该注解的接口
                    .apis(RequestHandlerSelectors.basePackage("com.zxk.controller"))
                          //paths 配置过滤规则，那些接口能显示
                                //.ant 指定url的可以显示(常用)
                                //.any 所有的都可以显示
                                //.none 所有的都不可以显示
                                //.regex 指定正则表达式的可以显示
                    //.paths(PathSelectors.ant("/zxk/**"))
                    .build()
                .apiInfo(apiInfo());
    }

    //配置ApiInfo，但是这个ApiInfo是作为
    //  docket的一个成员变量
    //  随着docket注入到spring中生效的，所以这里的apiInfo不需要@Bean
    /**
     * swagger-ui.html 封面信息
     */
    private ApiInfo apiInfo(){
        Contact contact = new Contact("扎心柯", "https://leetcode-cn.com/u/zhaxinke/", "2076294264@qq.com");
        return new ApiInfo(
                "扎心柯API文档", //标题
                "Speak is cheap,show me the code", //描述
                "version 1.0", //版本号
                "https://leetcode-cn.com/u/zhaxinke/",//组织网站
                //作者信息(姓名、地址、邮件)
                contact,
                //开源协议(不用动)
                "Apache 2.0",
                //开源协议地址(不用动)
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

    /*
    * 梳理swagger配置流程
    *   swagger是通过docket对象进行页面信息配置的
    *   我们具体怎么配置的呢？
    *       首先，我们通过@Bean注解，将Docket对象注入到sprig容器中
    *       然后，spring容器中就有Docket对象了
    *       最后，swagger加载的时候，从容器中拿到Docket对象，获取配置信息，加载到页面上
    *   相当于，约定好往spring中放一个Docket对象，你存我取
     */
}