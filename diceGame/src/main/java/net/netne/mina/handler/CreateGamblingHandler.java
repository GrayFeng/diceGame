package net.netne.mina.handler;

import net.netne.api.service.IMemberService;
import net.netne.common.SpringConstant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.common.pojo.LoginInfo;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
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
	
	private static Integer roomNum = 0;
	
	@Override
	public MinaResult execute(IoSession session,String params) {
		MinaResult result = null;
		try{
			CreateGamblingParams createGamblingParams = JSONObject
					.parseObject(params, CreateGamblingParams.class);
			LoginInfo loginInfo = MemberCache.getInstance().get(createGamblingParams.getUid());
			IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
			if(createGamblingParams.getScore() == null){
				createGamblingParams.setScore(50);
			}
			if(createGamblingParams.getGamerNum() == null){
				createGamblingParams.setGamerNum(5);
			}
			if(GamerCache.getInstance().getOne(createGamblingParams.getUid()) == null){
				if(createGamblingParams.getScore() > 0){
					//检测用户积分是否满足开局条件
					if(memberService.checkScore(loginInfo.getMember().getId(), 
							createGamblingParams.getScore())){
						memberService.freezeScore(loginInfo.getMember().getId(), 
								createGamblingParams.getScore());
						Gambling gambling = create(session,createGamblingParams);
						if(gambling != null){
							session.setAttribute("gbId", gambling.getId());
							result = MinaResult.getSuccessResult();
							CreateGamblingResult createGamblingResult = new CreateGamblingResult();
							createGamblingResult.setGamblingId(gambling.getId());
							createGamblingResult.setBoardNo(gambling.getBoardNo());
							createGamblingResult.setScore(gambling.getScore());
							result.setRe(createGamblingResult);
						}
					}else{
						result = new MinaResult(EEchoCode.ERROR.getCode(),"您的积分不足无法创建游戏");
					}
				}else{
					result = new MinaResult(EEchoCode.ERROR.getCode(),"请设置有效的开局积分数量");
				}
			}else{
				result = new MinaResult(EEchoCode.ERROR.getCode(),"您已经在游戏中，无法继续创建游戏");
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return result;
	}
	
	private Gambling create(IoSession session,CreateGamblingParams createGamblingParams){
		Gambling gambling = new Gambling();
		gambling.setId(GamblingKeyCreator.create());
		gambling.setBoardNo(getRoomNum().toString());
		gambling.setStatus(GameStatus.WAIT.getCode());
		gambling.setGamerNum(1);
		gambling.setMaxGamerNum(createGamblingParams.getGamerNum());
		gambling.setScore(createGamblingParams.getScore());
		gambling.setFast(false);
		gambling.setMakerId(createGamblingParams.getUid());
		gambling.setDiceNum(0);
		gambling.setDicePoint(0);
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
		gamer.setSex(loginInfo.getMember().getSex());
		gamer.setTokenIndex(gambling.getGamerNum() - 1);
		gamer.setPhotoUrl(loginInfo.getMember().getPhotoUrl());
		gamer.setSession(session);
		gamer.setGamblingId(gambling.getId());
		GamerCache.getInstance().addOne(gambling.getId(), gamer);
	}
	
	private synchronized String getRoomNum(){
		String roomNo = GamblingCache.getInstance().getFreeRoomNo();
		if(roomNo == null){
			CreateGamblingHandler.roomNum += 1;
			roomNo = CreateGamblingHandler.roomNum.toString();
		}
		return roomNo;
	}

}
