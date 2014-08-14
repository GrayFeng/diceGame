package net.netne.api.dao;

import java.util.Map;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.Account;
import net.netne.common.pojo.Member;

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
	
	public void addAccount(Account account);
	
	public Account getAccount(Integer memberId);
	
	public void updateMember(Map<String,Object> paramMap);

	public void modifyPassword(Map<String,Object> paramMap);

}
