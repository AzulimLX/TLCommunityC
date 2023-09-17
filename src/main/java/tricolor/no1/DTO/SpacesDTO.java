package tricolor.no1.DTO;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpacesDTO {
    //首先账号
    String author;
    //用户名
    String username;
    //用户头像
    String photo;
    //简介
    public String content;
    //各种平台啦
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
