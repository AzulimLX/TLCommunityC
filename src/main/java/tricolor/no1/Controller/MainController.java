package tricolor.no1.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/spring")
    public String hello(){
        return "hello";
    }
}


