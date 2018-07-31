package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Analysis;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnalysisMapper {

	List<Analysis> AnalysisByRoleAll();

	List<Analysis> AnalysisByRoleMain();

	List<Analysis> AnalysisByYear(Integer year);

	List<Analysis> AnalysisRoleByYear(Integer year);

	List<Analysis> AnalysisRoleByMonth(@Param("year") Integer year, @Param("month") Integer month);

	List<Analysis> AnalysisRoleByYearMain(Integer year);

	List<Analysis> AnalysisRoleByMonthMain(@Param("year") Integer year, @Param("month") Integer month);
	
	List<Integer> getYears();

}