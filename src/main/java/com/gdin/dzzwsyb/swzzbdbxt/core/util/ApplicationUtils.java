package com.gdin.dzzwsyb.swzzbdbxt.core.util;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * ApplicationUtils : 程序工具类，提供大量的便捷方法
 *
 */
public class ApplicationUtils {

	/**
	 * 产生一个36个字符的UUID
	 *
	 * @return UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * md5加密
	 *
	 * @param value
	 *            要加密的值
	 * @return md5加密后的值
	 */
	public static String md5Hex(String value) {
		return DigestUtils.md5Hex(value);
	}

	/**
	 * sha1加密
	 *
	 * @param value
	 *            要加密的值
	 * @return sha1加密后的值
	 */
	public static String sha1Hex(String value) {
		return DigestUtils.sha1Hex(value);
	}

	/**
	 * sha256加密
	 *
	 * @param value
	 *            要加密的值
	 * @return sha256加密后的值
	 */
	public static String sha256Hex(String value) {
		return DigestUtils.sha256Hex(value);
	}

	/**
	 * 督办事项、提请事项、附件表主键获取
	 * 
	 * @return 获取一个随机主键
	 */
	public static String newUUID() {
		return sha1Hex(randomUUID().toString());
	}

	/**
	 * 替换目标String为空字符串
	 * @param string
	 * @return
	 */
	public static String replaceNullToEmpty(String string) {
		return (string == null ? "" : string);
	}

	/**
	 * 替换目标String为&nbsp
	 * @param string
	 * @return
	 */
	public static String replaceNullToNbsp(String string) {
		return (string == null ? "&nbsp;" : ("".equals(string) ? "&nbsp;" : string));
	}

}
