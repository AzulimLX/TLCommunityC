package tricolor.no1.Server.place;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.placeMapper;
import tricolor.no1.model.place;

@Service
public class placeServerImpl extends ServiceImpl<placeMapper, place> implements placeServer {
}
