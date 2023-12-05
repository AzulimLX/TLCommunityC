package tricolor.no1.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tricolor.no1.util.TimeUtil;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendMsg {

       Integer id;

       @TableField("from_id")
       String fromId;

       @TableField("to_id")
       String toId;

       @TableField("is_read")
       Integer isRead;

       @TableField("information")
       String information;

       @TableField("create_time")
       Date createTime;


       public AddFriendMsg(String fromId,String toId,Integer isRead,String information){
              this.fromId = fromId;
              this.toId = toId;
              this.isRead = isRead;
              this.information = information;
              this.createTime = TimeUtil.getSqlDate();
       }

}
