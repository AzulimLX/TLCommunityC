package tricolor.no1.Server.Friend;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.FriendMapper;
import tricolor.no1.model.Friend;

@Service
public class FriendServerImpl extends ServiceImpl<FriendMapper, Friend> implements FriendServer {
}
