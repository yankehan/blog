package com.zxk.vo.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 11:09
 * @功能说明: =>分页参数
 *             第几页page，每页几条数据pageSize=>封装为对象PageParams
 *             哪个标签下，哪个分类下
 *             那年，哪月
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageParam {
    /**
     * 第几页
     */
    private int page = 1;
    /**
     * 每页多少条数据
     */
    private int pageSize = 10;
    /**
     * 哪个分类下
     */
    private Long categoryId;
    /**
     * 哪个标签下
     */
    private Long tagId;
    /**
     * 哪年
     */
    private String year;
    /**
     * 哪月
     */
    private String month;

    /**
     * 重写Month的get方法，例如 6 月
     *      因为 前端传过来可能是 6 月
     *      但是 MySql数据库（FROM_UNIXTIME函数的结果）需要的是 06 月
     *          也即（FROM_UNIXTIME函数的结果） == 06
     *          所以要转化一下
     * @return
     */
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0" + this.month;
        }
        return this.month;
    }
}
