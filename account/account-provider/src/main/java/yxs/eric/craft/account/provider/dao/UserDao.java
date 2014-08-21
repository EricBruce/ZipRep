package yxs.eric.craft.account.provider.dao;

import com.yxs.eric.craft.account.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-21.
 */
public class UserDao {

    @Resource
    JdbcTemplate jdbcTemplate;
    BeanPropertyRowMapper<User> mapper = new BeanPropertyRowMapper(User.class);

    /**
     * get user list for condition
     *
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<User> queryUsers(Map<String, Object> condition, int pageNo, int pageSize) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from s_user where 1=1 ");
        List<Object> args = new ArrayList<>();
        if (!StringUtils.isEmpty(condition.get("displayName"))) {
            sql.append(" and display_name like ?");
            args.add("%" + condition.get("displayName") + "%");
        }
        sql.append(" order by create_at desc ");
        if (pageSize > 0) {
            int start = (pageNo - 1) * pageSize;
            sql.append(" limit ?,?");
            args.add(start);
            args.add(pageSize);
        }
        List<User> users = jdbcTemplate.query(sql.toString(), mapper, args);
        return users;
    }

    public int queryUsersCount(Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(id) from s_user where 1=1 ");
        List<Object> args = new ArrayList<Object>();
        if (!StringUtils.isEmpty(condition.get("displayName"))) {
            sql.append(" and display_name like ?");
            args.add("%" + condition.get("displayName") + "%");
        }
        int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, args);
        return count;
    }

    /**
     * get a user of ${userId}
     * @param userId
     * @return
     */
    public User queryUser(int userId) {
        final String sql = "select * from s_user where id=? ";
        User user = jdbcTemplate.queryForObject(sql, User.class, userId);
        return user;
    }

    public int addUser(User user) {
        final String sql = "insert into s_user(user_name,display_name,password,create_at,create_by,modify_at)" +
                "values(:userName,:displayName,:password,:createAt,:createBy,:modifyAt)";
        int rows = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
        return rows;
    }

    public int editUser(int userId, User user) {
        final String sql = "update s_user set user_name=:userName,display_name=:displayName,modify_at=:modifyAt" +
                "where id="+userId;
        int rows = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
        return rows;
    }

    public int deleteUser(int userId) {
        final String sql = "delete from s_user where id=?";
        int rows = jdbcTemplate.update(sql, userId);
        return rows;
    }

    /**
     * set user of ${userId} with role of ${roleId}
     * @param userId
     * @param roleId
     */
    public int setUserRole(int userId, int roleId) {
        final String sql = "insert into r_user_role(user_id,role_id)values(?,?)ON DUPLICATE KEY UPDATE null";
        int rows = jdbcTemplate.update(sql, userId, roleId);
        return rows;
    }

    public int getUserRole(int userId) {
        final String sql = "select role_id from r_user_role where user_id=?";
        int roleId = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return roleId;
    }

    public int deleteUserRole(int userId) {
        final String sql = "delete from r_user_role where user_id=?";
        int rows = jdbcTemplate.update(sql, userId);
        return rows;
    }
}
