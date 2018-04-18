package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdin.dzzwsyb.swzzbdbxt.core.entity.DaoException;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Permission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Role;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.PermissionService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.RoleService;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

	@Resource
	private RoleService roleService;
	@Resource
	private PermissionService permissionService;

	@RequestMapping(value = "/list")
	public String list(Model model, HttpSession session, HttpServletRequest request) {
		final Integer roleId = (Integer) session.getAttribute("roleId");
		if (roleId != null && roleId > 0) {
			final Role role = roleService.selectById(roleId);
			final Long level = roleService.selectLevelById(roleId);
			if ("select".equals(request.getParameter("method"))) {
				model.addAttribute("method", "select");
			} else if ("selectNoCheck".equals(request.getParameter("method"))) {
				model.addAttribute("method", "selectNoCheck");
			}
			model.addAttribute("role", role);
			model.addAttribute("level", level);
		} else {
			model.addAttribute("role", null);
		}
		return "roleList";
	}

	@RequestMapping(value = "/openList")
	@RequiresRoles(value = { RoleSign.GD, RoleSign.GZ, RoleSign.SZ, RoleSign.ZH, RoleSign.ST, RoleSign.FS, RoleSign.SG,
			RoleSign.HY, RoleSign.MZ, RoleSign.HZ, RoleSign.SW, RoleSign.DG, RoleSign.ZS, RoleSign.JM, RoleSign.YJ,
			RoleSign.ZJ, RoleSign.MM, RoleSign.ZQ, RoleSign.QY, RoleSign.CZ, RoleSign.JY, RoleSign.YF }, logical = Logical.OR)
	public String openList(@Valid Role role, Model model, HttpServletRequest request) {
		if (role != null && role.getRoleId() != null) {
			final List<Role> roles = roleService.selectListById(role.getRoleId());
			final Long level = roleService.selectLevelById(role.getRoleId()) + 1L;
			model.addAttribute("roles", roles);
			model.addAttribute("level", level);
			model.addAttribute("method", request.getParameter("method"));
			model.addAttribute("roleId", role.getRoleId());
		} else {
			model.addAttribute("roles", null);
		}
		return "addList";
	}

	@RequestMapping(value = "/addRole")
	@RequiresRoles(value = { RoleSign.GD, RoleSign.GZ, RoleSign.SZ, RoleSign.ZH, RoleSign.ST, RoleSign.FS, RoleSign.SG,
			RoleSign.HY, RoleSign.MZ, RoleSign.HZ, RoleSign.SW, RoleSign.DG, RoleSign.ZS, RoleSign.JM, RoleSign.YJ,
			RoleSign.ZJ, RoleSign.MM, RoleSign.ZQ, RoleSign.QY, RoleSign.CZ, RoleSign.JY, RoleSign.YF }, logical = Logical.OR)
	public String addRole(Model model, HttpServletRequest request) {
		final String pId = request.getParameter("pId");
		if (pId != null && !"".equals(pId))
			model.addAttribute("pId", pId);
		return "addRole";
	}

	@Transactional
	@RequestMapping(value = "/addRoleSubmit")
	@RequiresRoles(value = { RoleSign.GD, RoleSign.GZ, RoleSign.SZ, RoleSign.ZH, RoleSign.ST, RoleSign.FS, RoleSign.SG,
			RoleSign.HY, RoleSign.MZ, RoleSign.HZ, RoleSign.SW, RoleSign.DG, RoleSign.ZS, RoleSign.JM, RoleSign.YJ,
			RoleSign.ZJ, RoleSign.MM, RoleSign.ZQ, RoleSign.QY, RoleSign.CZ, RoleSign.JY, RoleSign.YF }, logical = Logical.OR)
	public String addRoleSubmit(@Valid Role role, Model model, HttpServletRequest request, HttpSession session) {
		final Integer pId = new Integer(request.getParameter("pId"));
		final Integer myRoleId = (Integer) session.getAttribute("roleId");
		if (pId != null && !"".equals(pId)) {
			if (role != null && !role.isEmpty()) {
				final int count1 = roleService.insert(role);
				if (count1 < 1) {
					throw new DaoException();
				}
				final Role role0 = roleService.selectListByName(role.getRoleName()).get(0);
				final Permission permission = new Permission();
				permission.setPermId(role0.getRoleId());
				permission.setPermName(role0.getRoleName());
				permission.setPermSign(role0.getRoleSign());
				permission.setPermDesc(role0.getRoleDesc());
				final int count2 = permissionService.insert(permission);
				if (count2 < 1) {
					throw new DaoException();
				}
				final int count3 = roleService.insertRolePermission(pId, myRoleId, role0.getRoleId());
				if(count3 != 0) {
					throw new DaoException();
				}
			}
			final List<Role> roles = roleService.selectListById(pId);
			final Long level = roleService.selectLevelById(pId) + 1L;
			model.addAttribute("roles", roles);
			model.addAttribute("level", level);
			model.addAttribute("roleId", pId);
		} else {
			model.addAttribute("roles", null);
		}
		return "addList";
	}
}
