package net.netne.api.dao;

import org.springframework.stereotype.Repository;

import net.netne.common.annotation.MyBatisRepository;
import net.netne.common.pojo.Member;

/**
 * diceGame
 * @date 2014-8-11 下午9:46:40
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Repository
@MyBatisRepository
public interface IMemberDao {
	
	public void addMember(Member member);
	
	public Member getMember(String mobile);

}
