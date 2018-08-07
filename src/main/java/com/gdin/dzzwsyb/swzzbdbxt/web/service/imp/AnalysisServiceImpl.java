package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.web.dao.AnalysisMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Analysis;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.AnalysisService;

@Service
public class AnalysisServiceImpl implements AnalysisService {
	@Resource
	private AnalysisMapper analysisMapper;
	
	@Override
	public List<Analysis> analysis(Analysis condition) {
		Integer type = condition.getType();
		Integer year = condition.getYear();
		Integer month = condition.getMonth();
		if (type == null && year == null && month == null) {
			return null;
		} else if (type == null && year != null && month == null) {
			return analysisMapper.analysisByYear(year);
		} else if (type == 0 && year != null && month == 0) {
			return analysisMapper.analysisRoleByYear(year);
		} else if (type == 1 && year != null && month == 0) {
			return analysisMapper.analysisRoleByYearMain(year);
		} else if (type == 0 && year != null && month != null && month != 0) {
			return analysisMapper.analysisRoleByMonth(year, month);
		} else if (type == 1 && year != null && month != null && month != 0) {
			return analysisMapper.analysisRoleByMonthMain(year, month);
		} else if (type == 0 && year == null && month == null) {
			return analysisMapper.analysisByRoleAll();
		} else if (type == 1 && year == null && month == null) {
			return analysisMapper.analysisByRoleMain();
		} else {
			return null;
		}
	}

	@Override
	public List<Integer> getYears() {
		return analysisMapper.getYears();
	}
	
	@Override
	public List<Integer> getMonths(Integer year){
		return analysisMapper.getMonths(year);
	}

}
