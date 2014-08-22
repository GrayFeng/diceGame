package net.netne.mina.handler;

import java.util.List;
import java.util.Map;

import net.netne.api.service.IMemberService;
import net.netne.api.service.IScoreService;
import net.netne.common.SpringConstant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.pojo.Member;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.OpenIt;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.broadcast.DiceInfo;
import net.netne.mina.pojo.param.OpenParam;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class OpenHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(OpenHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = null;
		try{
			OpenParam openParam = JSONObject
					.parseObject(params, OpenParam.class);
			Gambling gambling = GamblingCache.getInstance().get(openParam.getGamblingId());
			if(gambling != null && gambling.getStatus() == GameStatus.GUESS.getCode()){
				Map<String,Gamer> gamers = GamerCache.getInstance().getGamerMap(gambling.getId());
				Gamer openGamer = gamers.get(openParam.getUid());
				Gamer lastGuessGamer = gamers.get(gambling.getLastGuessGamerId());
				if(openGamer != null && gambling.getDiceNum() > 0 && gambling.getDicePoint() > 0){
					gambling.setStatus(GameStatus.OPENING.getCode());
					GamblingCache.getInstance().add(gambling);
					Map<String,Integer> diceMap = Maps.newHashMap();
					List<DiceInfo> diceInfoList = Lists.newArrayList();
					for(Gamer gamer : gamers.values()){
						if(StringUtils.isNotEmpty(gamer.getDicePoint())){
							DiceInfo diceInfo = new DiceInfo();
							diceInfo.setDice(gamer.getDicePoint());
							diceInfo.setUserId(gamer.getId());
							diceInfo.setUserName(gamer.getName());
							diceInfoList.add(diceInfo);
							String[] dices = gamer.getDicePoint().split("-");
							if(dices != null){
								for(String dice : dices){
									if(NumberUtils.isNumber(dice)){
										Integer num = diceMap.get(dice);
										if(num == null){
											num = 1;
										}else{
											num += 1;
										}
										diceMap.put(dice, num);
									}
								}
							}
						}
					}
					Integer allDiceNum = diceMap.get(String.valueOf(lastGuessGamer.getGuessDicePoint()));
					allDiceNum = allDiceNum == null ? 0 :allDiceNum;
					IScoreService scoreService = SpringConstant.getBean("scoreServiceImpl");
					IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
					Integer score = gambling.getScore();
					result = new MinaResult(EEchoCode.SUCCESS.getCode(),"等待结果!");
					boolean isLastGamerWin = true;
					if(lastGuessGamer.getGuessDiceNum() > allDiceNum){
						isLastGamerWin = false;
					}
					for(Gamer gamer : gamers.values()){
						if(gamer.getId() == lastGuessGamer.getId()){
							if(isLastGamerWin){
								scoreService.settleScore(score, 0, gamer.getId());
							}else{
								scoreService.settleScore(-score, 0, gamer.getId());
							}
						}else{
							if(isLastGamerWin){
								scoreService.settleScore(-score, 0, gamer.getId());
							}else{
								scoreService.settleScore(score, 0, gamer.getId());
							}
						}
						Member member = memberService.getMemberById(gamer.getId());
						MemberCache.getInstance().updateMember(gamer.getUid(), member);
					}
					BroadcastThreadPool.execute(new OpenIt(gambling.getId(),lastGuessGamer,isLastGamerWin,diceInfoList));
					gambling.setStatus(GameStatus.WAIT.getCode());
					gambling.setCurrentGuessGamerId(null);
					gambling.setLastGuessGamerId(null);
					gambling.setDiceNum(0);
					gambling.setDicePoint(0);
					gambling.setFast(false);
					gambling.setTokenIndex(0);
					GamblingCache.getInstance().add(gambling);
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"还没有玩家竞猜点数，无法开盅");
			}
		}
		return result;
	}
}
