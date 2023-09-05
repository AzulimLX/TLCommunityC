package tricolor.no1.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tricolor.no1.DTO.articleDTO;
import tricolor.no1.model.article;

@Mapper
public interface articleMapper extends BaseMapper<article> {
    //对于文章内容，与User进行联表查询
    @Select("SELECT a.* ,u.username,u.photo " +
            "FROM article a " +
            "JOIN USER u ON a.`author` = u.`user`" +
            " ORDER BY a.id DESC")
    IPage<articleDTO> selectArticleWithAuthor(Page<articleDTO> articleDTOPage);

}
