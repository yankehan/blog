package com.zxk.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zxk.mapper.ArticleMapper;
import com.zxk.pojo.Article;
import com.zxk.service.ThreadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 闫柯含
 * @date 2022年 01月 30日 下午 4:45
 * @功能说明: =>异步操作
 */
@Slf4j
@Service
@AllArgsConstructor
public class ThreadServiceImpl implements ThreadService {

    private ArticleMapper articleMapper;

    @Async("taskExecutor") //将任务放过改线程池中执行
    @Override
    public void updateArticleView(Long id) {
        try {
            //这里睡眠5秒，查看是否异步成功,否则优化不明显👍
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            // 乐观锁 + 自旋锁(自旋3次，访问量大的时候可能会阅读量丢失)
            Article article = articleMapper.selectById(id);
            Article articleUpdate = new Article();
            articleUpdate.setViewCounts(article.getViewCounts() + 1);
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, article.getId());
            updateWrapper.eq(Article::getViewCounts, article.getViewCounts());
            int flag = articleMapper.update(articleUpdate, updateWrapper);
            if (flag == 1){
                break;
            }
        }
    }


}
