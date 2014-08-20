package yxs.eric.craft.account.provider.service;

import com.yxs.eric.craft.account.model.Permission;
import com.yxs.eric.craft.account.service.PermissionService;
import org.springframework.stereotype.Service;

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
