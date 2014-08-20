package yxs.eric.craft.account.provider.service;

import com.yxs.eric.craft.account.model.User;
import com.yxs.eric.craft.account.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getUserList(Map<String, Object> condition, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public int getUserCount(Map<String, Object> condition) {
        return 0;
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public int editUser(int userId, User user) {
        return 0;
    }

    @Override
    public int deleteUser(int userId) {
        return 0;
    }

    @Override
    public int setUserRole(int userId, int roleId) {
        return 0;
    }
}
