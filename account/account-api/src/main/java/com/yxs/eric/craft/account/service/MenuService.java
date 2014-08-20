package com.yxs.eric.craft.account.service;

import com.yxs.eric.craft.account.model.Menu;

import java.util.List;

/**
 * Created by popo on 14-8-14.
 */
public interface MenuService {

    /**
     * get menus for role of ${roleId}
     *
     * @param roleId
     * @return
     */
    public List<Menu> getMenus(int roleId);

    /**
     * get menus for user of ${userId}
     *
     * @param userId
     * @return
     */
    public List<Menu> getMenusForUser(int userId);
}
