package com.gdin.dzzwsyb.swzzbdbxt.web.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户表模型类
 * 
 * @author PanzerkleinVv
 *
 */
public class User {
	private Long id;

	private String username;

	private String password;

	private String userdesc;

    private Integer state;

	private Long permissionId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	private Long roleId;

	public User() {

	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getUserdesc() {
		return userdesc;
	}

	public void setUserdesc(String userdesc) {
		this.userdesc = userdesc == null ? null : userdesc.trim();
	}

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public boolean isEmpty() {
		if (this.id != null && !"".equals(this.id)) {
			return false;
		} else if (this.username != null && !"".equals(this.username)) {
			return false;
		} else if (this.userdesc != null && !"".equals(this.userdesc)) {
			return false;
		} else if (this.permissionId != null && 0 != this.permissionId) {
			return false;
		} else if (this.roleId != null && 0 != this.roleId) {
			return false;
		} else {
			return true;
		}
	}
}