package com.zxk.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zxk.pojo.SysUser;
import com.zxk.service.LoginService;
import com.zxk.util.UserThreadLocal;
import com.zxk.vo.error_code.ErrorCode;
import com.zxk.vo.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 闫柯含
 * @date 2022年 01月 29日 下午 5:36
 * @功能说明: =>登录过滤器（检验用户是否登录）
 *              1.已经登录：放行
 *              2.未登录：返回 “未登录” ，不放行
 */
@Component
@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    // 需要使用 loginService 中的 check 方法检验是否登录
    private LoginService loginService;

    /**
     * 在方法执行之前，做一个过滤校验，看看是否放行
     * @return true 放行
     *         false 不放行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HandlerMethod 是 Controller请求 才处理
        // 其他的 例如资源请求，springboot 可以直接放过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StrUtil.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }
        SysUser user = loginService.checkToken(token);
        if (user == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }
        // 到这里可以确认是 正确的登录状态
        // 将用户放到ThreadLocal中，方便使用
        UserThreadLocal.set(user);
        // 记得放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        // 这里非常重要，记得释放掉ThreadLocal，否则会有内存泄露的风险
        UserThreadLocal.remove();
    }
}
