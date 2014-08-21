package yxs.eric.craft.account.provider.service;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.yxs.eric.craft.account.model.Menu;
import com.yxs.eric.craft.account.model.Permission;
import com.yxs.eric.craft.account.service.MenuService;
import com.yxs.eric.craft.account.service.PermissionService;
import org.springframework.stereotype.Service;
import yxs.eric.craft.account.provider.dao.MenuDao;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by popo on 14-8-14.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Resource
    MenuDao menuDao;
    @Resource
    PermissionService permissionService;

    @Override
    public Menu getMenu(int menuId) {
        return menuDao.queryMenu(menuId);
    }

    @Override
    public Multimap<Menu, Menu> getMenus() {
        Multimap<Menu,Menu> allMenu = LinkedListMultimap.create();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("level", 1);
        List<Menu> menus = menuDao.queryMenus(condition);
        condition.put("level", 2);
        for (Menu menu : menus) {
            condition.put("pId", menu.getId());
            for (Menu sm : menuDao.queryMenus(condition)) {
                allMenu.put(menu, sm);
            }
        }
        return allMenu;
    }

    @Override
    public Multimap<Menu,Menu> getMenusForRole(int roleId) {
        Multimap<Menu,Menu> roleMenu = LinkedListMultimap.create();

        List<Permission> pList = permissionService.getPermissions(roleId);
        for (Permission p : pList) {
            // is a menu
            if (p.isMenu()) {
                Menu menu = menuDao.queryMenu(p.getMenuId());
                Menu pMenu = menuDao.queryMenu(menu.getpId());
                roleMenu.put(pMenu, menu);
            }
        }
        return roleMenu;
    }

    @Override
    public List<Menu> getMenusForUser(int userId) {
        return null;
    }
}
