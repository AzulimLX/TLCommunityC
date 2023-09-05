package tricolor.no1.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisIsConnectUtil {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean checkRedisConnection() {
        try {
            // 通过 RedisTemplate 的连接工厂获取连接，并执行测试命令
            redisTemplate.getConnectionFactory().getConnection().ping();

            // 或者使用 StringRedisTemplate 直接执行 ping 命令
            stringRedisTemplate.getConnectionFactory().getConnection().ping();

            // 连接测试成功
            return true;
        } catch (Exception e) {
            // 连接测试失败
            return false;
        }
    }
}
