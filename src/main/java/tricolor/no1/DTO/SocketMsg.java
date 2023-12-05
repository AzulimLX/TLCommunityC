package tricolor.no1.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("socket_msg")
public class SocketMsg {

    @TableField("cm_type")
    int type; //聊天类型
    @TableField("from_user")
    String fromUser; //发送者
    @TableField("to_user")
    String toUser;//接收者

    String msg;//消息

    @TableField("is_read")
    int isRead;//是否被读

    @TableField("create_time")
    public Date createTime;


}
