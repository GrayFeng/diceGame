package net.netne.common.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.netne.mina.pojo.Gamer;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-10 下午9:37:57
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class GamerCache {
	
	private static GamerCache gamerCache = null;
	
	private static Map<String,LinkedHashMap<String,Gamer>> cache = Maps.newLinkedHashMap();
	
	private GamerCache(){
		
	}
	
	public synchronized static GamerCache getInstance(){
		if(gamerCache == null){
			gamerCache = new GamerCache();
		}
		return gamerCache;
	}
	
	public void add(String gamblingId,List<Gamer> gamers){
		if(gamers != null && gamers.size() > 0){
			LinkedHashMap<String,Gamer> gamerMap = cache.get(gamblingId);
			if(gamerMap == null){
				gamerMap = Maps.newLinkedHashMap();
			}
			for(Gamer gamer : gamers){
				gamerMap.put(gamer.getUid(), gamer);
			}
			cache.put(gamblingId, gamerMap);
		}
		
	}
	
	public void add(String gamblingId,LinkedHashMap<String,Gamer> gamerMap){
		cache.put(gamblingId, gamerMap);
	}
	
	public void addOne(String gamblingId,Gamer gamer){
		if(StringUtils.isNotEmpty(gamblingId)){
			LinkedHashMap<String,Gamer> gamerMap = cache.get(gamblingId);
			if(gamerMap == null){
				gamerMap = Maps.newLinkedHashMap();
			}
			gamerMap.put(gamer.getUid(),gamer);
			cache.put(gamblingId, gamerMap);
		}
	}
	
	public Gamer getOne(String gamblingId,String uid){
		Map<String,Gamer> gamerMap = cache.get(gamblingId);
		if(gamerMap != null){
			return gamerMap.get(uid);
		}
		return null;
	}
	
	public Gamer getOne(String uid){
		if(cache.values() != null){
			for(LinkedHashMap<String,Gamer> gamers:cache.values()){
				if(gamers.containsKey(uid)){
					return gamers.get(uid);
				}
			}
		}
		return null;
	}
	
	public Gamer getPreOne(String gamblingId,Integer index){
		List<Gamer> gamers = getGamers(gamblingId);
		if(gamers != null){
			return gamers.get(index);
		}
		return null;
	}
	
	public List<Gamer> getGamers(String gamblingId){
		LinkedHashMap<String,Gamer> gamerMap = cache.get(gamblingId);
		if(gamerMap != null){
			return Lists.newArrayList(gamerMap.values());
		}
		return null;
	}
	
	public Map<String,Gamer> getGamerMap(String gamblingId){
		return cache.get(gamblingId);
	}
	
	public void removeOne(String key,String gamerId){
		LinkedHashMap<String,Gamer> gamerMap = cache.get(key);
		if(gamerMap != null){
			gamerMap.remove(gamerId);
			cache.put(key,gamerMap);
		}
	}
	
	public boolean checkGamerIsInGame(String gamblingId,Integer gamerId){
		List<Gamer> gamerList = getGamers(gamblingId);
		if(gamerList != null && gamerList.size() > 0){
			for(Gamer gamer : gamerList){
				if(gamer.getId().equals(gamerId)){
					return true;
				}
			}
		}
		return false;
	}
	
	public void remove(String key){
		cache.remove(key);
	}
	
	public void removeAll(){
		cache.clear();
	}
	
}
