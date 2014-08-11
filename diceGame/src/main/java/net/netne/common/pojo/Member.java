package net.netne.common.pojo;

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
