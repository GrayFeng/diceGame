package net.netne.mina.pojo;

import java.util.List;

/**
 * diceGame
 * @date 2014-8-10 下午9:23:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class Gambling{
	//唯一标识ID
	private String id;
	//牌局号
	private String boardNo;
	//最大参与人数
	private Integer maxGamerNum;
	//参与人数
	private Integer gamerNum;
	//游戏状态
	private Integer status;
	//开局分数
	private Integer score;
	//令牌序号
	private Integer tokenIndex;
	//当前竞猜个数
	private Integer diceNum;
	//当前竞猜点数
	private Integer dicePoint;
	
	public Integer getTokenIndex() {
		return tokenIndex;
	}
	public void setTokenIndex(Integer tokenIndex) {
		this.tokenIndex = tokenIndex;
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
	//当前参数游戏玩家列表
	private List<Gamer> gamerList;
	
	public Integer getMaxGamerNum() {
		return maxGamerNum;
	}
	public void setMaxGamerNum(Integer maxGamerNum) {
		this.maxGamerNum = maxGamerNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}
	public Integer getGamerNum() {
		return gamerNum;
	}
	public void setGamerNum(Integer gamerNum) {
		this.gamerNum = gamerNum;
	}
	public List<Gamer> getGamerList() {
		return gamerList;
	}
	public void setGamerList(List<Gamer> gamerList) {
		this.gamerList = gamerList;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
