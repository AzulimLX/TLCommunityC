package tricolor.no1.Server.spaces;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.spacesMapper;
import tricolor.no1.model.spaces;

@Service
public class spacesServerImpl extends ServiceImpl<spacesMapper, spaces> implements spacesServer {
}
