package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsor;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgCoSponsorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 督办事项-协办人表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface MsgCoSponsorMapper extends GenericDao<MsgCoSponsor, String> {
	long countByExample(MsgCoSponsorExample example);

	int deleteByExample(MsgCoSponsorExample example);

	int deleteByPrimaryKey(String id);

	int insert(MsgCoSponsor record);

	int insertSelective(MsgCoSponsor record);

	List<MsgCoSponsor> selectByExample(MsgCoSponsorExample example);

	MsgCoSponsor selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") MsgCoSponsor record, @Param("example") MsgCoSponsorExample example);

	int updateByExample(@Param("record") MsgCoSponsor record, @Param("example") MsgCoSponsorExample example);

	int updateByPrimaryKeySelective(MsgCoSponsor record);

	int updateByPrimaryKey(MsgCoSponsor record);

	List<Long> selectRoleIdByMsgId(String msgId);

	void deleteByMgsId(String msgId);
	
	List<MsgCoSponsor> selectMsgCoSponsorsByMsgId(String msgId);
	
	Long selectByMgsId(String msgId);

}