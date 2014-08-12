package net.netne.mina.handler;

import java.util.List;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Result;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.broadcast.NewGamerJoin;
import net.netne.mina.pojo.param.JoinGameParam;
import net.netne.mina.pojo.result.GamerVO;
import net.netne.mina.pojo.result.JoinGameResult;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class JoinGameHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(JoinGameHandler.class);

	@Override
	public Result execute(IoSession session, String params) {
		Result result = null;
		try{
			JoinGameParam joinGameParam = JSONObject
					.parseObject(params, JoinGameParam.class);
			Gambling gambling = GamblingCache.getInstance().get(joinGameParam.getGamblingId());
			//游戏状态为等待中且人数未满可加入游戏
			if(gambling != null && gambling.getStatus() == 0 
					&& gambling.getGamerNum() < gambling.getMaxGamerNum()){
				gambling.setGamerNum(gambling.getGamerNum() + 1);
				GamblingCache.getInstance().add(gambling);
				Gamer newGamer = new Gamer();
				newGamer.setUid(joinGameParam.getUid());
				newGamer.setSession(session);
				newGamer.setGamestatus(0);
				broadCast(gambling, newGamer);
				List<Gamer> gamers = GamerCache.getInstance().getGamers(gambling.getId());
				JoinGameResult jonGameResult = new JoinGameResult();
				List<GamerVO> gamerVOList = Lists.newArrayList();
				for(Gamer gamer : gamers){
					GamerVO gamerVO = new GamerVO();
					gamerVO.setName(gamer.getUid());
					gamerVOList.add(gamerVO);
				}
				jonGameResult.setGamers(gamerVOList);
				result = Result.getSuccessResult();
				result.setRe(jonGameResult);
				gamers.add(newGamer);
				GamerCache.getInstance().add(gambling.getId(),gamers);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"游戏已不可加入");
				session.close(false);
			}
		}
		return result;
	}
	
	private void broadCast(Gambling gambling,Gamer gamer){
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gambling.getId());
		if(gamers != null){
			NewGamerJoin newGamerJoin = new NewGamerJoin();
			newGamerJoin.setCode(EBroadcastCode.GAMER_JOININ.getCode());
			newGamerJoin.setNewGamerName(gamer.getUid());
			for(Gamer mGamer : gamers){
				IoSession session = mGamer.getSession();
				if(session.isConnected()){
					session.write(JSON.toJSONString(newGamerJoin));
				}
			}
		}
	}

}
