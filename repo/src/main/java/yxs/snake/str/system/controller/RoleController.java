package yxs.snake.str.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import yxs.snake.str.system.service.RoleService;

import javax.annotation.Resource;

/**
 * Created by popo on 14-8-14.
 */
@RequestMapping("/role")
@Controller
public class RoleController {
    @Resource
    RoleService roleService;

}
