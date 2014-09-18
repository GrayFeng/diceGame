package net.netne.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.netne.api.dao.IRechargeDao;
import net.netne.api.service.IRechargeService;
import net.netne.api.service.IScoreService;
import net.netne.common.enums.ERechargeStatus;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.RechargeOrder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

@Service
public class RechargeServiceImpl implements IRechargeService {

	private Logger log = LoggerFactory.getLogger(RechargeServiceImpl.class);
	
	@Autowired
	private IRechargeDao rechargeDao;
	
	@Autowired
	private IScoreService scoreService;
	
	@Override
	public RechargeOrder createRechargeOrder(Member member, Integer fee) {
		RechargeOrder order = new RechargeOrder();
		order.setOrderFee(fee);
		order.setMemberId(member.getId());
		order.setMemberMobile(member.getMobile());
		order.setStatus(ERechargeStatus.WAIT_2_PAY.getCode());
		order.setOrderNo(String.valueOf(System.currentTimeMillis() + new Random().nextInt(1000)));
		rechargeDao.createRechargeOrder(order);
		return order;
	}

	@Override
	public RechargeOrder getOrderByNo(String orderNo) {
		return rechargeDao.getOrderByNo(orderNo);
	}


	@Override
	public Page<RechargeOrder> getOrderList(Integer pageNum) {
		Page<RechargeOrder> page = new Page<RechargeOrder>();
		if(pageNum == null){
			pageNum = 1;
		}
		Long orderCount = rechargeDao.getOrderCount();
		if(orderCount != null && orderCount > 0){
			page.setTotal(orderCount.intValue());
			page.setSize(10);
			if(pageNum > page.getTotalPages()){
				pageNum = page.getTotalPages();
			}
			page.setNumber(pageNum);
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("startNum", page.getStartNum());
			paramMap.put("size", page.getSize());
			List<RechargeOrder> memberList = rechargeDao.getOrderList(paramMap);
			page.setContent(memberList);
		}
		return page;
	}

	@Override
	public boolean paymentCallback(String orderNo, String realFee,
			Integer channel, Integer status) {
		boolean result = false;
		log.error("支付订单回调：orderNo:" + (orderNo ==  null ? "null" : orderNo) 
				+ ",realFee:" + (realFee ==  null ? "null" : realFee)
				+ ",channel:" + (channel ==  null ? "null" : channel)
				+ ",status:" + (status ==  null ? "null" : status));
		try{
			if(StringUtils.isNotEmpty(orderNo) && realFee != null 
					&& NumberUtils.isNumber(realFee) && status != null){
				RechargeOrder order = rechargeDao.getOrderByNo(orderNo);
				if(order != null
						&& (ERechargeStatus.WAIT_2_PAY.getCode().equals(order.getStatus())
								|| ERechargeStatus.FAIL.getCode().equals(status))){
					order.setChannel(channel);
					order.setRealPayFee(Integer.valueOf(realFee));
					order.setStatus(status);
					rechargeDao.updatePayResult(order);
					if(ERechargeStatus.SUCCESS.getCode().equals(status)){
						scoreService.addScore(order.getMemberId(), Integer.valueOf(realFee) * 10000);
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return result;
	}
}
