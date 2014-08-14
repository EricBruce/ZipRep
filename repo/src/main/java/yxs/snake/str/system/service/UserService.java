package yxs.snake.str.system.service;

import yxs.snake.str.system.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
public interface UserService {

    /**
     * get user list
     *
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<User> getUserList(Map<String, Object> condition, int pageNo, int pageSize);

    /**
     * get user count
     *
     * @param condition
     * @return
     */
    public int getUserCount(Map<String, Object> condition);

    /**
     * get a user
     *
     * @param userId
     * @return
     */
    public User getUser(int userId);

    /**
     * add a user
     *
     * @param user
     * @return
     */
    public int addUser(User user);

    /**
     * edit a user
     *
     * @param userId
     * @param user
     * @return
     */
    public int editUser(int userId, User user);

    /**
     * delete a user
     *
     * @param userId
     * @return
     */
    public int deleteUser(int userId);

    /**
     * set user role with the roleId if there is a roleId for user then replace it
     *
     * @param userId
     * @param roleId
     * @return
     */
    public int setUserRole(int userId, int roleId);

}
