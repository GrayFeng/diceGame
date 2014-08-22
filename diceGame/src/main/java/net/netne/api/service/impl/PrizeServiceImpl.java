package net.netne.api.service.impl;

import java.util.List;
import java.util.Map;

import net.netne.api.dao.IPrizeDao;
import net.netne.api.service.IPrizeService;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.Prize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-17 下午11:22:55
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Service
public class PrizeServiceImpl implements IPrizeService {
	
	@Autowired
	private IPrizeDao prizeDao;

	@Override
	public void addPrize(Prize prize) {
		prizeDao.addPrize(prize);
	}

	@Override
	public void modifyPrize(Prize prize) {
		prizeDao.modifyPrize(prize);
	}

	@Override
	public Prize getPrizeById(Integer prizeId) {
		return prizeDao.getPrizeById(prizeId);
	}

	@Override
	public Page<Prize> getPrizeList(Integer pageNum) {
		Page<Prize> page = new Page<Prize>();
		if(pageNum == null){
			pageNum = 1;
		}
		Long prizeCount = prizeDao.getPrizeCount();
		if(prizeCount != null && prizeCount > 0){
			page.setTotal(prizeCount.intValue());
			page.setSize(10);
			if(pageNum > page.getTotalPages()){
				pageNum = page.getTotalPages();
			}
			page.setNumber(pageNum);
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("startNum", page.getStartNum());
			paramMap.put("size", page.getSize());
			List<Prize> prizeList = prizeDao.getPrizeList(paramMap);
			page.setContent(prizeList);
		}
		return page;
	}

	@Override
	public List<Prize> getAllPrizeList() {
		Page<Prize> page = new Page<Prize>();
		List<Prize> prizeList = null;
		Long prizeCount = prizeDao.getPrizeCount();
		if(prizeCount != null && prizeCount > 0){
			page.setTotal(prizeCount.intValue());
			page.setSize(prizeCount.intValue());
			page.setNumber(1);
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("startNum", page.getStartNum());
			paramMap.put("size", page.getSize());
			prizeList = prizeDao.getPrizeList(paramMap);
		}
		return prizeList;
	}
	
	public Prize lottery(Member member){
		List<Prize> prizeList = getAllPrizeList();
		if(prizeList != null && prizeList.size() > 0){
			
		}
		return null;
	}

}
