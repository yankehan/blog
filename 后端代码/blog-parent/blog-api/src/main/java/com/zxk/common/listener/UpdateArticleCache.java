package com.zxk.common.listener;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.zxk.service.ArticleService;
import com.zxk.vo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.util.Set;

/**
 * @author 闫柯含
 * @since 2022年 05月 09日 下午 5:44
 * 消费者：
 * 消费RabbitMQ队列中的《修改文章缓存》任务
 */
@Slf4j
@Component
@RabbitListener(queues = {"fanout.article.update.cache"}) // 接收哪个队列中的任务
public class UpdateArticleCache {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RabbitHandler
    public void updateArticleCacheById(String articleId) {
        // 1.更新该文章的缓存
        log.info("编辑功能启动=======>修改了文章缓存:{}",articleId);
        String params = DigestUtil.md5Hex(articleId);
        String redisKey = "findArticleById::ArticleController::findArticleById::" + params;
        Result result = articleService.findArticleById(Long.valueOf(articleId));
        redisTemplate.opsForValue()
                .set(redisKey,
                        JSON.toJSONString(result),
                        Duration.ofMillis(5 * 60 * 1000));
        // 2.删除文章列表缓存
        log.info("编辑功能启动=======>删除了文章列表缓存");
        Set<String> keys = redisTemplate.keys("list_article*");
        keys.forEach(s -> {
            redisTemplate.delete(s);
        });
    }

}
