package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.Analysis;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnalysisMapper {

	List<Analysis> analysisByRoleAll();

	List<Analysis> analysisByRoleMain();

	List<Analysis> analysisByYear(Integer year);

	List<Analysis> analysisRoleByYear(Integer year);

	List<Analysis> analysisRoleByMonth(@Param("year") Integer year, @Param("month") Integer month);

	List<Analysis> analysisRoleByYearMain(Integer year);

	List<Analysis> analysisRoleByMonthMain(@Param("year") Integer year, @Param("month") Integer month);
	
	List<Integer> getYears();
	
	List<Integer> getMonths(Integer year);

}