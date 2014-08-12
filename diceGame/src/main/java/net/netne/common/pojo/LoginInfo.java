package net.netne.common.pojo;

import java.util.Date;

public class LoginInfo {
	
	private Date lastAccessTime;
	
	private Member member;

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public LoginInfo(){
		this.lastAccessTime = new Date();
	}
	
}
