package yxs.eric.craft.account.provider.service;

import com.yxs.eric.craft.account.model.Menu;
import com.yxs.eric.craft.account.service.MenuService;
import org.springframework.stereotype.Service;

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
