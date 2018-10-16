package com.gdin.dzzwsyb.swzzbdbxt.web.model;

/**
 * 用户表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class UserBond {
	private String id;

	private Integer flag;

	private String name;

	private String backIP;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBackIP() {
		return backIP;
	}

	public void setBackIP(String backIP) {
		this.backIP = backIP;
	}
}