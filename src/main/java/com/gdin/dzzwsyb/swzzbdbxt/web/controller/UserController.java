package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdin.dzzwsyb.swzzbdbxt.core.util.SelectArray;
import com.gdin.dzzwsyb.swzzbdbxt.web.enums.MessageColor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Permission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Role;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.PermissionService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.RoleService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.UserService;

/**
 * 用户控制器
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
		try {
			Subject subject = SecurityUtils.getSubject();
			// 已登陆则 跳到首页
			if (subject.isAuthenticated()) {
				return "redirect:/";
			}
			if (result.hasErrors()) {
				model.addAttribute("error", "参数错误！");
				return "login";
			}
			// 身份验证
			subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
			// 验证成功在Session中保存用户信息
			final User authUserInfo = userService.selectByUsername(user.getUsername());
			final List<Role> role = roleService.selectRolesByUserId(authUserInfo.getId());
			final List<Permission> permission = permissionService.selectPermissionsByUserId(authUserInfo.getId());
			request.getSession().setAttribute("userInfo", authUserInfo);
			request.getSession().setAttribute("roleId", role.get(0).getId());
			request.getSession().setAttribute("permissionId", permission.get(0).getId());
			final List<Role> roles = roleService.selectList();
			final Map<Long, String> roleMap = new HashMap<Long, String>();
			for (Role role0 : roles) {
				roleMap.put(role0.getId(), role0.getRoleName());
			}
			final List<Permission> permissions = permissionService.selectList();
			final Map<Long, String> permissionMap = new HashMap<Long, String>();
			for (Permission permission0 : permissions) {
				System.out.println("=========="+permission0.getPermissionName());
				permissionMap.put(permission0.getId(), permission0.getPermissionName());
			}
			final List<User> roleUsers = userService.selectByRoleId(role.get(0).getId());
			request.getSession().setAttribute("roles", roles); //处室下拉菜单
			request.getSession().setAttribute("permissions", permissions); //权限下拉菜单
			request.getSession().setAttribute("roleMap", roleMap); //处室名显示映射
			request.getSession().setAttribute("permissionMap", permissionMap); //权限名显示映射
			request.getSession().setAttribute("userState", SelectArray.getUserState()); //用户状态下拉菜单
			request.getSession().setAttribute("roleUsers", roleUsers); //本处室用户下拉菜单
		} catch (AuthenticationException e) {
			// 身份验证失败
			model.addAttribute("error", "用户名或密码错误 ！");
			return "login";
		}
		return "redirect:/";
	}

	/**
	 * 用户登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("userInfo");
		session.removeAttribute("roleId");
		session.removeAttribute("permissionId");
		session.removeAttribute("roles");
		session.removeAttribute("permissions");
		session.removeAttribute("roleMap");
		session.removeAttribute("permissionMap");
		// 登出操作
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}

	/**
	 * 用户查询页面
	 */
	@RequestMapping(value = "/admin")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String admin(@Valid User user, Model model) {
		if (user == null || user.isEmpty()) {
			final List<User> users = userService.selectList();
			model.addAttribute("users", users);
			model.addAttribute("selectId", 0);
			model.addAttribute("navigationBar", "所有");
			return "admin";
		} else if (user.getUserdesc() != null && !"".equals(user.getUserdesc())) {
			final List<User> users = userService.searchUser(user.getUserdesc().trim());
			model.addAttribute("users", users);
			model.addAttribute("selectId", 0);
			if (users.size() == 0) {
				model.addAttribute("msg0", MessageColor.FAILURE.getColor());
				model.addAttribute("msg", "未找到用户");
			}
			model.addAttribute("userdesc", user.getUserdesc().trim());
			model.addAttribute("navigationBar", "“" + user.getUserdesc().trim() + "” 搜索结果");
			return "admin";
		} else if (user.getRoleId() != null && 0 != user.getRoleId()) {
			final List<User> users = userService.selectByRoleId(user.getRoleId());
			final Role role = roleService.selectById(user.getRoleId());
			model.addAttribute("users", users);
			model.addAttribute("selectId", role.getId());
			model.addAttribute("navigationBar", role.getRoleName());
			return "admin";
		} else {
			final List<User> users = userService.selectList();
			model.addAttribute("msg0", MessageColor.FAILURE.getColor());
			model.addAttribute("msg", "未找到用户");
			model.addAttribute("users", users);
			model.addAttribute("selectId", 0);
			model.addAttribute("navigationBar", "所有");
			return "admin";
		}
	}

	@RequestMapping(value = "/info")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String info(@Valid User user, Model model) {
		if (user == null || user.isEmpty()) {
			model.addAttribute("method", "新增");
			model.addAttribute("navigationBar", "新增 用户信息");
			return "info";
		} else {
			final User user0 = userService.selectById(user.getId());
			model.addAttribute("user", user0);
			model.addAttribute("method", "修改");
			model.addAttribute("navigationBar", user0.getUserdesc() + " 用户信息");
			return "info";
		}
	}

	@RequestMapping(value = "/modify")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String create(@Valid User user, Model model) {
		if (user == null || user.isEmpty()) {
			final User user0 = new User();
			model.addAttribute("user", user0);
			model.addAttribute("method", "新增");
			model.addAttribute("msg0", MessageColor.FAILURE.getColor());
			model.addAttribute("msg", "请填写用户信息");
			model.addAttribute("navigationBar", "新增 用户信息");
			return "info";
		} else if (user.getId() == null || 0 == user.getId()) {
			user.setPassword(DigestUtils.sha256Hex("123456"));
			user.setState(1);
			final int count = userService.insert(user);
			if (count > 0) {
				final List<User> users = userService.selectList();
				model.addAttribute("users", users);
				model.addAttribute("msg0", MessageColor.SUCCESS.getColor());
				model.addAttribute("msg", "新增用户成功");
				model.addAttribute("navigationBar", "所有");
				model.addAttribute("selectId", 0);
				return "admin";
			} else {
				model.addAttribute("user", user);
				model.addAttribute("method", "新增");
				model.addAttribute("msg0", MessageColor.FAILURE.getColor());
				model.addAttribute("msg", "请确认用户信息是否正确");
				model.addAttribute("navigationBar", "新增 用户信息");
				return "info";
			}
		} else {
			final int count = userService.update(user);
			if (count > 0) {
				final List<User> users = userService.selectList();
				model.addAttribute("users", users);
				model.addAttribute("msg0", MessageColor.SUCCESS.getColor());
				model.addAttribute("msg", "修改用户成功");
				model.addAttribute("navigationBar", "所有");
				model.addAttribute("selectId", 0);
				return "admin";
			} else {
				model.addAttribute("user", user);
				model.addAttribute("method", "修改");
				model.addAttribute("msg0", MessageColor.FAILURE.getColor());
				model.addAttribute("msg", "请确认用户信息是否正确");
				model.addAttribute("navigationBar", user.getUserdesc() + " 用户信息");
				return "info";
			}
		}
	}

	@RequestMapping(value = "/delete")
	@RequiresRoles(value = RoleSign.ADMIN)
	public String delete(@Valid User user, Model model) {
		if (user == null || user.isEmpty()) {
			final List<User> users = userService.selectList();
			model.addAttribute("users", users);
			model.addAttribute("msg0", MessageColor.FAILURE.getColor());
			model.addAttribute("msg", "删除失败：找不到该用户");
			model.addAttribute("navigationBar", "所有");
			model.addAttribute("selectId", 0);
			return "admin";
		} else if (user.getId() == null || 0 == user.getId()) {
			final List<User> users = userService.selectList();
			model.addAttribute("users", users);
			model.addAttribute("msg0", MessageColor.FAILURE.getColor());
			model.addAttribute("msg", "删除失败：用户ID不存在");
			model.addAttribute("navigationBar", "所有");
			model.addAttribute("selectId", 0);
			return "admin";
		} else {
			final int count = userService.delete(user.getId());
			if (count > 0) {
				final List<User> users = userService.selectList();
				model.addAttribute("users", users);
				model.addAttribute("msg0", MessageColor.SUCCESS.getColor());
				model.addAttribute("msg", "删除用户成功");
				model.addAttribute("navigationBar", "所有");
				model.addAttribute("selectId", 0);
				return "admin";
			} else {
				model.addAttribute("user", user);
				model.addAttribute("method", "修改");
				model.addAttribute("msg0", MessageColor.FAILURE.getColor());
				model.addAttribute("msg", "删除失败：请确认用户信息是否正确");
				model.addAttribute("navigationBar", user.getUserdesc() + " 用户信息");
				return "info";
			}
		}
	}


}
