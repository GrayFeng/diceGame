package net.netne.api.dao;

import java.util.List;
import java.util.Map;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.Prize;
import net.netne.common.pojo.PrizeMember;
import net.netne.common.pojo.PrizePhoto;

import org.springframework.stereotype.Repository;

/**
 * diceGame
 * @date 2014-8-17 下午11:06:47
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@MyBatisRepository
@Repository
public interface IPrizeDao {
	
	public void addPrize(Prize prize);
	
	public void modifyPrize(Prize prize);
	
	public Prize getPrizeById(Integer prizeId);
	
	public List<Prize> getPrizeList(Map<String,Object> paramMap);
	
	public Long getPrizeCount();
	
	public void modifyStockPrize(Map<String,Object> paramMap);
	
	public void addPrizeMember(Map<String,Object> paramMap);
	
	public Long getPrizeMemberCount();
	
	public List<PrizeMember> getPrizeMemberList(Map<String,Object> paramMap);
	
	public void updateMemberInfo4Prize(Map<String,Object> paramMap);
	
	public void updatePrizePhoto(PrizePhoto prizePhoto);
	
	public PrizePhoto getPrizePhoto(Integer prizeId);
	

}
