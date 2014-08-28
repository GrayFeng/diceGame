package net.netne.api.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.netne.api.dao.IPrizeDao;
import net.netne.api.service.IPrizeService;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.Prize;
import net.netne.common.pojo.PrizeMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
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
	
	@Override
	public void modifyStockPrize(Integer id, Integer stock) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("id", id);
		paramMap.put("stock", stock);
		prizeDao.modifyStockPrize(paramMap);
	}
	
	public Prize lottery(Member member){
		List<Prize> prizeList = getAllPrizeList();
		if(prizeList != null && prizeList.size() > 0){
			Map<Double,List<Integer>> probabilityMap = Maps.newHashMap();
			for(Prize prize : prizeList){
				if(prize.getProbability() == null || prize.getStock() < 1){
					continue;
				}
				List<Integer> prizeIdList = probabilityMap.get(prize.getProbability());
				if(prizeIdList == null || probabilityMap.isEmpty()){
					prizeIdList = Lists.newArrayList();
				}
				prizeIdList.add(prize.getId());
				probabilityMap.put(prize.getProbability(), prizeIdList);
			}
			if(probabilityMap.size() > 0){
				List<Double> probabilityList = Lists.newArrayList(probabilityMap.keySet());
				 Collections.sort(probabilityList, new Comparator<Double>(){
					@Override
					public int compare(Double arg0, Double arg1) {
						return arg0.compareTo(arg1);
					}
				 });
				 double randomNumber = Math.random() * 100;
				 Double prizeKey = null;
				 for(int i = 0; i < probabilityList.size();i++){
					 Double probability = probabilityList.get(i);
					 if(i == 0 && randomNumber <= probability){
						 prizeKey = probability;
						 break;
					 }else if(i > 0 && randomNumber > probabilityList.get(i-1) 
							 && randomNumber <= probability){
						 prizeKey = probability;
						 break;
					 }
				 }
//				 if(prizeKey == null 
//						 && probabilityList != null 
//						 && !probabilityList.isEmpty()){
//					 prizeKey = probabilityList.get(probabilityList.size() -1);
//				 }
				 Integer prizeId = null;
				 if(prizeKey != null){
					 List<Integer> prizeIds = probabilityMap.get(prizeKey);
					 if(prizeIds != null && !prizeIds.isEmpty()){
						 if(prizeIds.size() > 1){
							 int index = new Random().nextInt(prizeIds.size());
							 if(index > prizeIds.size() || index == prizeIds.size()){
								 index = prizeIds.size() - 1 ;
							 }
							 prizeId = prizeIds.get(index);
						 }else{
							 prizeId = prizeIds.get(0);
						 }
					 }
					 if(prizeId != null){
						 Prize prize = getPrizeById(prizeId);
						 if(prize != null){
							 modifyStockPrize(prize.getId(),1);
							 addPrizeMember(member,prize);
						 }
						 return prize;
					 }
				 }
			}
		}
		return null;
	}
	
	private void addPrizeMember(Member member,Prize prize){
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("memberId", member.getId());
		paramMap.put("memberMobile", member.getMobile());
		paramMap.put("memberName", member.getName());
		paramMap.put("prizeId", prize.getId());
		paramMap.put("prizeName", prize.getName());
		prizeDao.addPrizeMember(paramMap);
	}

	@Override
	public Page<PrizeMember> getPrizeMemberList(Integer pageNum) {
		Page<PrizeMember> page = new Page<PrizeMember>();
		if(pageNum == null){
			pageNum = 1;
		}
		Long count = prizeDao.getPrizeMemberCount();
		if(count != null && count > 0){
			page.setTotal(count.intValue());
			page.setSize(10);
			if(pageNum > page.getTotalPages()){
				pageNum = page.getTotalPages();
			}
			page.setNumber(pageNum);
			Map<String,Object> paramMap = Maps.newHashMap();
			paramMap.put("startNum", page.getStartNum());
			paramMap.put("size", page.getSize());
			List<PrizeMember> memberList = prizeDao.getPrizeMemberList(paramMap);
			page.setContent(memberList);
		}
		return page;
	}

}
