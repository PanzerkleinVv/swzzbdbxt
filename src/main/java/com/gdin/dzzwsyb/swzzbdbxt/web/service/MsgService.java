package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.feature.orm.mybatis.Page;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Msg;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;

/**
 * 督办事项 业务接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface MsgService extends GenericService<Msg, String> {

	public Page<Msg> selectByExampleAndPage(MsgExample example, int pageNo);
	
	public int  insertSelective(Msg record);
	
	public List<String> selectOldDataIds();
	
	void deleteByIds(List<String> ids);

}
