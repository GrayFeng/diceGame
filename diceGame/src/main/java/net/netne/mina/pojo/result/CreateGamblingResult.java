package net.netne.mina.pojo.result;
/**
 * diceGame
 * @date 2014-8-10 下午9:29:04
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CreateGamblingResult implements IResult{
	
	private String gamblingId;
	
	private String boardNo;
	
	private Integer score;
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}

	public String getGamblingId() {
		return gamblingId;
	}

	public void setGamblingId(String gamblingId) {
		this.gamblingId = gamblingId;
	}
}
