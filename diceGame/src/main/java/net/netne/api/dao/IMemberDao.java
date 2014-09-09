package net.netne.api.dao;

import java.util.List;
import java.util.Map;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.Account;
import net.netne.common.pojo.LoginLog;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.MemberPhoto;

import org.springframework.stereotype.Repository;

/**
 * diceGame
 * @date 2014-8-11 下午9:46:40
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Repository
@MyBatisRepository
public interface IMemberDao {
	
	public Integer addMember(Member member);
	
	public Member getMember(String mobile);
	
	public Member getMemberById(Integer id);
	
	public void addAccount(Account account);
	
	public Account getAccount(Integer memberId);
	
	public void updateMember(Map<String,Object> paramMap);

	public void modifyPassword(Map<String,Object> paramMap);
	
	public void addMemberPhoto(MemberPhoto memberPhoto);
	
	public MemberPhoto getMemberPhoto(Integer memberId);
	
	public void updateMemberPhoto(MemberPhoto memberPhoto);
	
	public Long getMemberCount();
	
	public List<Member> getMemberList(Map<String,Object> paramMap);
	
	public Member sysLogin(String name);
	
	public Integer addLoginLog(LoginLog loginLog);
	
	public Integer getLoginCount(Integer memberId);

}
