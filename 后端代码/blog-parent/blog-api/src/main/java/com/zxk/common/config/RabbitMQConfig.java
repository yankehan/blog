package com.zxk.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 闫柯含
 * @since 2022年 05月 09日 下午 5:32
 * RabbitMQ的配置
 */
@Configuration
public class RabbitMQConfig {

    // 1.声明注册fanout模式的交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        // 参数1：交换机名称  参数2：是否持久化   参数3：消息为空，是否自动删除队列
        return new FanoutExchange("fanout_blog_article"
                , true
                , false);
    }



    // 2.声明队列
    @Bean
    public Queue updateArticleQueue(){
        return new Queue("fanout.article.update.cache", true);
    }



    // 3.完成队列和交换机的绑定关系
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(updateArticleQueue())
                .to(fanoutExchange());
    }
}
