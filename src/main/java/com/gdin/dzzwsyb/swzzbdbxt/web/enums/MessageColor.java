package com.gdin.dzzwsyb.swzzbdbxt.web.enums;

/**
 * 信息颜色代码枚举
 * 
 * @author PanzerkleinVv
 *
 */
public enum MessageColor {
	SUCCESS("#00FF00"), FAILURE("#FF0000");
	private String color;

	private MessageColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
