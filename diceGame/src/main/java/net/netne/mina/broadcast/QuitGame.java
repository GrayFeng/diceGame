package net.netne.mina.broadcast;

import java.util.List;
import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.GameStatus;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.broadcast.BroadcastTO;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

/**
 * diceGame
 * 
 * @date 2014-8-12 下午10:32:39
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class QuitGame implements IBroadcastThread {

	private String gamblingId;

	private Gamer gamer;

	public QuitGame(String gamblingId, Gamer gamer) {
		this.gamblingId = gamblingId;
		this.gamer = gamer;
	}

	@Override
	public void run() {
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gamblingId);
		if (gamers != null && gamers.size() > 0) {
			BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_QUIT.getCode(),"玩家退出");
			Map<String,Object> params = Maps.newHashMap();
			params.put("id", gamer.getId());
			broadcastTO.setContent(params);
			for (Gamer mGamer : gamers) {
				IoSession session = mGamer.getSession();
				if (session.isConnected() 
						&& !mGamer.getUid().equals(gamer.getUid())) {
					session.write(JSON.toJSONString(broadcastTO));
				}
			}
		}else{
			Gambling gambling = GamblingCache.getInstance().get(gamblingId);
			if(gambling != null){
				gambling.setStatus(GameStatus.WAIT.getCode());
				gambling.setGamerNum(0);
				gambling.setFast(false);
				gambling.setDiceNum(0);
				gambling.setDicePoint(0);
				GamblingCache.getInstance().add(gambling);
			}
		}
	}

}
