package net.netne.common.cache;

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
	
	private static Map<String,Map<String,Gamer>> cache = Maps.newLinkedHashMap();
	
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
			Map<String,Gamer> gamerMap = cache.get(gamblingId);
			if(gamerMap == null){
				gamerMap = Maps.newHashMap();
			}
			for(Gamer gamer : gamers){
				gamerMap.put(gamer.getUid(), gamer);
			}
			cache.put(gamblingId, gamerMap);
		}
		
	}
	
	public void add(String gamblingId,Map<String,Gamer> gamerMap){
		cache.put(gamblingId, gamerMap);
	}
	
	public void addOne(String gamblingId,Gamer gamer){
		if(StringUtils.isNotEmpty(gamblingId)){
			Map<String,Gamer> gamerMap = cache.get(gamblingId);
			if(gamerMap == null){
				gamerMap = Maps.newHashMap();
			}
			gamer.setTokenIndex(gamerMap.size());
			gamerMap.put(gamer.getUid(),gamer);
			cache.put(gamblingId, gamerMap);
		}
	}
	
	public List<Gamer> getGamers(String gamblingId){
		Map<String,Gamer> gamerMap = cache.get(gamblingId);
		if(gamerMap != null){
			return Lists.newArrayList(gamerMap.values());
		}
		return null;
	}
	
	public Map<String,Gamer> getGamerMap(String gamblingId){
		return cache.get(gamblingId);
	}
	
	public void removeOne(String key,String gamerId){
		Map<String,Gamer> gamerMap = cache.get(key);
		if(gamerMap != null){
			gamerMap.remove(gamerId);
			cache.put(key,gamerMap);
		}
	}
	
	public void remove(String key){
		cache.remove(key);
	}
	
}
