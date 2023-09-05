package tricolor.no1.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.Server.spaces.spacesServerImpl;
import tricolor.no1.model.Result;
import tricolor.no1.model.User;
import tricolor.no1.model.spaces;

@RestController
@RequestMapping("/spaces")
public class spacesController {

    @Autowired
    private spacesServerImpl spacesServer;

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

}
