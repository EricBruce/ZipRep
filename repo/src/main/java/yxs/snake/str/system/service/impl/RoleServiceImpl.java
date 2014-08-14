package yxs.snake.str.system.service.impl;

import org.springframework.stereotype.Service;
import yxs.snake.str.system.dao.RoleDao;
import yxs.snake.str.system.model.Role;
import yxs.snake.str.system.service.RoleService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleDao roleDao;

    @Override
    public List<Role> getRoleList(Map<String, Object> condition, int pageNo, int pageSize) {
        return roleDao.queryRole(condition, pageNo, pageSize);
    }

    @Override
    public int getRoleCount(Map<String, Object> condition) {
        return roleDao.queryRoleCount(condition);
    }

    @Override
    public Role getRole(int roleId) {
        return roleDao.getRole(roleId);
    }

    @Override
    public int addRole(Role role) {
        return roleDao.addRole(role);
    }

    @Override
    public int editRole(int id, Role role) {
        return roleDao.editRole(id, role);
    }

    @Override
    public int deleteRole(int id) {
        return roleDao.deleteRole(id);
    }

    @Override
    public int setRolePermission(int roleId, int[] permissionIds) {
        // TODO set role permission
        return 0;
    }
}
