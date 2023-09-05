package tricolor.no1.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tricolor.no1.DTO.danDTO;
import tricolor.no1.model.dan;

import java.util.List;

@Mapper
public interface danMapper extends BaseMapper<dan> {
    @Select("SELECT d.*,u.username,u.photo " +
            "FROM dan d " +
            "JOIN USER u " +
            "ON d.author=u.user " +
            "ORDER BY d.id DESC")
    List<danDTO> getdanList();
}
