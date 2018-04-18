package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Code;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Service;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Status;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Type;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.ServiceService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.StatusService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.TypeService;

@Controller
@RequestMapping(value = "/code")
public class CodeController {

	@Resource
	private ServiceService serviceService;
	@Resource
	private StatusService statusService;
	@Resource
	private TypeService typeService;

	@RequestMapping(value = "/menu")
	@RequiresRoles(value = RoleSign.GD)
	public String menu() {
		return "codeMenu";
	}

	@RequestMapping(value = "/service")
	public String service(Model model, HttpServletRequest request) {
		final String method = request.getParameter("method");
		System.out.println(method);
		final List<Service> services = serviceService.selectList();
		final List<Code> codes = new ArrayList<>();
		for (Service service : services) {
			codes.add(new Code(service));
		}
		if ("select".equals(method)) {
			model.addAttribute("method", "select");
		}
		model.addAttribute("codes", codes);
		model.addAttribute("codeType", "service");
		return "codeList";
	}

	@RequestMapping(value = "/status")
	public String status(Model model, HttpServletRequest request) {
		final String method = request.getParameter("method");
		final List<Status> statuses = statusService.selectList();
		final List<Code> codes = new ArrayList<>();
		for (Status status : statuses) {
			codes.add(new Code(status));
		}
		if ("select".equals(method)) {
			model.addAttribute("method", "select");
		} else if ("select1".equals(method)) {
			model.addAttribute("method", "select1");
		}
		model.addAttribute("codes", codes);
		model.addAttribute("codeType", "status");
		return "codeList";
	}

	@RequestMapping(value = "/type")
	public String type(Model model, HttpServletRequest request) {
		final String method = request.getParameter("method");
		final List<Type> types = typeService.selectList();
		final List<Code> codes = new ArrayList<>();
		for (Type type : types) {
			codes.add(new Code(type));
		}
		if ("select".equals(method)) {
			model.addAttribute("method", "select");
		}
		model.addAttribute("codes", codes);
		model.addAttribute("codeType", "type");
		return "codeList";
	}

	@RequiresRoles(value = RoleSign.GD)
	@RequestMapping(value = "/save")
	public String save(@Valid Code code, Model model, HttpServletRequest request) {
		final String codeType = request.getParameter("type");
		final String method = request.getParameter("method");
		final List<Code> codes = new ArrayList<>();
		switch (codeType) {
		case "type":
			final Type type0 = new Type();
			type0.setTypeId(code.getId());
			type0.setTypeName(code.getName());
			if ("update".equals(method)) {
				typeService.update(type0);
			} else if ("insert".equals(method)) {
				typeService.insert(type0);
			}
			final List<Type> types = typeService.selectList();
			for (Type type : types) {
				codes.add(new Code(type));
			}
			model.addAttribute("codeType", "type");
			break;
		case "status":
			final Status status0 = new Status();
			status0.setStatusId(code.getId());
			status0.setStatusName(code.getName());
			if ("update".equals(method)) {
				statusService.update(status0);
			} else if ("insert".equals(method)) {
				statusService.insert(status0);
			}
			final List<Status> statuses = statusService.selectList();
			for (Status status : statuses) {
				codes.add(new Code(status));
			}
			model.addAttribute("codeType", "status");
			break;
		case "service":
			final Service service0 = new Service();
			service0.setServiceId(code.getId());
			service0.setServiceName(code.getName());
			if ("update".equals(method)) {
				serviceService.update(service0);
			} else if ("insert".equals(method)) {
				serviceService.insert(service0);
			}
			final List<Service> services = serviceService.selectList();
			for (Service service : services) {
				codes.add(new Code(service));
			}
			model.addAttribute("codeType", "service");
			break;
		}
		model.addAttribute("codes", codes);
		return "codeList";
	}
}
