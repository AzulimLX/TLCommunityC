package tricolor.no1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class articleDTO {

    String username;

    String photo;

    public Integer id;

    public String title;

    public String content;

    public String author;
}
