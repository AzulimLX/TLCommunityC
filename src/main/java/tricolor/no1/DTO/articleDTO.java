package tricolor.no1.DTO;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tricolor.no1.model.User;
import tricolor.no1.model.article;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class articleDTO {

    String username;

    String photo;

    public Integer id;

    public String title;

    public String content;
}
