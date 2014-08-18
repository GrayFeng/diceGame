package net.netne.api.service;

import java.util.List;

import net.netne.common.pojo.Page;
import net.netne.common.pojo.Prize;

/**
 * diceGame
 * @date 2014-8-17 下午11:21:37
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface IPrizeService {
	
	public void addPrize(Prize prize);
	
	public void modifyPrize(Prize prize);
	
	public Prize getPrizeById(Integer prizeId);
	
	public Page<Prize> getPrizeList(Integer pageNum);
	
	public List<Prize> getAllPrizeList();
	
}