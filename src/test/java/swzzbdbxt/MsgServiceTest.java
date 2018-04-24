package swzzbdbxt;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdin.dzzwsyb.swzzbdbxt.web.model.MsgExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.MsgService;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.SequenceNumberService;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations ={"classpath:applicationContext.xml"}) 
public class MsgServiceTest {
	

	//@Resource
	private MsgService service;
	
	@Resource
	private SequenceNumberService service1;

	//@Test
	public void testQuery() {
		MsgExample example = new MsgExample();
		example.createCriteria().andIdIsNotNull();
		service.selectByExampleAndPage(example, 1);
	}
	
	@Test
	public void testNext() {
		Assert.assertEquals(Integer.getInteger("20180001"), service1.next());
	}
}
