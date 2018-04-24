package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgContractorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 督办事项-承办人表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface MsgContractorMapper extends GenericDao<MsgContractor, String> {
	long countByExample(MsgContractorExample example);

	int deleteByExample(MsgContractorExample example);

    int deleteByPrimaryKey(String id);

	int insert(MsgContractor record);

	int insertSelective(MsgContractor record);

	List<MsgContractor> selectByExample(MsgContractorExample example);

    MsgContractor selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") MsgContractor record, @Param("example") MsgContractorExample example);

	int updateByExample(@Param("record") MsgContractor record, @Param("example") MsgContractorExample example);

	int updateByPrimaryKeySelective(MsgContractor record);

	int updateByPrimaryKey(MsgContractor record);
}