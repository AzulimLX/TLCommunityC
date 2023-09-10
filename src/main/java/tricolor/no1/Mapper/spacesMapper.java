package tricolor.no1.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tricolor.no1.model.spaces;

@Mapper
public interface spacesMapper extends BaseMapper<spaces> {

    @Update("UPDATE USER u, spaces s SET " +
            "u.username = #{spaces.username}," +
            "s.username= #{spaces.username}," +
            "s.content = #{spaces.content}," +
            "s.bili=#{spaces.bili} ," +
            "s.github = #{spaces.github}," +
            "s.csdn = #{spaces.csdn}," +
            "s.microblog = #{spaces.microblog}," +
            "s.zhihu = #{spaces.zhihu}," +
            "s.juejin = #{spaces.juejin} " +
            "WHERE s.author = #{spaces.author} AND u.user = #{spaces.author}")
    public int updatedUserAndSpace(@Param("spaces") spaces spacess);
}
