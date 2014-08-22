package net.netne.mina.broadcast;

import java.util.List;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.broadcast.BroadcastTO;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;

/**
 * diceGame
 * @date 2014-8-12 下午10:28:32
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class checkGameCanBegin4Quit implements IBroadcastThread{
	
	private String gamblingId;
	
	public checkGameCanBegin4Quit(String gamblingId){
		this.gamblingId = gamblingId;
	}

	@Override
	public void run() {
		isGameCanBegin(gamblingId);
	}
	
	private void isGameCanBegin(String gamblingId){
		Gambling gambling = GamblingCache.getInstance().get(gamblingId);
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gamblingId);
		if(gambling != null && gamers != null){
			if(GameStatus.WAIT.getCode().equals(gambling.getStatus()) 
					|| GameStatus.OVER.getCode().equals(gambling.getStatus())){
				int readyCount = 0;
				for(Gamer gamer : gamers){
					if(!GamerStatus.READY.getCode().equals(gamer.getGamestatus())){
						break;
					}else{
						readyCount += 1;
					}
				}
				if(readyCount == gambling.getGamerNum() 
						&& gambling.getGamerNum() > 1){
					gambling.setStatus(GameStatus.START.getCode());
					GamblingCache.getInstance().add(gambling);
					broadCastGameBegin(gamers);
				}
			}
		}
	}
	
	private void broadCastGameBegin(List<Gamer> gamers){
		if(gamers != null){
			BroadcastTO result = new BroadcastTO(EBroadcastCode.GAMER_START.getCode(), "游戏开始");
			for(Gamer mGamer : gamers){
				IoSession session = mGamer.getSession();
				if(session.isConnected()){
					session.write(JSON.toJSONString(result));
				}
			}
		}
	}

}
