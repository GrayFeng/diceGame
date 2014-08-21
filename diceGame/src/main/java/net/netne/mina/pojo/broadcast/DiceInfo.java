package net.netne.mina.pojo.broadcast;
/**
 * diceGame
 * @date 2014-8-15 下午11:58:06
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class DiceInfo{
	private String dice;
	
	private Integer userId;
	
	private String userName;

	public String getDice() {
		return dice;
	}

	public void setDice(String dice) {
		this.dice = dice;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
