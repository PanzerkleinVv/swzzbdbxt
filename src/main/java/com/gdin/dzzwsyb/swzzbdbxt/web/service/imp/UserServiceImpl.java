package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;
import javax.annotation.Resource;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.UserMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.UserExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户Service实现类
 *
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public int insert(User model) {
		return userMapper.insertSelective(model);
	}

	@Override
	public int update(User model) {
		return userMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int delete(Long id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User authentication(User user) {
		user.setPassword(ApplicationUtils.sha256Hex(user.getPassword()));
		return userMapper.authentication(user);
	}

	@Override
	public User selectById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public GenericDao<User, Long> getDao() {
		return userMapper;
	}

	@Override
	public User selectByUsername(String username) {
		UserExample example = new UserExample();
		example.createCriteria().andUsernameEqualTo(username);
		final List<User> list = userMapper.selectByExample(example);
		return list.get(0);
	}

	@Override
	public List<User> selectList() {
		UserExample example = new UserExample();
		example.createCriteria().andIdIsNotNull();
		example.setOrderByClause("ID");
		return userMapper.selectByExample(example);
	}

	@Override
	public List<User> selectByRoleId(Long roleId) {
		UserExample example = new UserExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		example.setOrderByClause("PERMISSION_ID");
		return userMapper.selectByExample(example);
	}

	@Override
	public List<User> searchUser(String userdesc) {
		UserExample example = new UserExample();
		example.createCriteria().andUserdescLike("%" + userdesc + "%");
		example.setOrderByClause("ID");
		return userMapper.selectByExample(example);
	}
}
