package swzzbdbxt;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;

public class MsgServiceTest {

	@Resource(name = "testService")
	private MsgService service;

	@Test
	public void testQuery() {
		MsgExample example = new MsgExample();
		example.createCriteria().andMsgIdIsNotNull();
		service.selectByExampleAndPage(example, 1);
	}

}
