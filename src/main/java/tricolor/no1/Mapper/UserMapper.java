package tricolor.no1.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tricolor.no1.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
