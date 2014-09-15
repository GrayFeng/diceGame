package net.netne.mina.broadcast;

import java.util.List;
import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.common.uitls.ResultUtil;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.broadcast.BroadcastTO;
import net.netne.mina.utils.MessageUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-12 下午11:16:43
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class GamerGuessDice implements IBroadcastThread{

	private String gamblingId;

	private Gamer gamer;
	
	private Gamer tokenGamer;

	public GamerGuessDice(String gamblingId, Gamer gamer,Gamer tokenGamer) {
		this.gamblingId = gamblingId;
		this.gamer = gamer;
		this.tokenGamer = tokenGamer;
	}

	@Override
	public void run() {
		Gambling gambling = GamblingCache.getInstance().get(gamblingId);
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gamblingId);
		if(gambling != null && gamers != null){
			BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_GUESSED.getCode(),"玩家已经完成竞猜");
			Map<String,Object> resultMap = Maps.newHashMap();
			resultMap.put("tokenUserId", tokenGamer.getId());
			resultMap.put("preUserId", gamer.getId());
			resultMap.put("preDiceNum", gamer.getGuessDiceNum());
			resultMap.put("preDicePoint", gamer.getGuessDicePoint());
			broadcastTO.setContent(resultMap);
			if(GameStatus.GUESS.getCode().equals(gambling.getStatus())){
				for(Gamer gamer : gamers){
					if((gamer.getGamestatus() == GamerStatus.GUESSED.getCode() 
							|| gamer.getGamestatus() == GamerStatus.SHOOK.getCode())){
						MessageUtils.sendMsg(gamer.getSession(),JSON.toJSONString(broadcastTO));
					}
				}
			}
			
		}
	}
}
