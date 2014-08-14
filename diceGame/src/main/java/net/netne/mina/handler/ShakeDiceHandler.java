package net.netne.mina.handler;

import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.GamerShakeDice;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.param.ShakeDiceParam;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ShakeDiceHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(ShakeDiceHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = null;
		try{
			ShakeDiceParam shakeDiceParam = JSONObject
					.parseObject(params, ShakeDiceParam.class);
			Gambling gambling = GamblingCache.getInstance().get(shakeDiceParam.getGamblingId());
			if(gambling != null && gambling.getStatus() == GameStatus.START.getCode()){
				Map<String,Gamer> gamers = GamerCache.getInstance().getGamerMap(gambling.getId());
				Gamer gamer = gamers.get(shakeDiceParam.getUid());
				if(gamer != null && gamer.getGamestatus() == GamerStatus.READY.getCode()){
					gamer.setDicePoint(shakeDiceParam.getDicePoint());
					gamer.setGamestatus(GamerStatus.SHOOK.getCode());
					GamerCache.getInstance().addOne(shakeDiceParam.getGamblingId(), gamer);
					result = MinaResult.getSuccessResult();
					BroadcastThreadPool.execute(new GamerShakeDice(gambling.getId(),gamer));
				}else{
					result = new MinaResult(EEchoCode.ERROR.getCode(),"您已经摇过骰子了");
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"游戏尚未开始");
			}
		}
		return result;
	}
}
