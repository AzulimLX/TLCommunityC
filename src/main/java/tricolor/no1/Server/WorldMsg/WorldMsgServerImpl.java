package tricolor.no1.Server.WorldMsg;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.WorldMsgMapper;
import tricolor.no1.model.WorldMsg;

@Service
public class WorldMsgServerImpl extends ServiceImpl<WorldMsgMapper, WorldMsg> implements WorldMsgServer {
}
