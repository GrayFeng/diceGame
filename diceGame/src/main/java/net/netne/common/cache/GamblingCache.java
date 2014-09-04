package net.netne.common.cache;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import net.netne.mina.pojo.Gambling;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-10 下午9:37:57
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class GamblingCache {
	
	private static GamblingCache gamblingCache = null;
	
	private static Map<String,Gambling> cache = Maps.newLinkedHashMap();
	
	private static Map<String,String> roomNoCache = Maps.newLinkedHashMap();
	
	private static Vector<String> freeRoomNo = new Vector<String>();
	
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
		roomNoCache.put(gambling.getBoardNo(), gambling.getId());
	}
	
	public Gambling get(String key){
		return cache.get(key);
	}
	
	public Gambling getByRoomNo(String roomNo){
		String key = roomNoCache.get(roomNo);
		if(StringUtils.isNotEmpty(key)){
			return cache.get(key);
		}
		return null;
	}
	
	public List<Gambling> getAll(){
		return Lists.newArrayList(cache.values());
	}
	
	public void remove(String key){
		Gambling gambling = cache.remove(key);
		if(gambling != null){
			freeRoomNo.add(gambling.getBoardNo());
			roomNoCache.remove(gambling.getBoardNo());
		}
	}
	
	public synchronized String getFreeRoomNo(){
		if(!freeRoomNo.isEmpty()){
			return freeRoomNo.remove(0);
		}else{
			return null;
		}
	}
	
	public void removeAll(){
		cache.clear();
		roomNoCache.clear();
	}
	
}
