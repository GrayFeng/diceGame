package net.netne.common.pojo;

import java.util.Date;

import net.netne.common.annotation.MyBatisRepository;

import org.springframework.stereotype.Repository;

/**
 * diceGame
 * @date 2014-8-11 下午9:46:57
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Repository
@MyBatisRepository
public class Member {
	
	private Integer id;
	
	private String name;
	
	private String password;
	
	private String mobile;
	
	private Integer sex;
	
	private Account account;
	
	private MemberPhoto memberPhoto;
	
	private String photoUrl;
	
	private Date regTime;
	
	private Date modifyTime;
	
	private int status;
	
	private boolean firstLogin;
	
	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public MemberPhoto getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(MemberPhoto memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
}
