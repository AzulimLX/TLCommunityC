package tricolor.no1.Server.User;

import com.baomidou.mybatisplus.extension.service.IService;
import tricolor.no1.model.User;

import java.util.List;

public interface UserServer extends IService<User> {
   User Testlogin(User user);

    List<User> getUser();
}
