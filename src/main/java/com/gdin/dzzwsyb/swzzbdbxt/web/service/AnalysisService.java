package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Analysis;

public interface AnalysisService {
	
	List<Analysis> AnalysisByRole(Integer flag);

	List<Analysis> AnalysisByYear(Integer year);

	List<Analysis> AnalysisRoleByYear(Integer year, Integer flag);

	List<Analysis> AnalysisRoleByMonth(Integer year, Integer month, Integer flag);
	
	List<Integer> getYears();

}
