package tricolor.no1.Handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tricolor.no1.model.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginHandler implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //前端会把token放在HTTP头里，需要获取
        String token = request.getHeader("token");
        //查询是否在redis中存在
        if (token!=null&&redisTemplate.hasKey(token))
        {
            //token如果存在的话，说明可以进行记录
            User user = (User)redisTemplate.opsForValue().get(token);
            if (user!=null)
            {
                //放进请求上下文中，或者保存在ThreadLocal中以便之后的请求也能获取
                request.setAttribute("user",user);
                return true;
            }
        }


        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
