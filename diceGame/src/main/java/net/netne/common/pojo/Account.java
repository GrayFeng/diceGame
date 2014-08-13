package net.netne.common.pojo;

import java.util.Date;

public class Account {
	
	private Integer memberId;
	
	private Long scoreAmount;
	
	private Long freezeAmount;
	
	private Date motifyDate;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Long getScoreAmount() {
		return scoreAmount;
	}

	public void setScoreAmount(Long scoreAmount) {
		this.scoreAmount = scoreAmount;
	}

	public Long getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Long freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public Date getMotifyDate() {
		return motifyDate;
	}

	public void setMotifyDate(Date motifyDate) {
		this.motifyDate = motifyDate;
	}
	
}
