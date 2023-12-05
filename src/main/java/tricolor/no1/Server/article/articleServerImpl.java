package tricolor.no1.Server.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.ArticleMapper;
import tricolor.no1.model.Article;

@Service
public class articleServerImpl extends ServiceImpl<ArticleMapper, Article> implements articleServer{
}
