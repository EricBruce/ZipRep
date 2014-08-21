package yxs.eric.craft.account.provider.service;

import com.yxs.eric.craft.account.model.Permission;
import com.yxs.eric.craft.account.service.PermissionService;
import org.springframework.stereotype.Service;
import yxs.eric.craft.account.provider.dao.PermissionDao;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by popo on 14-8-14.
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    PermissionDao permissionDao;

    @Override
    public List<Permission> getPermissions(int roleId) {
        List<Permission> pList = permissionDao.queryPermission(roleId);
        return pList;
    }


}
