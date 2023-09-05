package tricolor.no1.Server.User;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tricolor.no1.Mapper.UserMapper;
import tricolor.no1.model.User;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserServer {
    @Autowired
    public UserMapper userMapper;


    @Override
    public User Testlogin(User user) {
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("user",user.getUsername());
        objectQueryWrapper.eq("password",user.getPassword());
        User user1 = userMapper.selectOne(objectQueryWrapper);
        if (user1 == null)
        {
            return null;
        }
        else
        return user1;
    }

    @Override
    public List<User> getUser() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(userQueryWrapper);
        return users;
    }
}
