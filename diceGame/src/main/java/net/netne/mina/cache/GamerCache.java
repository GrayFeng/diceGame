package net.netne.mina.cache;

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
	
	private static Map<String,List<Gamer>> cache = Maps.newHashMap();
	
	private GamerCache(){
		
	}
	
	public synchronized static GamerCache getInstance(){
		if(gamerCache == null){
			gamerCache = new GamerCache();
		}
		return gamerCache;
	}
	
	public void add(String gamblingId,List<Gamer> gamers){
		cache.put(gamblingId, gamers);
	}
	
	public void addOne(String gamblingId,Gamer gamer){
		if(StringUtils.isNotEmpty(gamblingId)){
			List<Gamer> gamerList = cache.get(gamblingId);
			if(gamerList == null){
				gamerList = Lists.newArrayList();
			}
			gamerList.add(gamer);
			cache.put(gamblingId, gamerList);
		}
	}
	
	public List<Gamer> getGamers(String gamblingId){
		return cache.get(gamblingId);
	}
	
	public void removeOne(String key,String gamerId){
		List<Gamer> gamerList = cache.get(key);
		if(gamerList != null){
			for(Gamer gamer : gamerList){
				if(gamer.getUid().equals(gamerId)){
					gamerList.remove(gamer);
					break;
				}
			}
			cache.put(key,gamerList);
		}
	}
	
	public void remove(String key){
		cache.remove(key);
	}
	
}
