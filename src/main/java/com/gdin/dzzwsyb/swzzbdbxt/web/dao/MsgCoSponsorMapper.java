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
public interface MsgCoSponsorMapper extends GenericDao<MsgCoSponsor, Long> {
	long countByExample(MsgCoSponsorExample example);

	int deleteByExample(MsgCoSponsorExample example);

	int deleteByPrimaryKey(Long id);

	int insert(MsgCoSponsor record);

	int insertSelective(MsgCoSponsor record);

	List<MsgCoSponsor> selectByExample(MsgCoSponsorExample example);

	MsgCoSponsor selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") MsgCoSponsor record, @Param("example") MsgCoSponsorExample example);

	int updateByExample(@Param("record") MsgCoSponsor record, @Param("example") MsgCoSponsorExample example);

	int updateByPrimaryKeySelective(MsgCoSponsor record);

	int updateByPrimaryKey(MsgCoSponsor record);
}