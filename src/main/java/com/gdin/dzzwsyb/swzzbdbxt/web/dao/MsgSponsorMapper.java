package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgSponsorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 督办事项-承办人表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface MsgSponsorMapper extends GenericDao<MsgSponsor, Long> {
	long countByExample(MsgSponsorExample example);

	int deleteByExample(MsgSponsorExample example);

	int deleteByPrimaryKey(Long id);

	int insert(MsgSponsor record);

	int insertSelective(MsgSponsor record);

	List<MsgSponsor> selectByExample(MsgSponsorExample example);

	MsgSponsor selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") MsgSponsor record, @Param("example") MsgSponsorExample example);

	int updateByExample(@Param("record") MsgSponsor record, @Param("example") MsgSponsorExample example);

	int updateByPrimaryKeySelective(MsgSponsor record);

	int updateByPrimaryKey(MsgSponsor record);
}