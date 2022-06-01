package com.zxk.common.exception;

import com.zxk.vo.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 闫柯含
 * @date 2021年 12月 31日 下午 8:51
 * @功能说明: => 对异常处理
 *  一般异常会在页面显示一堆500等提示信息，对用户不友好
 *              使用该异常处理器
 *  当异常发生时，返回一个错误代码，提示用户系统出错
 */
@ControllerAdvice
public class AllExceptionHandle {

    /**
     * 处理所有服务器抛出的异常
     * @ExceptionHandler 捕获指定类型的异常
     * @ResponseBody 返回JSON数据
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result allExceptionHandle(Exception e){
        e.printStackTrace();//打印错误信息，后期写入日志文件中
        return Result.fail(500,"发生了一点小错误，我们正在尽全力维护");
    }
}
