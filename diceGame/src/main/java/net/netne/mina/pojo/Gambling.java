package net.netne.mina.pojo;

import java.util.List;

/**
 * diceGame
 * @date 2014-8-10 下午9:23:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class Gambling {
	//唯一标识ID
	private String id;
	//牌局号
	private String boardNo;
	//参与人数
	private Integer gamerNum;
	//当前参数游戏玩家列表
	private List<Gamer> gamerList;
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
}
