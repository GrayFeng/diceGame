package net.netne.api.service.impl;

import java.util.Map;

import net.netne.api.dao.IMemberDao;
import net.netne.api.dao.IScoreDao;
import net.netne.api.service.IScoreService;
import net.netne.common.pojo.Account;
import net.netne.common.pojo.Member;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

@Service
public class ScoreServiceImpl implements IScoreService {
	
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IScoreDao scoreDao;

	@Override
	public Account addScore(Integer memberId, Integer amount) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("memberId",memberId);
		paramMap.put("amount", amount);
		scoreDao.addScore(paramMap);
		return memberDao.getAccount(memberId);
	}

	@Override
	public Account addScore(String mobile, Integer amount) {
		Account account = null;
		if(StringUtils.isEmpty(mobile) || amount == null || amount < 0){
			return account;
		}
		Member member = memberDao.getMember(mobile);
		if(member != null){
			account = addScore(member.getId(), amount);
		}
		return account;
	}

}
