package net.netne.common.cache;

import java.util.concurrent.TimeUnit;

import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.Member;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * diceGame
 * @date 2014-8-10 下午9:37:57
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class MemberCache {
	
	private static MemberCache memberCache = null;
	
	private static Cache<String, LoginInfo> cache = CacheBuilder.newBuilder()
			.maximumSize(10000)
			.expireAfterAccess(2, TimeUnit.DAYS).build(); 
	
	private MemberCache(){
		
	}
	
	public synchronized static MemberCache getInstance(){
		if(memberCache == null){
			memberCache = new MemberCache();
		}
		return memberCache;
	}
	
	public void add(String uid,LoginInfo loginInfo){
		cache.put(uid, loginInfo);
	}
	
	public void updateMember(String uid,Member member){
		LoginInfo loginInfo = get(uid);
		if(loginInfo != null){
			loginInfo.setMember(member);
			add(uid, loginInfo);
		}
	}
	
	
	public LoginInfo get(String uid){
		return cache.getIfPresent(uid);
	}
	
	public void remove(String uid){
		cache.invalidate(uid);
	}
	
	public boolean isHave(String uid){
		return cache.asMap().containsKey(uid);
	}
	
	public boolean isLogin(String uid){
		LoginInfo loginInfo = get(uid);
		if(loginInfo != null && loginInfo.getMember() != null){
			return true;
		}
		return false;
	}
	
}
