package com.gdin.dzzwsyb.swzzbdbxt.web.dao;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Notice;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeCount;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.NoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 通知提醒表DAO接口
 * 
 * @author PanzerkleinVv
 *
 */
public interface NoticeMapper extends GenericDao<Notice, Long> {
	long countByExample(NoticeExample example);

	int deleteByExample(NoticeExample example);

	int deleteByPrimaryKey(Long id);

	int insert(Notice record);

	int insertSelective(Notice record);

	List<Notice> selectByExample(NoticeExample example);

	Notice selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeExample example);

	int updateByExample(@Param("record") Notice record, @Param("example") NoticeExample example);

	int updateByPrimaryKeySelective(Notice record);

	int updateByPrimaryKey(Notice record);

	List<NoticeCount> countNotice(Long userid);
	
	


}