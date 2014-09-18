package net.netne.common.pojo;

public class RechargeOrder {
	
	private Integer id;
	
	private String orderNo;
	
	private Long orderFee;
	
	private Long realPayFee;
	
	private Integer memberId;
	
	private String memberMobile;
	
	private Integer status;
	
	private Integer channel;
	
	private String createTime;
	
	private String modifyTime;
	
	public Long getRealPayFee() {
		return realPayFee;
	}

	public void setRealPayFee(Long realPayFee) {
		this.realPayFee = realPayFee;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(Long orderFee) {
		this.orderFee = orderFee;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
}
