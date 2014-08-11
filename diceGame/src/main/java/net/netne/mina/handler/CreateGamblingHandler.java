package net.netne.mina.handler;

import java.util.List;
import java.util.Random;

import net.netne.mina.cache.GamblingCache;
import net.netne.mina.cache.GamerCache;
import net.netne.mina.enums.EEchoCode;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.param.CreateGamblingParams;
import net.netne.mina.pojo.result.CommonResult;
import net.netne.mina.pojo.result.CreateGamblingResult;
import net.netne.mina.utils.GamblingKeyCreator;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * diceGame
 * @date 2014-8-10 下午9:33:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CreateGamblingHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(CreateGamblingHandler.class);

	@Override
	public CommonResult execute(IoSession session,String params) {
		CommonResult result = null;
		try{
			CreateGamblingParams createGamblingParams = JSONObject
					.parseObject(params, CreateGamblingParams.class);
			Gambling gambling = create(session,createGamblingParams);
			if(gambling != null){
				result = new CommonResult();
				CreateGamblingResult createGamblingResult = new CreateGamblingResult();
				createGamblingResult.setGamblingId(gambling.getId());
				result.setCode(EEchoCode.SUCCESS.getCode());
				result.setMsg("成功");
				result.setContent(createGamblingResult);
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
		gambling.setStatus(0);
		gambling.setGamerNum(1);
		gambling.setMaxGamerNum(createGamblingParams.getGamerNum());
		GamblingCache.getInstance().add(gambling);
		addGamer(session, gambling, createGamblingParams);
		return gambling;
	}
	
	private void addGamer(IoSession session,Gambling gambling,CreateGamblingParams createGamblingParams){
		Gamer gamer = new Gamer();
		gamer.setUid(createGamblingParams.getUid());
		gamer.setGamestatus(0);
		gamer.setSession(session);
		List<Gamer> gamers = Lists.newArrayList();
		gamers.add(gamer);
		GamerCache.getInstance().add(gambling.getId(), gamers);
	}

}
