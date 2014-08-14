package net.netne.mina.handler;

import java.util.List;
import java.util.Map;

import net.netne.common.Constant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.GamerGuessDice;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.param.GuessDiceParam;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class GuessDiceHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(GuessDiceHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = null;
		try{
			GuessDiceParam guessDiceParam = JSONObject
					.parseObject(params, GuessDiceParam.class);
			Gambling gambling = GamblingCache.getInstance().get(guessDiceParam.getGamblingId());
			if(gambling != null 
					&& gambling.getStatus() == GameStatus.GUESS.getCode()){
				Map<String,Gamer> gamers = GamerCache.getInstance().getGamerMap(gambling.getId());
				Gamer gamer = gamers.get(guessDiceParam.getUid());
				if(gamer != null 
						&& gamer.getTokenIndex() == gambling.getTokenIndex()
						&& (gamer.getGamestatus() == GamerStatus.SHOOK.getCode()
								|| gamer.getGamestatus() == GamerStatus.GUESSED.getCode())){
					boolean GuessEffective = false;
					if(guessDiceParam.getDiceNum() != null 
							&& guessDiceParam.getDicePoint() != null
							&& guessDiceParam.getDicePoint() <= Constant.MAX_DICE_POINT
							&& guessDiceParam.getDiceNum() <= Constant.MAX_DICE_NUM){
						//如果玩家竞猜个数与当前一致且点数大于当前，则视为有效
						if(guessDiceParam.getDiceNum() == gambling.getDiceNum() 
								&& guessDiceParam.getDicePoint() > gambling.getDicePoint()){
							GuessEffective = true;
						}else if(guessDiceParam.getDiceNum() > gambling.getDiceNum()){
							GuessEffective = true;
						}
					}
					if(GuessEffective){
						Gamer tokenGamer = getTokenGamer(gambling);
						gambling.setDiceNum(guessDiceParam.getDiceNum());
						gambling.setDicePoint(gambling.getDicePoint());
						GamblingCache.getInstance().add(gambling);
						gamer.setGamestatus(GamerStatus.GUESSED.getCode());
						gamer.setGuessDiceNum(guessDiceParam.getDiceNum());
						gamer.setGuessDicePoint(guessDiceParam.getDicePoint());
						GamerCache.getInstance().addOne(guessDiceParam.getGamblingId(), gamer);
						result = MinaResult.getSuccessResult();
						BroadcastThreadPool.execute(new GamerGuessDice(gambling.getId(),gamer,tokenGamer));
					}else{
						result = new MinaResult(EEchoCode.ERROR.getCode(),"竞猜点数无效");
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"还没有轮到您进行竞猜");
			}
		}
		return result;
	}
	
	private Gamer getTokenGamer(Gambling gambling){
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gambling.getId());
		Gamer tokenGamer = null;
		for(int i = 0;i < gamers.size();i++){
			Gamer gamer = gamers.get(i);
			if(gamer.getGamestatus() == GamerStatus.GUESSED.getCode() 
					|| gamer.getGamestatus() == GamerStatus.SHOOK.getCode()){
				if(tokenGamer == null){
					tokenGamer = gamer;
					gambling.setTokenIndex(i);
				}
				if(gambling.getTokenIndex() < (i)){
					tokenGamer = gamer;
					gambling.setTokenIndex(i);
					break;
				}
			}
		}
		return tokenGamer;
	}
}
