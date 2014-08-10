package net.netne.mina.handler;

import java.util.List;
import java.util.Random;

import org.apache.mina.core.session.IoSession;

import net.netne.mina.cache.GamblingCache;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.param.CreateGamblingParams;
import net.netne.mina.pojo.param.IParams;
import net.netne.mina.pojo.result.CommonResult;
import net.netne.mina.pojo.result.CreateGamblingResult;
import net.netne.mina.utils.GamblingKeyCreator;

import com.google.common.collect.Lists;

/**
 * diceGame
 * @date 2014-8-10 下午9:33:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CreateGamblingHandler implements IHandler{

	@Override
	public CommonResult execute(IoSession session,IParams paramObj) {
		CreateGamblingParams params = (CreateGamblingParams)paramObj;
		Gambling gambling = new Gambling();
		gambling.setId(GamblingKeyCreator.create());
		Random random = new Random();
		gambling.setBoardNo(random.nextInt(100)+"");
		gambling.setGamerNum(params.getGamerNum());
		Gamer gamer = new Gamer();
		gamer.setUid(params.getUid());
		gamer.setGamestatus(0);
		gamer.setSession(session);
		List<Gamer> gamers = Lists.newArrayList();
		gamers.add(gamer);
		gambling.setGamerList(gamers);
		GamblingCache.getInstance().add(gambling);
		
		CommonResult result = new CommonResult();
		CreateGamblingResult createGamblingResult = new CreateGamblingResult();
		createGamblingResult.setGamblingId(gambling.getId());
		result.setCode("200");
		result.setMsg("成功");
		result.setContent(createGamblingResult);
		return result;
	}

}
