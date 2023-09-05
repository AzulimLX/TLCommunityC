package tricolor.no1.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article")
public class article {

    public Integer id;

    public String title;

    public String content;

    public String author;
}
