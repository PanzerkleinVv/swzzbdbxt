package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.Calendar;
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
	public List<Analysis> AnalysisByRole(Integer flag) {
		if (flag != null && flag == 1) {
			return analysisMapper.AnalysisByRoleMain();
		} else {
			return analysisMapper.AnalysisByRoleAll();
		}
	}

	@Override
	public List<Analysis> AnalysisByYear(Integer year) {
		if (year == null || year == 0) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		return analysisMapper.AnalysisByYear(year);
	}

	@Override
	public List<Analysis> AnalysisRoleByYear(Integer year, Integer flag) {
		if (year == null || year == 0) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		if (flag != null && flag == 1) {
			return analysisMapper.AnalysisRoleByYearMain(year);
		} else {
			return analysisMapper.AnalysisRoleByYear(year);
		}
	}

	@Override
	public List<Analysis> AnalysisRoleByMonth(Integer year, Integer month, Integer flag) {
		if ((year == null || year == 0) && (month == null || month == 0)) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH) + 1;
		} else if (year == null || year == 0) {
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		} else if (month == null || month == 0) {
			month = 1;
		}
		if (flag != null && flag == 1) {
			return analysisMapper.AnalysisRoleByMonthMain(year, month);
		} else {
			return analysisMapper.AnalysisRoleByMonth(year, month);
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
