package net.netne.api.service.impl;

import java.util.Map;

import net.netne.api.dao.IMemberDao;
import net.netne.api.dao.IScoreDao;
import net.netne.api.service.IMemberService;
import net.netne.common.pojo.Account;
import net.netne.common.pojo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-11 下午9:51:16
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Service
public class MemberServiceImpl implements IMemberService{
	@Autowired
	private IMemberDao memberDao;
	@Autowired
	private IScoreDao scoreDao;

	@Override
	@Transactional(readOnly=false,rollbackFor=Throwable.class)
	public void addMember(Member member) {
		memberDao.addMember(member);
		Account account = new Account();
		account.setMemberId(member.getId());
		account.setScoreAmount(500L);
		account.setFreezeAmount(0L);
		memberDao.addAccount(account);
	}

	@Override
	public Member login(String mobile, String password) {
		Member member = getMember(mobile);
		if(member != null 
				&& member.getPassword().equals(password)){
			Account account = memberDao.getAccount(member.getId());
			member.setAccount(account);
			return member;
		}
		return null;
	}

	@Override
	public Member getMember(String mobile) {
		return memberDao.getMember(mobile);
	}

	@Override
	public void freezeScore(Integer memberId, Integer amount) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("memberId", memberId);
		paramMap.put("amount", amount);
		scoreDao.freezeScore(paramMap);
	}

	@Override
	public boolean checkScore(Integer memberId, Integer amount) {
		Account account = memberDao.getAccount(memberId);
		if(account != null){
			long realScore = account.getScoreAmount() - account.getFreezeAmount();
			if(realScore >= amount){
				return true;
			}
		}
		return false;
	}

	@Override
	public void unFreezeScore(Integer memberId, Integer amount) {
		Account account = memberDao.getAccount(memberId);
		if(account != null){
			if(account.getFreezeAmount() >= amount){
				Map<String,Object> paramMap = Maps.newHashMap();
				paramMap.put("memberId", memberId);
				paramMap.put("amount", amount);
				scoreDao.unFreezeScore(paramMap);
			}
		}
	}

	@Override
	public void updateMember(Member member) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("memberId", member.getId());
		paramMap.put("name", member.getName());
		paramMap.put("sex", member.getSex());
		memberDao.updateMember(paramMap);
	}

	@Override
	public void modifyPassword(Integer memberId, String password) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("memberId", memberId);
		paramMap.put("password", password);
		memberDao.modifyPassword(paramMap);
	}

}
