package tricolor.no1.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.Server.spaces.spacesServerImpl;
import tricolor.no1.model.Result;
import tricolor.no1.model.User;
import tricolor.no1.model.spaces;
import tricolor.no1.util.spacesUtils;

import java.lang.reflect.Field;

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

    //更改改账号的空间信息
    @PostMapping("/UpdatedSpacesData")
    public Result UpdatedData(@RequestBody spaces spacess)
    {
        spaces Spaces = spacesUtils.NullSpaces(spacess);
        QueryWrapper<spaces> spacesQueryWrapper = new QueryWrapper<>();
        spacesQueryWrapper.eq("author", spacess.author);
        boolean update = spacesServer.update(Spaces, spacesQueryWrapper);
        if (update)
        {
            return Result.success("更新成功");
        }
        return Result.fail("更新失败,请稍后再试");
    }

}
