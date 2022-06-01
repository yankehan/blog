package com.zxk.vo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2021年 12月 31日 下午 10:05
 * @功能说明: =>文章归档（x年 x月 发布x篇文章）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Archives {
    private Integer year;
    private Integer month;
    private Integer count;
}
