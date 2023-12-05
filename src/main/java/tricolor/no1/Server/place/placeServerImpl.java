package tricolor.no1.Server.place;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.PlaceMapper;
import tricolor.no1.model.Place;

@Service
public class placeServerImpl extends ServiceImpl<PlaceMapper, Place> implements placeServer {
}
