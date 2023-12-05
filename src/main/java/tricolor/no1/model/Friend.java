package tricolor.no1.model;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {

    Integer id;

    @TableField("main_id")
    String mainId;

    @TableField("friend_id")
    String friendId;

    @TableField("create_time")
    Date createTime;

}
