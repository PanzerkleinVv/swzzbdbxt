package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.SequenceNumberMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SequenceNumber;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SequenceNumberService;

@Service
public class SequenceNumberServiceImpl extends GenericServiceImpl<SequenceNumber, Integer>
		implements SequenceNumberService {

	@Resource
	private SequenceNumberMapper sequenceNumberMapper;

	@Override
	public int insert(SequenceNumber model) {
		return 0;
	}

	@Override
	public int update(SequenceNumber model) {
		return 0;
	}

	@Override
	public int delete(Integer id) {
		return 0;
	}

	@Override
	public SequenceNumber selectById(Integer id) {
		return null;
	}

	@Override
	public SequenceNumber selectOne() {
		return null;
	}

	@Override
	public List<SequenceNumber> selectList() {
		return null;
	}

	@Override
	public GenericDao<SequenceNumber, Integer> getDao() {
		return sequenceNumberMapper;
	}

	@Override
	public Integer next() {
		return next(new Integer(Calendar.getInstance().get(Calendar.YEAR)));
	}

	@Override
	public Integer next(Integer year) {
		SequenceNumber sequenceNumber = sequenceNumberMapper.selectByPrimaryKey(year);
		if (sequenceNumber != null) {
			sequenceNumber.setCount(sequenceNumber.getCount() + 1);
			sequenceNumberMapper.updateByPrimaryKeySelective(sequenceNumber);
		} else {
			sequenceNumber = new SequenceNumber();
			sequenceNumber.setId(year);
			sequenceNumber.setCount(1);
			sequenceNumberMapper.insertSelective(sequenceNumber);
		}
		final String sequenceString = year.toString() + String.format("%04d", sequenceNumber.getCount());
		return new Integer(sequenceString);
	}

}
