package com.gdin.dzzwsyb.swzzbdbxt.web.security;

/**
 * 角色标识配置类, <br>
 * 与 role_info 角色表中的 role_sign 字段 相对应 <br>
 * 使用:
 * 
 * <pre>
 * &#064;RequiresRoles(value = RoleSign.ADMIN)
 * public String admin() {
 * 	return &quot;拥有admin角色,能访问&quot;;
 * }
 * </pre>
 * 
 **/
public class RoleSign {

	public static final String ADMIN = "admin";

	public static final String BU_LING_DAO = "1";

	public static final String BAN_GONG_SHI = "2";

	public static final String YAN_JIU_SHI = "3";

	public static final String ZU_ZHI_YI_CHU = "4";

	public static final String ZU_ZHI_ER_CHU = "5";

	public static final String GAN_BU_YI_CHU = "6";

	public static final String SHI_XIAN_GAN_BU_YI_CHU = "7";

	public static final String SHI_XIAN_GAN_BU_ER_CHU = "8";

	public static final String GAN_BU_SAN_CHU = "9";

	public static final String GAN_BU_SI_CHU = "10";

	public static final String GAN_BU_WU_CHU = "11";

	public static final String GAN_BU_LIU_CHU = "12";

	public static final String GAN_BU_PEI_XUN_CHU = "13";

	public static final String GAN_BU_JIAN_DU_SHI = "14";

	public static final String REN_CAI_GONG_ZUO_CHU = "15";

	public static final String XIN_XI_CHU = "16";

	public static final String ZHU_BU_JI_JIAN_ZU = "17";

}
