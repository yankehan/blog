package com.zxk.vo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 闫柯含
 * @date 2021年 12月 29日 下午 11:16
 * @功能说明: =>返回值
 *             Controller层需要返回JSON数据，并且该返回值有 状态码、状态信息、数据
 *             就要将返回值封装成一个对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result {
    /**
     * 本次请求是否成功
     */
    private boolean success;
    /**
     * 状态码 (200表示成功)
     */
    private Integer code;
    /**
     * 状态信息(success表示成功)
     */
    private String msg;
    /**
     * 数据 (不知道什么类型=>用Object)
     */
    private Object data;

    /**
     * 响应成功的请求
     */
    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }
    /**
     * 响应失败的请求
     */
    public static Result fail(Integer code,String msg){
        return new Result(false,code,msg,null);
    }

}
