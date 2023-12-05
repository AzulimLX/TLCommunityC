package tricolor.no1.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这个是个人空间的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spaces {

    public String author;

    public String username;

    public String content;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    public String bili;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    public String github;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    public String csdn;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    public String microblog;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    public String zhihu;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    public String juejin;

}
