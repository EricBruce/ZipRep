package yxs.eric.craft.account.provider.dao;

import com.yxs.eric.craft.account.model.Menu;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-21.
 */
public class MenuDao {
    @Resource
    JdbcTemplate jdbcTemplate;
    BeanPropertyRowMapper<Menu> mapper = new BeanPropertyRowMapper(Menu.class);

    /**
     * query a menu of ${menuId}
     *
     * @param menuId
     * @return
     */
    public Menu queryMenu(int menuId) {
        String sql = "select * from s_menu where id=?";
        Menu menu = jdbcTemplate.queryForObject(sql, Menu.class, mapper);
        return menu;
    }

    /**
     * query all menus
     *
     * @return
     */
    public List<Menu> queryMenus(Map<String,Object> condition) {
        StringBuilder sql = new StringBuilder("select * from s_menu where 1=1 ");
        List<Object> args = new ArrayList<>();
        if (!StringUtils.isEmpty(condition.get("level"))) {
            sql.append(" and level=? ");
            args.add(condition.get("level"));
        }
        if (!StringUtils.isEmpty(condition.get("pId"))) {
            sql.append(" and p_id=? ");
            args.add(condition.get("pId"));
        }
        List<Menu> menus = jdbcTemplate.queryForList(sql.toString(), Menu.class, args);
        return menus;
    }
}
