package net.netne.mina.pojo.param;
/**
 * diceGame
 * @date 2014-8-10 下午9:19:44
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CreateGamblingParams implements IParams{
	
	//动作指令代码
	private String actionCode;
	//用户标识
	private String uid;
	//游戏参与人数
	private Integer gamerNum;
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getGamerNum() {
		return gamerNum;
	}
	public void setGamerNum(Integer gamerNum) {
		this.gamerNum = gamerNum;
	}
}
