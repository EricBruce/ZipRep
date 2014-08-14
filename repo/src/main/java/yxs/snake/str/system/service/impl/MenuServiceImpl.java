package yxs.snake.str.system.service.impl;

import org.springframework.stereotype.Service;
import yxs.snake.str.system.model.Menu;
import yxs.snake.str.system.service.MenuService;

import java.util.List;

/**
 * Created by popo on 14-8-14.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Override
    public List<Menu> getMenus(int roleId) {
        return null;
    }

    @Override
    public List<Menu> getMenusForUser(int userId) {
        return null;
    }
}
