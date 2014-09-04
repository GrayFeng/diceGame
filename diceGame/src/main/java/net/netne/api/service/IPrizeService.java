package net.netne.api.service;

import java.util.List;
import java.util.Map;

import net.netne.common.pojo.Member;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.Prize;
import net.netne.common.pojo.PrizeMember;
import net.netne.common.pojo.PrizePhoto;

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
	
	public Prize lottery(Member member);
	
	public void modifyStockPrize(Integer id,Integer stock);
	
	public Page<PrizeMember> getPrizeMemberList(Integer pageNum);
	
	public void updateMemberInfo4Prize(Map<String,Object> paramMap);
	
	public void updatePrizePhoto(PrizePhoto prizePhoto);
	
	public PrizePhoto getPrizePhoto(Integer prizeId);
	
	public void deletePrize(Integer prizeId);
	
}
