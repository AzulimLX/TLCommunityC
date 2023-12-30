package tricolor.no1.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tricolor.no1.DTO.AddFriendMsgDTO;
import tricolor.no1.model.AddFriendMsg;

import java.util.List;

@Mapper
public interface AddFriendMsgMapper extends BaseMapper<AddFriendMsg> {
    @Select("SELECT m.*,u.username,u.photo " +
            "FROM add_friend_msg m " +
            "JOIN USER u " +
            "ON m.from_id = u.user " +
            "WHERE m.is_read =0 " +
            "AND m.to_id = #{MainId}")
    List<AddFriendMsgDTO> AllAddFriendMsgList(@Param("MainId")String MainId);
}
