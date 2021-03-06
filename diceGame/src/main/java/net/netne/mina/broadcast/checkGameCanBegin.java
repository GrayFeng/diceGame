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
import net.netne.mina.pojo.broadcast.NewGamerJoinTO;
import net.netne.mina.utils.MessageUtils;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;

/**
 * diceGame
 * @date 2014-8-12 下午10:28:32
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class checkGameCanBegin implements IBroadcastThread{
	
	private String gamblingId;
	
	private Gamer gamer;
	
	public checkGameCanBegin(String gamblingId,Gamer gamer){
		this.gamblingId = gamblingId;
		this.gamer = gamer;
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
				}else{
					broadCastGamerReady(gamers);
				}
			}
		}
	}
	
	private void broadCastGamerReady(List<Gamer> gamers){
		BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_READY.getCode(),"玩家准备");
		NewGamerJoinTO newGamerJoin = new NewGamerJoinTO();
		newGamerJoin.setId(gamer.getId());
		broadcastTO.setContent(newGamerJoin);
		for (Gamer mGamer : gamers) {
			IoSession session = mGamer.getSession();
			if (session.isConnected() 
					&& !mGamer.getUid().equals(gamer.getUid())) {
				MessageUtils.sendMsg(session,JSON.toJSONString(broadcastTO));
			}
		}
	}
	
	private void broadCastGameBegin(List<Gamer> gamers){
		if(gamers != null){
			BroadcastTO result = new BroadcastTO(EBroadcastCode.GAMER_START.getCode(), "游戏开始");
			for(Gamer mGamer : gamers){
				IoSession session = mGamer.getSession();
				if(session.isConnected()){
					MessageUtils.sendMsg(session,JSON.toJSONString(result));
				}
			}
		}
	}

}
