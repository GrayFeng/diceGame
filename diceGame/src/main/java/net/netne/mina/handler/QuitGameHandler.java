package net.netne.mina.handler;

import java.util.Map;

import net.netne.api.service.IScoreService;
import net.netne.common.SpringConstant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GamerStatus;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.QuitGame;
import net.netne.mina.broadcast.checkGameCanBegin4Quit;
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
			if(gambling != null){
				Map<String,Gamer> gamers = GamerCache.getInstance().getGamerMap(gambling.getId());
				if(gamers != null){
					Gamer gamer = gamers.get(quitGameParam.getUid());
					if(gamer != null){
						IScoreService scoreService = SpringConstant.getBean("scoreServiceImpl");
						if(gamer.getGamestatus() != GamerStatus.NEW_JOIN.getCode() 
								&& gamer.getGamestatus() != GamerStatus.READY.getCode()){
							scoreService.addScore(gamer.getId(), -gambling.getScore());
						}
						GamerCache.getInstance().removeOne(quitGameParam.getGamblingId(),gamer.getUid());
						if(gambling.getGamerNum() == 1){
							GamblingCache.getInstance().remove(gambling.getId());
						}else{
							gambling.setGamerNum(gambling.getGamerNum() - 1);
							GamblingCache.getInstance().add(gambling);
							BroadcastThreadPool.execute(new QuitGame(gambling.getId(), gamer));
							BroadcastThreadPool.execute(new checkGameCanBegin4Quit(gambling.getId()));
						}
						result = MinaResult.getSuccessResult();
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"游戏退出失败");
			}
		}
		return result;
	}
}
