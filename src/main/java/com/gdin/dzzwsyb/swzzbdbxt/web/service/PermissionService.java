package com.gdin.dzzwsyb.swzzbdbxt.web.service;

import java.util.List;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericService;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Permission;

/**
 * 权限 业务接口
 * 
 * @since 2014年6月10日 下午12:02:39
 **/
public interface PermissionService extends GenericService<Permission, Long> {

	/**
	 * 通过角色id 查询角色 拥有的权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<Permission> selectPermissionsByUserId(Long userId);

}
