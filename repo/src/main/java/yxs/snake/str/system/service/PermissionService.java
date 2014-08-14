package yxs.snake.str.system.service;

import yxs.snake.str.system.model.Permission;

import java.util.List;

/**
 * Created by popo on 14-8-14.
 */
public interface PermissionService {

    /**
     * get permissions for role of ${roleId}
     *
     * @param roleId
     * @return
     */
    public List<Permission> getPermissions(int roleId);


}
