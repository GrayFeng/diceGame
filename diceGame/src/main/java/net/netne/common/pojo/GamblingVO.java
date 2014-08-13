package net.netne.common.pojo;


/**
 * diceGame
 * @date 2014-8-10 下午9:23:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class GamblingVO{
	//唯一标识ID
	private String id;
	//牌局号
	private String boardNo;
	//最大参与人数
	private Integer maxGamerNum;
	//参与人数
	private Integer gamerNum;
	//等待-0,开始游戏-1
	private Integer status;
	//开局分数
	private Integer score;
	
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
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
