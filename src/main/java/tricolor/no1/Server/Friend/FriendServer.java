package tricolor.no1.Server.Friend;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import tricolor.no1.model.Friend;
import tricolor.no1.model.User;

import java.util.List;


public interface FriendServer extends IService<Friend> {
    List<Friend> GetAllFriendId(String MainId);

    boolean sendAddInfo(String MainId,String ToId,String information);

    List<User> getUserInfoById(List<Friend> FriendsList);
}
