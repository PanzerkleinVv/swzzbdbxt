package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.core.util.ApplicationUtils;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.UserMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.UserExample;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.UserExample.Criteria;
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
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<User> selectList() {
		UserExample example = new UserExample();
		example.createCriteria().andIdIsNotNull().andStateNotEqualTo(0);
		example.setOrderByClause("ID");
		return userMapper.selectByExample(example);
	}

	@Override
	public List<User> selectByRoleId(Long roleId) {
		UserExample example = new UserExample();
		example.createCriteria().andRoleIdEqualTo(roleId).andStateNotEqualTo(0);
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

	@Override
	public boolean checkUsername(User user) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		if (user.getId() != null) {
			criteria.andIdNotEqualTo(user.getId());
		}
		long count = userMapper.countByExample(example);
		if (count > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<User> selectByUserIds(List<Long> userIds) {
		if (userIds != null && userIds.size() > 0) {
			UserExample example = new UserExample();
			example.createCriteria().andIdIn(userIds);
			return userMapper.selectByExample(example);
		}
		return null;
	}

	@Override
	public List<User> selectByRoleIdList(List<Long> roleIdList) {
		List<User> users = new ArrayList<User>();
		for(Long roleId : roleIdList) {
			UserExample example = new UserExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andStateNotEqualTo(0);
			example.setOrderByClause("PERMISSION_ID");
			List<User> userList = userMapper.selectByExample(example);
			for(User user : userList) {
				users.add(user);
			}
		}
		return users;
	}
}
