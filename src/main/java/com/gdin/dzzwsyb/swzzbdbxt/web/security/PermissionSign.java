package com.gdin.dzzwsyb.swzzbdbxt.web.security;

/**
 * 权限标识配置类, <br>
 * 与 permission 权限表 中的 permission_sign 字段 相对应 <br>
 * 使用:
 * 
 * <pre>
 * &#064;RequiresPermissions(value = PermissionConfig.USER_CREATE)
 * public String create() {
 * 	return &quot;拥有user:create权限,能访问&quot;;
 * }
 * </pre>
 * 
 * @author StarZou
 * @since 2014年6月17日 下午3:58:51
 **/
public class PermissionSign {

	public static final String ADMIN = "admin";

	public static final String BU_LING_DAO = "1-1";

	public static final String BAN_GONG_SHI_GUAN_LI = "1-2";

	public static final String CHU_SHI_FU_ZE_REN = "2-1";

	public static final String CHU_SHI_NEI_QIN = "2-2";

	public static final String CHENG_BAN_REN = "3";

}
