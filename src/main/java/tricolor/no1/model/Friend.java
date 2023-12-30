package tricolor.no1.model;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tricolor.no1.util.TimeUtil;

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

    public Friend(String mainId,String friendId){
        this.mainId = mainId;
        this.friendId = friendId;
        this.createTime = TimeUtil.getSqlDate();
    }

}
