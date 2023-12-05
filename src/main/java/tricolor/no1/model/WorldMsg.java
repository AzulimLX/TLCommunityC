package tricolor.no1.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("world_msg")
public class WorldMsg
{
    @TableField("from_user")
    String fromUser;

    @TableField("msg")
    String msg;

    @TableField("create_time")
    String createTime;

    @TableField("is_file")
    Integer isFile;

}
