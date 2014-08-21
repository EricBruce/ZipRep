package com.yxs.eric.craft.account.service;

import com.yxs.eric.craft.account.model.Role;
import com.yxs.eric.craft.account.model.User;

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
     * @param createBy
     * @param user
     * @return
     */
    public int addUser(String createBy, User user);

    /**
     * edit a user
     * @param modifyBy
     * @param userId
     * @param user
     * @return
     */
    public int editUser(String modifyBy, int userId, User user);

    /**
     * delete a user
     *
     * @param deleteBy
     * @param userId
     * @return
     */
    public int deleteUser(String deleteBy, int userId);

    /**
     * set user role with the roleId if there is a roleId for user then replace it
     *
     * @param userId
     * @param roleId
     * @return
     */
    public int setUserRole(int userId, int roleId);

    /**
     * get role of user(${userId}) if there is no role then return null
     *
     * @param userId
     * @return
     */
    public Role getUserRole(int userId);

    /**
     * delete user role
     * @param userId
     * @return
     */
    public int deleteUserRole(int userId);

}
