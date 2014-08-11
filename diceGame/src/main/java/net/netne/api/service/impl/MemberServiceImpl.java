package net.netne.api.service.impl;

import net.netne.api.dao.IMemberDao;
import net.netne.api.service.IMemberService;
import net.netne.common.pojo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public void addMember(Member member) {
		memberDao.addMember(member);
	}

	@Override
	public Member login(String mobile, String password) {
		Member member = getMember(mobile);
		if(member != null 
				&& member.getPassword().equals(password)){
			return member;
		}
		return null;
	}

	@Override
	public Member getMember(String mobile) {
		return memberDao.getMember(mobile);
	}

}
