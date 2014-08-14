package yxs.snake.str.system.service;

import yxs.snake.str.system.model.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
public interface RoleService {

    /**
     * get role list
     *
     * @param condition
     * @return
     */
    public List<Role> getRoleList(Map<String, Object> condition, int pageNo, int pageSize);

    /**
     * get role list count
     *
     * @param condition
     * @return
     */
    public int getRoleCount(Map<String, Object> condition);

    /**
     * get a role
     *
     * @param roleId
     * @return
     */
    public Role getRole(int roleId);

    /**
     * add a role
     *
     * @param role
     * @return
     */
    public int addRole(Role role);

    /**
     * edit a role
     *
     * @param id
     * @param role
     * @return
     */
    public int editRole(int id, Role role);

    /**
     * delete a role
     *
     * @param id
     * @return
     */
    public int deleteRole(int id);

    /**
     * set the role of ${roleId} with permissions of ${permissionIds}
     *
     * @param roleId
     * @param permissionIds
     * @return
     */
    public int setRolePermission(int roleId, int[] permissionIds);
}
