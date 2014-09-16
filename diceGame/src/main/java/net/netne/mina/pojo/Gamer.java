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
	
	private Integer sex;

	//游戏状态 0-初始加入,1-确认准备、2-游戏中 、3-退出
	private Integer gamestatus;
	
	private IoSession session;
	
	private Integer id;
	
	private String dicePoint;
	
	//当前竞猜个数
	private Integer guessDiceNum;
	//当前竞猜点数
	private Integer guessDicePoint;
	//令牌序号
	private Integer tokenIndex;
	//头像地址
	private String photoUrl;
	
	//游戏ID
	private String gamblingId;
	
	//超时次数
	private Integer timeOutCount = 0;
	
	public Integer getTimeOutCount() {
		return timeOutCount;
	}

	public void setTimeOutCount(Integer timeOutCount) {
		this.timeOutCount = timeOutCount;
	}

	public String getGamblingId() {
		return gamblingId;
	}

	public void setGamblingId(String gamblingId) {
		this.gamblingId = gamblingId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getTokenIndex() {
		return tokenIndex;
	}

	public void setTokenIndex(Integer tokenIndex) {
		this.tokenIndex = tokenIndex;
	}

	public Integer getGuessDiceNum() {
		return guessDiceNum;
	}

	public void setGuessDiceNum(Integer guessDiceNum) {
		this.guessDiceNum = guessDiceNum;
	}

	public Integer getGuessDicePoint() {
		return guessDicePoint;
	}

	public void setGuessDicePoint(Integer guessDicePoint) {
		this.guessDicePoint = guessDicePoint;
	}

	public String getDicePoint() {
		return dicePoint;
	}

	public void setDicePoint(String dicePoint) {
		this.dicePoint = dicePoint;
	}

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
