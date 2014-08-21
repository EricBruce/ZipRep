package yxs.eric.craft.account.provider.service;

import com.yxs.eric.craft.account.model.Role;
import com.yxs.eric.craft.account.model.User;
import com.yxs.eric.craft.account.service.RoleService;
import com.yxs.eric.craft.account.service.UserService;
import org.springframework.stereotype.Service;
import yxs.eric.craft.account.provider.dao.UserDao;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;
    @Resource
    RoleService roleService;

    @Override
    public List<User> getUserList(Map<String, Object> condition, int pageNo, int pageSize) {
        return userDao.queryUsers(condition, pageNo, pageSize);
    }

    @Override
    public int getUserCount(Map<String, Object> condition) {
        return userDao.queryUsersCount(condition);
    }

    @Override
    public User getUser(int userId) {
        return userDao.queryUser(userId);
    }

    @Override
    public int addUser(String createBy, User user) {
        Date createAt = new Date();
        // TODO encrypt password use tea or SHA
        user.setCreateBy(createBy);
        user.setModifyAt(new Date());
        user.setCreateAt(new Date());
        return userDao.addUser(user);
    }

    @Override
    public int editUser(String modifyBy, int userId, User user) {
        user.setModifyAt(new Date());
        int rows = userDao.editUser(userId, user);
        return rows;
    }

    @Override
    public int deleteUser(String createBy, int userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public int setUserRole(int userId, int roleId) {
        return userDao.setUserRole(userId,roleId);
    }

    @Override
    public Role getUserRole(int userId) {
        int roleId = userDao.getUserRole(userId);
        Role role = roleService.getRole(roleId);
        return role;
    }

    @Override
    public int deleteUserRole(int userId) {
        return userDao.deleteUserRole(userId);
    }
}
