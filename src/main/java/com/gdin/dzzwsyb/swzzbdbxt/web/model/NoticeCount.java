package com.gdin.dzzwsyb.swzzbdbxt.web.model;
/**
 * 提醒类型统计和未读统计
 * 
 * @author helezhu
 * @date   2018年4月24日 下午3:25:58 
 */
public class NoticeCount {
	
	private Integer type; //提醒类型   -1 动态更新   0逾期
	
	private Integer oneType; // 某类提醒类型的总数
	
	private Integer unRead;//未读记录数

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOneType() {
		return oneType;
	}

	public void setOneType(Integer oneType) {
		this.oneType = oneType;
	}

	public Integer getUnRead() {
		return unRead;
	}

	public void setUnRead(Integer unRead) {
		this.unRead = unRead;
	}

	
	
}
