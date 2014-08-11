package net.netne.mina.cache;

import java.util.Map;

import net.netne.mina.pojo.Gambling;

import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-10 下午9:37:57
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class GamblingCache {
	
	private static GamblingCache gamblingCache = null;
	
	private static Map<String,Gambling> cache = Maps.newHashMap();
	
	private GamblingCache(){
		
	}
	
	public synchronized static GamblingCache getInstance(){
		if(gamblingCache == null){
			gamblingCache = new GamblingCache();
		}
		return gamblingCache;
	}
	
	public void add(Gambling gambling){
		cache.put(gambling.getId(), gambling);
	}
	
	public Gambling get(String key){
		return cache.get(key);
	}
	
	public void remove(String key){
		cache.remove(key);
	}
	
}
