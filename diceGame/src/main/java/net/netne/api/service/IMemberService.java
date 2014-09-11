package net.netne.api.service;

import net.netne.common.pojo.LoginLog;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.MemberPhoto;
import net.netne.common.pojo.Page;

/**
 * diceGame
 * @date 2014-8-11 下午9:50:33
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface IMemberService {
	
	public void addMember(Member member);
	
	public Member login(String mobile,String password,String ip);
	
	public Member getMember(String mobile);
	
	public Member getMemberById(Integer id);
	
	public void freezeScore(Integer memberId,Integer amount);
	
	public void unFreezeScore(Integer memberId, Integer amount);
	
	public boolean checkScore(Integer memberId,Integer amount);
	
	public void updateMember(Member member);

	public void modifyPassword(Integer memberId,String password);
	
	public MemberPhoto getMemberPhoto(Integer memberId);
	
	public Page<Member> getMemberList(Integer pageNum);
	
	public Member sysLogin(String name);
	
	public Integer addLoginLog(LoginLog loginLog);
	
	public boolean checkMemberIsFirstLogin(Member member,String ip);

}
