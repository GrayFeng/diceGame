package net.netne.mina.handler;

import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.param.QuitGameParam;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class QuitGameHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(QuitGameHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = null;
		try{
			QuitGameParam quitGameParam = JSONObject
					.parseObject(params, QuitGameParam.class);
			Gambling gambling = GamblingCache.getInstance().get(quitGameParam.getGamblingId());
			if(gambling != null && (gambling.getStatus() == GameStatus.WAIT.getCode() 
					|| gambling.getStatus() == GameStatus.OVER.getCode())){
				Map<String,Gamer> gamers = GamerCache.getInstance().getGamerMap(gambling.getId());
				Gamer gamer = gamers.get(quitGameParam.getUid());
				if(gamer != null 
						&& (gamer.getGamestatus() == GamerStatus.NEW_JOIN.getCode() 
							|| gamer.getGamestatus() == GamerStatus.READY.getCode())){
					GamerCache.getInstance().removeOne(gamer.getUid(), quitGameParam.getGamblingId());
					result = MinaResult.getSuccessResult();
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"游戏已经开始，无法退出");
			}
		}
		return result;
	}
}
