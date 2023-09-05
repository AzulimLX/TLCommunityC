package tricolor.no1.Controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.Server.User.UserServer;
import tricolor.no1.model.Result;
import tricolor.no1.model.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    public UserServer userServer;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    @PostMapping("/login")
    public Result login(@RequestBody User user,
                        HttpServletResponse response,
                        HttpServletRequest request
                        ){
        //首先先验证账号把
        User testlogin = userServer.Testlogin(user);
        if (testlogin == null)
        {
            return Result.Tfail(null,"账号或密码错误");
        }
        else
        {  //生成token并存入redis
            String token = UUID.randomUUID()+"";
            //生成刷新token,使用base64
            String refreshToken = UUID.randomUUID()+"";
            //时间为分钟，设置了30分钟的有效时长
            redisTemplate.opsForValue().set(token,testlogin,30,TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(refreshToken,testlogin,2,TimeUnit.DAYS);
            //用'token'key放进map里
            Map<String, String> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("token",token);
            stringStringHashMap.put("refreshtoken",refreshToken);
            //之后直接转为json格式给前端
            String s = JSON.toJSONString(stringStringHashMap);
            return Result.success(s,"登录成功");
        }


    }


}
