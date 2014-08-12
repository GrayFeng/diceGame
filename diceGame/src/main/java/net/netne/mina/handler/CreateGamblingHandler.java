package net.netne.mina.handler;

import java.util.Random;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.Result;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.param.CreateGamblingParams;
import net.netne.mina.pojo.result.CreateGamblingResult;
import net.netne.mina.utils.GamblingKeyCreator;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * diceGame
 * @date 2014-8-10 下午9:33:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CreateGamblingHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(CreateGamblingHandler.class);

	@Override
	public Result execute(IoSession session,String params) {
		Result result = null;
		try{
			CreateGamblingParams createGamblingParams = JSONObject
					.parseObject(params, CreateGamblingParams.class);
			Gambling gambling = create(session,createGamblingParams);
			if(gambling != null){
				result = Result.getSuccessResult();
				CreateGamblingResult createGamblingResult = new CreateGamblingResult();
				createGamblingResult.setGamblingId(gambling.getId());
				result.setRe(createGamblingResult);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return result;
	}
	
	private Gambling create(IoSession session,CreateGamblingParams createGamblingParams){
		Gambling gambling = new Gambling();
		gambling.setId(GamblingKeyCreator.create());
		Random random = new Random();
		gambling.setBoardNo(random.nextInt(100)+"");
		gambling.setGamerNum(createGamblingParams.getGamerNum());
		gambling.setStatus(GameStatus.WAIT.getCode());
		gambling.setGamerNum(1);
		gambling.setMaxGamerNum(createGamblingParams.getGamerNum());
		GamblingCache.getInstance().add(gambling);
		addGamer(session, gambling, createGamblingParams);
		return gambling;
	}
	
	private void addGamer(IoSession session,Gambling gambling,CreateGamblingParams createGamblingParams){
		LoginInfo loginInfo = MemberCache.getInstance().get(createGamblingParams.getUid());
		Gamer gamer = new Gamer();
		gamer.setUid(createGamblingParams.getUid());
		gamer.setId(loginInfo.getMember().getId());
		gamer.setGamestatus(GamerStatus.NEW_JOIN.getCode());
		gamer.setName(loginInfo.getMember().getName());
		gamer.setSex(String.valueOf(loginInfo.getMember().getSex()));
		gamer.setSession(session);
		GamerCache.getInstance().addOne(gambling.getId(), gamer);
	}

}
