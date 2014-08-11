package net.netne.common.cache;

import java.util.Map;

import net.netne.common.pojo.Member;

import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-10 下午9:37:57
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class MemberCache {
	
	private static MemberCache memberCache = null;
	
	private static Map<String,Member> cache = Maps.newHashMap();
	
	private MemberCache(){
		
	}
	
	public synchronized static MemberCache getInstance(){
		if(memberCache == null){
			memberCache = new MemberCache();
		}
		return memberCache;
	}
	
	public void add(String uid,Member member){
		cache.put(uid, member);
	}
	
	
	public Member get(String uid){
		return cache.get(uid);
	}
	
	public void remove(String uid){
		cache.remove(uid);
	}
	
	public boolean isHave(String uid){
		return cache.containsKey(uid);
	}
	
}
