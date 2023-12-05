package tricolor.no1.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tricolor.no1.DTO.WorldMsgDTO;
import tricolor.no1.model.WorldMsg;

import java.util.List;

@Mapper
public interface WorldMsgMapper extends BaseMapper<WorldMsg> {


    @Select("SELECT w.*,u.username,u.user,u.photo " +
            "FROM world_msg w" +
            " JOIN USER u " +
            "ON w.from_user = u.user " +
            "ORDER BY w.create_time ASC" +
            " LIMIT 10")
    List<WorldMsgDTO> selectNewMs();

}
