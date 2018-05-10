package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.User;

/**
 * 用户 业务 接口
 * 
 **/
public interface UserService extends GenericService<User, Long> {

	/**
	 * 用户验证
	 * 
	 * @param user
	 *            -用户对象
	 * @return User -用户对象
	 */
	User authentication(User user);

	/**
	 * 根据大组工网id查询用户
	 * 
	 * @param username
	 *            -大组工网id
	 * @return User -用户对象
	 */
	User selectByUsername(String username);

	/**
	 * 根据用户姓名查询用户
	 * 
	 * @param userdesc
	 *            -用户姓名
	 * @return List<User> -用户对象集合
	 */
	List<User> searchUser(String userdesc);

	/**
	 * 根据处室id查询用户
	 * 
	 * @param roleId
	 * @return List<User> -用户对象集合
	 */
	List<User> selectByRoleId(Long roleId);

	boolean checkUsername(User user);
	
	List<User> selectByUserIds(List<Long> userIds);
	
	List<User> selectByRoleIdList(List<Long> roleIdList);
}
