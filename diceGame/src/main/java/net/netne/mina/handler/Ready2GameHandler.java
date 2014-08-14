package net.netne.mina.handler;

import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.checkGameCanBegin;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.param.Ready2GameParam;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Ready2GameHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(Ready2GameHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = null;
		try{
			Ready2GameParam ready2GameParam = JSONObject
					.parseObject(params, Ready2GameParam.class);
			Gambling gambling = GamblingCache.getInstance().get(ready2GameParam.getGamblingId());
			if(gambling != null && gambling.getStatus() == GameStatus.WAIT.getCode()){
				Map<String,Gamer> gamers = GamerCache.getInstance().getGamerMap(gambling.getId());
				Gamer gamer = gamers.get(ready2GameParam.getUid());
				if(gamer != null && gamer.getGamestatus() == GamerStatus.NEW_JOIN.getCode()){
					gamer.setGamestatus(GamerStatus.READY.getCode());
					GamerCache.getInstance().addOne(ready2GameParam.getGamblingId(), gamer);
					result = MinaResult.getSuccessResult();
					BroadcastThreadPool.execute(new checkGameCanBegin(gambling.getId(),gamer));
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"用户无需确认准备");
			}
		}
		return result;
	}
}
