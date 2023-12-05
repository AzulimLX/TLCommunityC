package tricolor.no1.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorldMsgDTO {

    String user;

    String userName;

    String photo;

    String fromUser;

    String msg;

    String createTime;

    Integer isFile;
}
