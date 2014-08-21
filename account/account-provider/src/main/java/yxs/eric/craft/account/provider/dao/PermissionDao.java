package yxs.eric.craft.account.provider.dao;

import com.yxs.eric.craft.account.model.Permission;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-21.
 */
public class PermissionDao {
    @Resource
    JdbcTemplate jdbcTemplate;
    BeanPropertyRowMapper<Permission> mapper = new BeanPropertyRowMapper(Permission.class);

    /**
     * get role's permissions
     * @param roleId
     * @return
     */
    public List<Permission> queryPermission(int roleId) {
        String sql = "select t1.* from s_permission t1, r_role_permission t2 where t1.id=t2.permission_id and t2.role_id=?";
        List<Permission> list = jdbcTemplate.queryForList(sql, Permission.class, roleId);
        return list;
    }


}
