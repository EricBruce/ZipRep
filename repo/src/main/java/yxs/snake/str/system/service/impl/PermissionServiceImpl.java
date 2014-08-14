package yxs.snake.str.system.service.impl;

import org.springframework.stereotype.Service;
import yxs.snake.str.system.model.Permission;
import yxs.snake.str.system.service.PermissionService;

import java.util.List;

/**
 * Created by popo on 14-8-14.
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<Permission> getPermissions(int roleId) {

        return null;
    }
}
