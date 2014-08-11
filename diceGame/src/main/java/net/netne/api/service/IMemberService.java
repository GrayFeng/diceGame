package net.netne.api.service;

import net.netne.common.pojo.Member;

/**
 * diceGame
 * @date 2014-8-11 下午9:50:33
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface IMemberService {
	
	public void addMember(Member member);
	
	public Member login(String mobile,String password);
	
	public Member getMember(String mobile);

}
