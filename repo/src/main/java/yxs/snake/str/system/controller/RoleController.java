package yxs.snake.str.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxs.eric.craft.account.model.Role;
import com.yxs.eric.craft.account.service.RoleService;

/**
 * Created by popo on 14-8-14.
 */
@RequestMapping("/role")
@Controller
public class RoleController {
	@Resource
	RoleService roleService;

	/**
	 * get role list
	 * 
	 * @param condition
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(@RequestParam Map<String, Object> condition,
			@RequestParam("page") int page, @RequestParam("rows") int rows) {
		Map<String,Object> retMap = new HashMap<String,Object>();
		List<Role> roles = roleService.getRoleList(condition, page, rows);
		int total = roleService.getRoleCount(condition);
		retMap.put("total", total);
		retMap.put("rows", roles);
		return retMap;
	}
	
	/**
	 * role without page
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("/list/all")
	@ResponseBody
	public List<Role> listAll(@RequestParam Map<String,Object> condition) {
		List<Role> roles = roleService.getRoleList(condition, 1, 0);
		return roles;
	}

	/**
	 * add a role
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public int add(Role role) {
		int rows = roleService.addRole(role);
		if (rows > 0 ) 
			return 0;
		else
			return 1;
	}
	
	/**
	 * edit a role
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public int edit(@RequestParam("roleId") int roleId, Role role) {
		int rows = roleService.editRole(role.getId(), role);
		if (rows > 0)
			return 0;
		else 
			return 1;
	}
	
	/**
	 * set role permissions
	 * 
	 * @param roleId
	 * @param pIds like 1,2,3    [not ended with ',']
	 * @return 
	 */
	@RequestMapping("/setPermission")
	@ResponseBody
	public int setPermission(@RequestParam("roleId") int roleId, @RequestParam("pIds") String pIds) {
		int ret = -1;
		if (!StringUtils.isEmpty(pIds)) {
			String[] ids = pIds.split(",");
			int[] permissionIds = new int[ids.length];
			for (int i = 0; i < ids.length; i++) {
				permissionIds[i] = Integer.parseInt(ids[i]);
			}
			boolean bSucc = roleService.setRolePermission(roleId, permissionIds);
			if (bSucc) 
				ret = 0;
			else 
				ret = 1;
		}
		return ret;
	}
	
	// TODO set menu and permission
	
}
 