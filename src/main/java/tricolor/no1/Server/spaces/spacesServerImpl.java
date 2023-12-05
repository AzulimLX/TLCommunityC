package tricolor.no1.Server.spaces;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.SpacesMapper;
import tricolor.no1.model.Spaces;

@Service
public class spacesServerImpl extends ServiceImpl<SpacesMapper, Spaces> implements spacesServer {
}
