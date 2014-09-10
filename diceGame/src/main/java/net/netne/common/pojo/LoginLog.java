package net.netne.common.pojo;

import java.util.Date;

/**
 * diceGame
 * @date 2014-9-5 上午8:57:58
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class LoginLog {
	
	private Integer id;
	
	private Integer memberId;
	
	private String ip;
	
	private Date loginTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
}
