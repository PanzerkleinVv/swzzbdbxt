package com.gdin.dzzwsyb.swzzbdbxt.web.service.imp;

import java.util.List;
import javax.annotation.Resource;

import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericDao;
import com.gdin.dzzwsyb.swzzbdbxt.core.generic.GenericServiceImpl;
import com.gdin.dzzwsyb.swzzbdbxt.web.dao.PermissionMapper;
import com.gdin.dzzwsyb.swzzbdbxt.web.model.Permission;
import com.gdin.dzzwsyb.swzzbdbxt.web.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * 权限Service实现类
 *
 * @since 2014年6月10日 下午12:05:03
 */
@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Long> implements PermissionService {

	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public GenericDao<Permission, Long> getDao() {
		return permissionMapper;
	}

	@Override
	public List<Permission> selectPermissionsByUserId(Long userId) {
		return permissionMapper.selectPermissionsByUserId(userId);
	}
}
