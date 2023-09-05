package tricolor.no1.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class danDTO {
    public Integer id;

    public String author;

    public String content;

    public Date createTime;

    public String photo;

    public String username;

}
