package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Attach;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.AttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 附件表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface AttachMapper extends GenericDao<Attach, String> {
	long countByExample(AttachExample example);

	int deleteByExample(AttachExample example);

	int deleteByPrimaryKey(String id);

	int insert(Attach record);

	int insertSelective(Attach record);

	List<Attach> selectByExample(AttachExample example);

	Attach selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Attach record, @Param("example") AttachExample example);

	int updateByExample(@Param("record") Attach record, @Param("example") AttachExample example);

	int updateByPrimaryKeySelective(Attach record);

	int updateByPrimaryKey(Attach record);
}