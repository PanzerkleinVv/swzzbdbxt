package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.SequenceNumber;

public interface SequenceNumberService extends GenericService<SequenceNumber, Integer> {

	Integer next();

	Integer next(Integer year);
}
