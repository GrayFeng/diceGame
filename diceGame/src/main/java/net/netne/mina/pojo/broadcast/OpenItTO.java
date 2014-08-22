package net.netne.mina.pojo.broadcast;

import java.util.List;

/**
 * diceGame
 * @date 2014-8-15 下午11:52:38
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class OpenItTO {
	
	private Integer score;
	
	private Integer win;
	
	private Integer diceNum;
	
	private Integer dicePoint;
	
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

	private List<DiceInfo> diceList;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getWin() {
		return win;
	}

	public void setWin(Integer win) {
		this.win = win;
	}

	public List<DiceInfo> getDiceList() {
		return diceList;
	}

	public void setDiceList(List<DiceInfo> diceList) {
		this.diceList = diceList;
	}
}