package tricolor.no1.Server.SocketMsg;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.DTO.SocketMsg;
import tricolor.no1.Mapper.SocketMsgMapper;

@Service
public class SocketMsgServerImpl extends ServiceImpl<SocketMsgMapper, SocketMsg> implements SocketMsgServer {
}
