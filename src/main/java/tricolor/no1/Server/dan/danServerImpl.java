package tricolor.no1.Server.dan;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.DanMapper;
import tricolor.no1.model.Dan;

@Service
public class danServerImpl extends ServiceImpl<DanMapper, Dan> implements danServer {
}
