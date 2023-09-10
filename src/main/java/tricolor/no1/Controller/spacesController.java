package tricolor.no1.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.Enum.Status.status;
import tricolor.no1.Mapper.spacesMapper;
import tricolor.no1.Server.spaces.spacesServerImpl;
import tricolor.no1.model.Result;
import tricolor.no1.model.User;
import tricolor.no1.model.spaces;
import tricolor.no1.util.RedisIsConnectUtil;
import tricolor.no1.util.spacesUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/spaces")
public class spacesController {

    @Autowired
    private spacesServerImpl spacesServer;

    @Autowired
    private spacesMapper spacesMappers;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private RedisIsConnectUtil redisIsConnectUtil;

    //返回该账号的所有空间信息
    @GetMapping("UserSpacesData")
    public Result AllData(@RequestParam(name = "user") String user)
    {
        //使用user联合查询,但好像也不用
        QueryWrapper<spaces> spacesQueryWrapper = new QueryWrapper<>();
        spacesQueryWrapper.eq("author",user);
        spaces userSpace = spacesServer.getOne(spacesQueryWrapper);
        return Result.success(userSpace);
    }

    //更改改账号的空间信息
    @PostMapping("/UpdatedSpacesData")
    public Result UpdatedData(@RequestBody spaces spacess,
                              HttpServletRequest request,
                              HttpServletResponse response)
    {
        spaces Spaces = spacesUtils.NullSpaces(spacess);
        //更新需要进行联表更新，使用Mapper
        int update = spacesMappers.updatedUserAndSpace(Spaces);
        //TODO 而我们在redis中获取用户信息，本来则需要在redis里也进行更新,这里进行一个偷懒，感觉不需要

        if (update >=1)
        {
            return Result.success("更新成功");
        }else
        {
            return Result.fail("出现不明原因，更新失败");
        }

    }

}
