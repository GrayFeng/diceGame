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

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;

/**
 * diceGame
 * @date 2014-8-12 下午11:16:43
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class GamerShakeDice implements IBroadcastThread{

	private String gamblingId;

	private Gamer gamer;

	public GamerShakeDice(String gamblingId, Gamer gamer) {
		this.gamblingId = gamblingId;
		this.gamer = gamer;
	}

	@Override
	public void run() {
		Gambling gambling = GamblingCache.getInstance().get(gamblingId);
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gamblingId);
		if(gambling != null && gamers != null){
			if(GameStatus.START.getCode().equals(gambling.getStatus())){
				int shookCount = 0;
				for(Gamer gamer : gamers){
					if(!GamerStatus.SHOOK.getCode().equals(gamer.getGamestatus())){
						break;
					}else{
						shookCount += 1;
					}
				}
				if(shookCount == gambling.getMaxGamerNum()){
					gambling.setStatus(GameStatus.REPORT_NO.getCode());
					GamblingCache.getInstance().add(gambling);
					broadCastStartReport(gamers);
				}else{
					broadCastShakeDice(gamers);
				}
			}
			
		}
	}
	
	private void broadCastShakeDice(List<Gamer> gamers){
		BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_SHOOK.getCode(),"玩家已经摇完");
		NewGamerJoinTO newGamerJoin = new NewGamerJoinTO();
		newGamerJoin.setId(gamer.getId());
		broadcastTO.setContent(newGamerJoin);
		for (Gamer mGamer : gamers) {
			IoSession session = mGamer.getSession();
			if (session.isConnected() 
					&& !mGamer.getUid().equals(gamer.getUid())) {
				session.write(JSON.toJSONString(broadcastTO));
			}
		}
	}
	
	private void broadCastStartReport(List<Gamer> gamers){
		if(gamers != null){
			BroadcastTO result = new BroadcastTO(EBroadcastCode.GAMER_START_REPORT.getCode(), "上报点数");
			for(Gamer mGamer : gamers){
				IoSession session = mGamer.getSession();
				if(session.isConnected()){
					session.write(JSON.toJSONString(result));
				}
			}
		}
	}

}
