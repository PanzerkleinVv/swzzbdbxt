package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Submission;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SubmissionExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SubmissionWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 提请事项表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface SubmissionMapper extends GenericDao<Submission, String> {
	long countByExample(SubmissionExample example);

	int deleteByExample(SubmissionExample example);

	int deleteByPrimaryKey(String id);

	int insert(SubmissionWithBLOBs record);

	int insertSelective(SubmissionWithBLOBs record);

	List<SubmissionWithBLOBs> selectByExampleWithBLOBs(SubmissionExample example);

	List<Submission> selectByExample(SubmissionExample example);

	SubmissionWithBLOBs selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") SubmissionWithBLOBs record,
			@Param("example") SubmissionExample example);

	int updateByExampleWithBLOBs(@Param("record") SubmissionWithBLOBs record,
			@Param("example") SubmissionExample example);

	int updateByExample(@Param("record") Submission record, @Param("example") SubmissionExample example);

	int updateByPrimaryKeySelective(SubmissionWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(SubmissionWithBLOBs record);

	int updateByPrimaryKey(Submission record);
}