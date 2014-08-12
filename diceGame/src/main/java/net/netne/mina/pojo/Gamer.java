package net.netne.mina.pojo;

import org.apache.mina.core.session.IoSession;

/**
 * diceGame
 * @date 2014-8-10 下午9:24:50
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class Gamer {
	
	//用户标识
	private String uid;
	
	private String name;
	
	private String sex;

	//游戏状态 0-初始加入,1-确认准备、2-游戏中 、3-退出
	private Integer gamestatus;
	
	private IoSession session;
	
	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public Integer getGamestatus() {
		return gamestatus;
	}

	public void setGamestatus(Integer gamestatus) {
		this.gamestatus = gamestatus;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
