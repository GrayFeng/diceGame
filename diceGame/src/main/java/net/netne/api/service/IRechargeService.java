package net.netne.api.service;

import net.netne.common.pojo.Member;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.RechargeOrder;

public interface IRechargeService {
	
	public RechargeOrder createRechargeOrder(Member member,Integer fee);
	
	public RechargeOrder getOrderByNo(String orderNo);
	
	public Page<RechargeOrder> getOrderList(Integer pageNum);
	
	public boolean paymentCallback(String orderNo,String realFee,Integer channel,Integer status);


}
