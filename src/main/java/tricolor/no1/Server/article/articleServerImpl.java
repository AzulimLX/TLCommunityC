package tricolor.no1.Server.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.articleMapper;
import tricolor.no1.model.article;

@Service
public class articleServerImpl extends ServiceImpl<articleMapper, article> implements articleServer{
}
