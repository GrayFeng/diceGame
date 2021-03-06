package net.netne.mina.handler;

import java.util.List;
import java.util.Map;

import net.netne.common.Constant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EActionCode;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.GamerGuessDice;
import net.netne.mina.broadcast.GamerGuessTimeOut;
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
						&& gamer.getUid().equals(gambling.getCurrentGuessGamerId())
						&& (gamer.getGamestatus() == GamerStatus.SHOOK.getCode()
								|| gamer.getGamestatus() == GamerStatus.GUESSED.getCode())){
					if(EActionCode.GUESS_TIMEOUT.getCode().equals(guessDiceParam.getActionCode())){
						Gamer tokenGamer = getTokenGamer(gambling,gamer.getUid());
						gambling.setCurrentGuessGamerId(tokenGamer.getUid());
						gambling.setTokenIndex(tokenGamer.getTokenIndex());
						GamblingCache.getInstance().add(gambling);
						gamer.setGamestatus(GamerStatus.GUESSED.getCode());
						gamer.setTimeOutCount(gamer.getTimeOutCount() + 1);
						GamerCache.getInstance().addOne(guessDiceParam.getGamblingId(), gamer);
						result = MinaResult.getSuccessResult();
						BroadcastThreadPool.execute(new GamerGuessTimeOut(gambling.getId(),gamer,tokenGamer));
						result = MinaResult.getSuccessResult();
					}else{
						// 如果竞猜点数有效
						if(checkGuessDice(gambling,guessDiceParam)){
							Gamer tokenGamer = getTokenGamer(gambling,gamer.getUid());
							gambling.setLastGuessGamerId(guessDiceParam.getUid());
							gambling.setCurrentGuessGamerId(tokenGamer.getUid());
							gambling.setDiceNum(guessDiceParam.getDiceNum());
							gambling.setDicePoint(guessDiceParam.getDicePoint());
							gambling.setTokenIndex(tokenGamer.getTokenIndex());
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
			}else if(gambling != null 
						&& gambling.getStatus() == GameStatus.OPENING.getCode()){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"其他玩家已叫开，请等待结果");
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"请您等待其他人先完成竞猜");
			}
		}
		return result;
	}
	
	
	private boolean checkGuessDice(Gambling gambling,GuessDiceParam guessDiceParam){
		if(guessDiceParam.getDiceNum() != null 
				&& guessDiceParam.getDicePoint() != null
				&& guessDiceParam.getDicePoint() <= Constant.MAX_DICE_POINT
				&& guessDiceParam.getDiceNum() <= Constant.MAX_DICE_NUM * gambling.getGamerNum()
				&& guessDiceParam.getDiceNum() >= gambling.getGamerNum()){
			//叫点数量与玩家数量一致或叫了点数1，则默认为斋
			if(!gambling.isFast() 
					&& guessDiceParam.getDicePoint() == 1){
				gambling.setFast(true);
			}
			//如果玩家竞猜个数与当前一致且点数大于当前，则视为有效
			if(guessDiceParam.getDiceNum() == gambling.getDiceNum() 
					&& guessDiceParam.getDicePoint() > gambling.getDicePoint()){
				return true;
			}else if(guessDiceParam.getDiceNum() > gambling.getDiceNum()){
				return true;
			}
		}
		return false;
	}
	
	private Gamer getTokenGamer(Gambling gambling,String uid){
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gambling.getId());
		Gamer currentGamer = GamerCache.getInstance().getOne(gambling.getId(),uid);
		Gamer tokenGamer = null;
		Integer tokenIndex = 0;
		try{
			if(currentGamer != null){
				int index = gamers.indexOf(currentGamer);
				if(index < (gamers.size() -1)){
					tokenIndex = index + 1;
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		tokenGamer = gamers.get(tokenIndex);
		if(tokenGamer.getGamestatus() == GamerStatus.GUESSED.getCode() 
				|| tokenGamer.getGamestatus() == GamerStatus.SHOOK.getCode()){
			return tokenGamer;
		}
		for(Gamer gamer : gamers){
			if(gamer.getGamestatus() == GamerStatus.GUESSED.getCode() 
					|| gamer.getGamestatus() == GamerStatus.SHOOK.getCode()){
				if(!gamer.getUid().equals(uid)){
					tokenGamer = gamer;
					break;
				}
			}
		}
		return tokenGamer;
	}
}
