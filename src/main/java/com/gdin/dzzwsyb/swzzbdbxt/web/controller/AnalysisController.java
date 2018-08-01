package com.gdin.dzzwsyb.swzzbdbxt.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Analysis;
import com.gdin.dzzwsyb.swzzbdbxt.web.security.RoleSign;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AnalysisService;

@Controller
@RequestMapping(value = "/analysis")
public class AnalysisController {
	// 附件类业务接口
	@Resource
	private AnalysisService analysisService;
	
	@RequestMapping(value = "/index")
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public String index(Model model) {
		model.addAttribute("years", analysisService.getYears());
		return "analysis";
	}

	@RequestMapping(value = "/getMonths")
	@ResponseBody
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public List<Integer> getMonths(Integer year) {
		return analysisService.getMonths(year);
	}
	
	@RequestMapping(value = "/analysis")
	@ResponseBody
	@RequiresRoles(value = { RoleSign.ADMIN, RoleSign.BAN_GONG_SHI, RoleSign.BU_LING_DAO }, logical = Logical.OR)
	public Map<Integer, List<Analysis>> analysis(Analysis condition) {
		if (condition != null) {
			List<Analysis> result;
			Integer type = condition.getType();
			Integer year = condition.getYear();
			Integer month = condition.getMonth();
			if (type != null && year != null && month != null && month != 0) {
				result = analysisService.AnalysisRoleByMonth(year, month, type);
			} else if (type != null && year != null && month == 0) {
				result = analysisService.AnalysisRoleByMonth(year, month, type);
			}
			return null;
		} else {
			return null;
		}
	}

}
