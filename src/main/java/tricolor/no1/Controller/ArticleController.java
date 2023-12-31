package tricolor.no1.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tricolor.no1.DTO.articleDTO;
import tricolor.no1.Mapper.ArticleMapper;
import tricolor.no1.Server.User.UserServer;
import tricolor.no1.Server.article.articleServer;
import tricolor.no1.model.Result;

@RestController
public class ArticleController {
    @Autowired
    public articleServer articServer;

    @Autowired
    public UserServer userServer;

    @Autowired
    public ArticleMapper articleMapperr;

    @GetMapping("/page")
    public Result getAllArticle(@RequestParam(name = "page",defaultValue = "1") Integer page,
                                @RequestParam(name = "size",defaultValue = "5") Integer size,
                                @RequestParam(required = false,name = "name") String name)
    {

        Page<articleDTO> articleDTOPage = new Page<>(page, size);
        IPage<articleDTO> articleDTOIPage = articleMapperr.selectArticleWithAuthor(articleDTOPage);
        return Result.success(articleDTOIPage);

    }


}
