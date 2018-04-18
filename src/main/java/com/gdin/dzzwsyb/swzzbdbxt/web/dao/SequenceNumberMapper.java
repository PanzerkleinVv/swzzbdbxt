package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SequenceNumber;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SequenceNumberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 立项号表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface SequenceNumberMapper extends GenericDao<SequenceNumber, Integer> {
	long countByExample(SequenceNumberExample example);

	int deleteByExample(SequenceNumberExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(SequenceNumber record);

	int insertSelective(SequenceNumber record);

	List<SequenceNumber> selectByExample(SequenceNumberExample example);

	SequenceNumber selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") SequenceNumber record,
			@Param("example") SequenceNumberExample example);

	int updateByExample(@Param("record") SequenceNumber record, @Param("example") SequenceNumberExample example);

	int updateByPrimaryKeySelective(SequenceNumber record);

	int updateByPrimaryKey(SequenceNumber record);
}