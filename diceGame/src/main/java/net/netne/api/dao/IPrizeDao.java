package net.netne.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.Prize;

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

}
