package tricolor.no1.Controller;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.Enum.Status.status;
import tricolor.no1.Server.User.UserServer;
import tricolor.no1.model.Result;
import tricolor.no1.model.User;
import tricolor.no1.util.RedisIsConnectUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 写User的controller类
 */
@RestController
public class UserController {

    @Autowired
    private UserServer userServer;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RedisIsConnectUtil redisIsConnectUtil;

    //获取用户
    @GetMapping("/main/getUser")
    public Result GetUser(HttpServletRequest request,
                          HttpServletResponse response)
    {
        //根据前端的请求头获取
        String authorization1 = request.getHeader("Authorization");
        System.out.println(authorization1);
        if (authorization1 == null)
        {
            return Result.fail("没有授权，请登录后再试");
        }
        else
        {   //判断redis是否开启
            if (redisIsConnectUtil.checkRedisConnection())
            {
               //连接成功
                if (redisTemplate.hasKey(authorization1))
                {
                    //存在则找token的对应用户信息
                    User user = (User)redisTemplate.opsForValue().get(authorization1);
                    System.out.println(user.getPhoto());
                    user.setPassword("xxxxxxxx");
                    user.setPhoneNumber("xxxxxxx");
                    return Result.success(user,"查询成功");
                }
                //不存在则告知用户
                response.setStatus(401);
                return Result.faill(status.Token_Error,"错误");
            }
            //连接失败
            return Result.fail("redis没有连接，请通知管理员");
        }

    }

    //刷新token
    @PostMapping("/user/refresh_token")
    public Result refresh(@RequestParam(required = false) String refreshtoken, HttpServletRequest request)
    {
          //获取到refreshtoken
          if (refreshtoken != null)
          {
              //判定不为空，从数据库寻找refreshtoken
              if (redisTemplate.hasKey(refreshtoken))
              {
                  //能找到，则取出来value，返回并生成新的token，refreshtoken
                  User use = (User)redisTemplate.opsForValue().get(refreshtoken);
                  String token = UUID.randomUUID()+"";
                  //存入redis
                  redisTemplate.opsForValue().set(token,use,30, TimeUnit.MINUTES);
                  //删除本来的refreshtoken
                  redisTemplate.delete(refreshtoken);
                  //重新生成新的refreshtoken,
                  String NewReToken = UUID.randomUUID()+"";
                  //存入redis
                  redisTemplate.opsForValue().set(NewReToken,use,30,TimeUnit.DAYS);
                  //返回新的token与刷新token
                  Map<String, String> TokenMap = new HashMap<>();
                  TokenMap.put("token",token);
                  TokenMap.put("refreshtoken",NewReToken);
                  String s = JSON.toJSONString(TokenMap);
                  System.out.println("成功刷新了token哦");
                  return Result.success(s,"刷新token成功");
              }

          }
              return Result.fail("失败");
    }

    //退出登录
    @PostMapping("/main/logout")
    public Result logout(HttpServletRequest request)
    {
        //首先获取请求头的token
        String authorization = request.getHeader("Authorization");
        //如果是空的
        if (authorization.equals(""))
        {
            return Result.fail("token为空，请正确进行请求");
        }

        //因为浏览器那边在前端已经清除了，所以不需要后端进行清除
        //之后在redis里找到并删除
        //判断redis是否开启
        if (redisIsConnectUtil.checkRedisConnection())
        {
            //连接成功,删除
            redisTemplate.delete(authorization);
            return Result.success("删除成功");
        }
        //连接失败
        return Result.fail("redis没有连接，请通知管理员再进行操作");

    }

}
