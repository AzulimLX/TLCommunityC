package tricolor.no1.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tricolor.no1.DTO.AddFriendMsgDTO;
import tricolor.no1.Mapper.AddFriendMsgMapper;
import tricolor.no1.Server.AddFriendMsg.AddFriendMsgServer;
import tricolor.no1.Server.Friend.FriendServer;
import tricolor.no1.Server.Friend.FriendServerImpl;
import tricolor.no1.model.AddFriendMsg;
import tricolor.no1.model.Friend;
import tricolor.no1.model.Result;
import tricolor.no1.model.User;

import java.util.List;

@RestController
@RequestMapping("/Friend")
public class FriendController {

    @Autowired
    public AddFriendMsgMapper mapper;

    @Autowired
    public AddFriendMsgServer addFriendMsgServer;

    @Autowired
    public FriendServer friendServer;


    @GetMapping("/getAllFriend")
    public Result getAllFriends(@RequestParam("MainId") String MainId)
    {

        //需要根据MainId获取到相对应的角色的所有friend
        List<Friend> FriendList = friendServer.GetAllFriendId(MainId);
        return Result.success(FriendList);
    }


    //发送好友请求方法
    @PostMapping("/AddFriend")
    public Result AddFriend(@RequestParam("MainId") String MainId,
                            @RequestParam("ToId") String ToId,
                            @RequestParam("information") String information)
    {
        boolean save = friendServer.sendAddInfo(MainId,ToId,information);
        if (save)
        {
            return Result.success("存储成功");
        }
        else return Result.fail("不明原因更新失败");
    }

    //寻找有几个信息未读
    @GetMapping("GetNotReadCount")
    public Result GetNotReadCount(@RequestParam("MainId") String MainId)
    {
        //首先根据MainId和is_Read==0 搜索出数量
        QueryWrapper<AddFriendMsg> addFriendMsgQueryWrapper = new QueryWrapper<>();
        addFriendMsgQueryWrapper.eq("to_id",MainId);
        addFriendMsgQueryWrapper.eq("is_read",0);
        long count = addFriendMsgServer.count(addFriendMsgQueryWrapper);

        return Result.success(count);
    }

    //寻找未读信息列表
    @GetMapping("GetNotReadMsg")
    public Result GetNotReadMsg(@RequestParam("MainId") String MainId)
    {
        //首先，MainId是被发送的ID，其次，搜索没被读取的信息，最后list返回
        List<AddFriendMsgDTO> AddFriendMsgList = mapper.AllAddFriendMsgList(MainId);
        return Result.success(AddFriendMsgList);
    }

    //同意添加
    @PostMapping("/AgreeAdd")
    public Result AgreeAdd(@RequestParam("MainId") String MainId,@RequestParam("FromId") String FormId)
    {
        //首先把is_read改成1,首先把改的数据找出来，并进行set,和Update操作
        QueryWrapper<AddFriendMsg> addFriendMsgQueryWrapper = new QueryWrapper<>();
        addFriendMsgQueryWrapper.eq("from_id",FormId);
        addFriendMsgQueryWrapper.eq("to_id",MainId);
        addFriendMsgQueryWrapper.eq("is_read",0);
        AddFriendMsg result = addFriendMsgServer.getOne(addFriendMsgQueryWrapper);
        result.setIsRead(1);
        addFriendMsgServer.update(result, addFriendMsgQueryWrapper);
        //之后把FromId的用户添加到好友列表
        Friend friend = new Friend(MainId,FormId);
        Friend friend1 = new Friend(FormId, MainId);
        friendServer.save(friend);
        friendServer.save(friend1);
        return Result.success("成功添加好友");
    }
    //拒绝添加
    @PostMapping("/disAgreeAdd")
    public Result disAgreeAdd(@RequestParam("MainId") String MainId,@RequestParam("FromId") String FormId)
    {
        //只需要对is_read改成1就行了
        QueryWrapper<AddFriendMsg> addFriendMsgQueryWrapper = new QueryWrapper<>();
        addFriendMsgQueryWrapper.eq("from_id",FormId);
        addFriendMsgQueryWrapper.eq("to_id",MainId);
        addFriendMsgQueryWrapper.eq("is_read",0);
        AddFriendMsg result = addFriendMsgServer.getOne(addFriendMsgQueryWrapper);
        result.setIsRead(1);
        addFriendMsgServer.update(result, addFriendMsgQueryWrapper);

        return Result.success("成功拒绝");
    }

    //连带Friend的信息也一起返回
    @GetMapping("getAllFriendInfo")
    public Result getAllFriendInfo(@RequestParam("MainId") String MainId){
        //首先搜索出所有的朋友消息
        List<Friend> friends = friendServer.GetAllFriendId(MainId);
        //搜索所有friends的信息
        List<User> userInfoById = friendServer.getUserInfoById(friends);
        return Result.success(userInfoById);
    }



}
