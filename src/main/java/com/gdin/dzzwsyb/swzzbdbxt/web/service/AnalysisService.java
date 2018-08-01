package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Analysis;

public interface AnalysisService {
	
	List<Analysis> analysis(Analysis condition);
	
	List<Integer> getYears();
	
	List<Integer> getMonths(Integer year);

}
