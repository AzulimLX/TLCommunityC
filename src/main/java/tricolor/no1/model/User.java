package tricolor.no1.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("user")
public class User {
    @TableField("user")
    private String user;

    private String username;

    private String password;
    @TableField("phonenumber")
    private String phoneNumber;

    private String photo;

    private String token;

}
