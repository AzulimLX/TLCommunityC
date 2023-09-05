package tricolor.no1.Server.dan;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.danMapper;
import tricolor.no1.model.dan;

@Service
public class danServerImpl extends ServiceImpl<danMapper,dan> implements danServer {
}
