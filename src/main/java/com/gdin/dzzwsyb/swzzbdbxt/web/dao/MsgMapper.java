package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 督办事项表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface MsgMapper extends GenericDao<Msg, String> {
	long countByExample(MsgExample example);

	int deleteByExample(MsgExample example);

	int deleteByPrimaryKey(String id);

	int insert(Msg record);

	int insertSelective(Msg record);

	List<Msg> selectByExampleWithBLOBs(MsgExample example);

	List<Msg> selectByExample(MsgExample example);

	Msg selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Msg record, @Param("example") MsgExample example);

	int updateByExampleWithBLOBs(@Param("record") Msg record, @Param("example") MsgExample example);

	int updateByExample(@Param("record") Msg record, @Param("example") MsgExample example);

	int updateByPrimaryKeySelective(Msg record);

	int updateByPrimaryKeyWithBLOBs(Msg record);

	int updateByPrimaryKey(Msg record);
}