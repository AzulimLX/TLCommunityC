package tricolor.no1.Server.Friend;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.FriendMapper;
import tricolor.no1.Server.AddFriendMsg.AddFriendMsgServer;
import tricolor.no1.Server.User.UserServer;
import tricolor.no1.model.AddFriendMsg;
import tricolor.no1.model.Friend;
import tricolor.no1.model.Result;
import tricolor.no1.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendServerImpl extends ServiceImpl<FriendMapper, Friend> implements FriendServer {

    //添加friendServer
    @Autowired
    public FriendServer friendServer;

    @Autowired
    public AddFriendMsgServer addFriendMsgServer;

    @Autowired
    public UserServer userServer;

    @Override
    public List<Friend> GetAllFriendId(String MainId) {
        QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
        friendQueryWrapper.eq("main_id",MainId);
        List<Friend> FriendIdList = friendServer.list(friendQueryWrapper);
        return FriendIdList;
    }

    @Override
    public boolean sendAddInfo(String MainId, String ToId, String information) {
        boolean result = true;
        //查看ToId是否有这个请求，且这个请求若是还未读，且好友验证信息和旧的一样的，则不必要进行处理
        QueryWrapper<AddFriendMsg> addFriendMsgQueryWrapper = new QueryWrapper<>();
        addFriendMsgQueryWrapper.eq("from_id",MainId);
        addFriendMsgQueryWrapper.eq("to_id",ToId);
        addFriendMsgQueryWrapper.eq("is_read",0);
        addFriendMsgQueryWrapper.eq("information",information);
        AddFriendMsg one = addFriendMsgServer.getOne(addFriendMsgQueryWrapper);
        if (one != null)
        {
            return false;
        }

        //首先构造发送请求的对象
        AddFriendMsg addFriendMsg = new AddFriendMsg(MainId,ToId,0,information);
        //构建好了后直接更新
        result = addFriendMsgServer.save(addFriendMsg);
        return result;
    }

    @Override
    public List<User> getUserInfoById(List<Friend> FriendsList) {
        //先进行遍历查找,感觉比较适合使用lambda
        List<User> UserList =  FriendsList.stream().map(friend -> {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("user",friend.getFriendId());
            User one = userServer.getOne(userQueryWrapper);
            one.setPassword(null);
            one.setPhoneNumber(null);
            return one;
        }).collect(Collectors.toList());

        return UserList;
    }
}
