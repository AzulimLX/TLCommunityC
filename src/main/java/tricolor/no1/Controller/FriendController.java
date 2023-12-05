package tricolor.no1.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.Server.AddFriendMsg.AddFriendMsgServer;
import tricolor.no1.Server.Friend.FriendServer;
import tricolor.no1.model.AddFriendMsg;
import tricolor.no1.model.Friend;
import tricolor.no1.model.Result;

import java.util.List;

@RestController
@RequestMapping("/Friend")
public class FriendController {

    @Autowired
    public AddFriendMsgServer addFriendMsgServer;

    @Autowired
    public FriendServer friendServer;

    @GetMapping("/getAllFriend")
    public Result getAllFriends(@RequestParam("MainId") String MainId)
    {
        //需要根据MainId获取到相对应的角色的所有friend
        QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
        friendQueryWrapper.eq("main_id",MainId);
        List<Friend> FriendList = friendServer.list(friendQueryWrapper);
        return Result.success(FriendList);
    }


    @PostMapping("/AddFriend")
    public Result AddFriend(@RequestParam("MainId") String MainId,
                            @RequestParam("ToId") String ToId,
                            @RequestParam("information") String information)
    {
        //查看ToId是否有这个请求，且这个请求若是还未读，且好友验证信息和旧的一样的，则不必要进行处理
        QueryWrapper<AddFriendMsg> addFriendMsgQueryWrapper = new QueryWrapper<>();
        addFriendMsgQueryWrapper.eq("from_id",MainId);
        addFriendMsgQueryWrapper.eq("to_id",ToId);
        addFriendMsgQueryWrapper.eq("is_read",0);
        addFriendMsgQueryWrapper.eq("information",information);
        AddFriendMsg one = addFriendMsgServer.getOne(addFriendMsgQueryWrapper);
        if (one != null)
        {
            return Result.success("已经存在好友请求信息");
        }

        //首先构造发送请求的对象
        AddFriendMsg addFriendMsg = new AddFriendMsg(MainId,ToId,0,information);
        //构建好了后直接更新
        boolean save = addFriendMsgServer.save(addFriendMsg);
        if (save)
        {
            return Result.success("存储成功");
        }
        else return Result.fail("不明原因更新失败");
    }
}
