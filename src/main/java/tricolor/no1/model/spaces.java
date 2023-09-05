package tricolor.no1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这个是个人空间的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class spaces {

    public String author;

    public String content;

    public String bili;

    public String github;

    public String csdn;

    public String microblog;

    public String zhihu;

    public String juejin;

}
