package tricolor.no1.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dan {

    public Integer id;

    public String author;

    public String content;

    @TableField("create_time")
    public Date createTime;

}
