package tricolor.no1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    public String name;

    public String photo;

    public String content;

    public String mapname;

    public String coordinate;

    public String adcode;

}
