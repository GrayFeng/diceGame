package net.netne.mina.pojo.param;

public class GuessDiceParam {
	
	//动作指令代码
	private String actionCode;
	//用户标识
	private String uid;
	
	private String gamblingId;
	
	private Integer diceNum;
	
	private Integer dicePoint;

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

	public String getGamblingId() {
		return gamblingId;
	}

	public void setGamblingId(String gamblingId) {
		this.gamblingId = gamblingId;
	}

	public Integer getDiceNum() {
		return diceNum;
	}

	public void setDiceNum(Integer diceNum) {
		this.diceNum = diceNum;
	}

	public Integer getDicePoint() {
		return dicePoint;
	}

	public void setDicePoint(Integer dicePoint) {
		this.dicePoint = dicePoint;
	}
	
}
