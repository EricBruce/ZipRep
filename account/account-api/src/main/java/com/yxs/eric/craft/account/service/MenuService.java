package com.yxs.eric.craft.account.service;

import com.google.common.collect.Multimap;
import com.yxs.eric.craft.account.model.Menu;

import java.util.List;

/**
 * Created by popo on 14-8-14.
 */
public interface MenuService {

    /**
     * get a menu of ${menuId}
     *
     * @param menuId
     * @return
     */
    public Menu getMenu(int menuId);

    /**
     * get menus
     *
     * @return
     */
    public Multimap<Menu, Menu> getMenus();

    /**
     * get menus for role of ${roleId}
     *
     * @param roleId
     * @return
     */
    public Multimap<Menu,Menu> getMenusForRole(int roleId);

    /**
     * get menus for user of ${userId}
     *
     * @param userId
     * @return
     */
    public List<Menu> getMenusForUser(int userId);
}
