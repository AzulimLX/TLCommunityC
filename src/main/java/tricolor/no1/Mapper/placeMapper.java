package tricolor.no1.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tricolor.no1.DTO.danDTO;
import tricolor.no1.model.place;

import java.util.List;

@Mapper
public interface placeMapper extends BaseMapper<place> {

}
