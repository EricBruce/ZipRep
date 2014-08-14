package yxs.snake.str.system.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import yxs.snake.str.system.model.Role;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
@Repository
public class RoleDao {
    @Resource
    JdbcTemplate jdbcTemplate;
    BeanPropertyRowMapper<Role> mapper = new BeanPropertyRowMapper<>(Role.class);

    /**
     * query role for condition
     *
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<Role> queryRole(Map<String, Object> condition, int pageNo, int pageSize) {
        StringBuilder sql = new StringBuilder();
        List<Object> args = new ArrayList<>();
        sql.append("select * from s_role where 1=1 ");
        /* name condition */
        if (!StringUtils.isEmpty(condition.get("name"))) {
            sql.append(" and name like ? ");
            args.add("%" + condition.get("name") + "%");
        }
        sql.append(" order by id ");
        if (pageSize > 0) {
            int start = (pageSize - 1) * pageNo;
            sql.append(" limit ?,?");
            args.add(start);
            args.add(pageSize);
        }
        List<Role> roleList = jdbcTemplate.query(sql.toString(), args.toArray(), mapper);
        return roleList;
    }

    /**
     * query role count
     *
     * @param condition
     * @return
     */
    public int queryRoleCount(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        List<Object> args = new ArrayList<>();
        sql.append("select count(id) from s_role where 1=1");
        if (!StringUtils.isEmpty(condition.get("name"))) {
            sql.append(" and name like ? ");
            args.add("%" + condition.get("name") + "%");
        }
        int count = jdbcTemplate.queryForObject(sql.toString(), args.toArray(), Integer.class);
        return count;
    }

    /**
     * get a role
     *
     * @param roleId
     * @return
     */
    public Role getRole(int roleId) {
        String sql = "select * from s_role where id = ?";
        Role role = jdbcTemplate.queryForObject(sql.toString(), new Object[]{roleId}, mapper);
        return role;
    }

    /**
     * add a role
     *
     * @param role
     * @return
     */
    public int addRole(Role role) {
        String sql = "insert into s_role(`name`,`desc`,create_at,create_by)" +
                " values (:name,:desc,:createAt,:createBy)";
        int rows = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(role));
        return rows;
    }

    /**
     * delete a role
     *
     * @param roleId
     * @return
     */
    public int deleteRole(int roleId) {
        String sql = "delete from s_role where id = ?";
        int rows = jdbcTemplate.update(sql, roleId);
        return rows;
    }

    /**
     * edit role
     *
     * @param roleId
     * @param role
     * @return
     */
    public int editRole(int roleId, Role role) {
        StringBuilder sql = new StringBuilder();
        sql.append("update s_role set `name`=:name, `desc`=:desc where id=" + roleId);
        int rows = jdbcTemplate.update(sql.toString(), new BeanPropertySqlParameterSource(role));
        return rows;
    }
}
