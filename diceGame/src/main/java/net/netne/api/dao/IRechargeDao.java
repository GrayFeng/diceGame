package net.netne.api.dao;

import java.util.List;
import java.util.Map;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.RechargeOrder;

import org.springframework.stereotype.Repository;


@Repository
@MyBatisRepository
public interface IRechargeDao {
	
	public void createRechargeOrder(RechargeOrder order);
	
	public void updatePayResult(RechargeOrder order);
	
	public RechargeOrder getOrderByNo(String orderNo);
	
	public Long getOrderCount();
	
	public List<RechargeOrder> getOrderList(Map<String,Object> paramMap);

}
