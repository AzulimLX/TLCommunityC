package tricolor.no1.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendMsgDTO {

    Integer id;

    String fromId;

    String toId;

    Integer isRead;

    String information;

    Date createTime;

    String username;

    String photo;

}
