package net.netne.api.service;

import net.netne.common.pojo.Account;

public interface IScoreService {
	
	public Account addScore(Integer memberId,Integer amount);
	
	public Account addScore(String mobile,Integer amount);
	
	public void settleScore(Integer amount,Integer freezeAmount,Integer memberId);

}
