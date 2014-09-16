package net.netne.mina.handler;

import java.util.Collection;
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
				if(openParam.getUid().equals(gambling.getLastGuessGamerId())){
					return new MinaResult(EEchoCode.ERROR.getCode(),"其他玩家没有完成竞猜，无法开盅");
				}
				Map<String,Gamer> gamers = GamerCache.getInstance().getGamerMap(gambling.getId());
				Gamer openGamer = gamers.get(openParam.getUid());
				Gamer lastGuessGamer = gamers.get(gambling.getLastGuessGamerId());
				if(openGamer != null && gambling.getDiceNum() > 0 
						&& gambling.getDicePoint() > 0 ){
					if(openGamer.getUid().equals(gambling.getCurrentGuessGamerId())){
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
						//是否计算点数1的数量
						if(!gambling.isFast()){
							Integer oneDiceNum = diceMap.get(String.valueOf(1));
							oneDiceNum = oneDiceNum == null ? 0 :oneDiceNum;
							allDiceNum += oneDiceNum;
						}
						Integer score = gambling.getScore();
						result = new MinaResult(EEchoCode.SUCCESS.getCode(),"等待结果!");
						boolean isLastGamerWin = true;
						if(lastGuessGamer.getGuessDiceNum() > allDiceNum){
							isLastGamerWin = false;
						}                                                                         
						//处理用户分数
						handleScore(gamers.values(),openGamer, lastGuessGamer,score, isLastGamerWin);
						BroadcastThreadPool.execute(new OpenIt(gambling.getId(),lastGuessGamer,openGamer,isLastGamerWin,diceInfoList,allDiceNum));
						gambling.setStatus(GameStatus.WAIT.getCode());
						gambling.setLastGuessGamerId(null);
						gambling.setDiceNum(0);
						gambling.setDicePoint(0);
						gambling.setFast(false);
						if(isLastGamerWin){
							gambling.setTokenIndex(openGamer.getTokenIndex());
							gambling.setCurrentGuessGamerId(openGamer.getUid());
						}else{
							gambling.setTokenIndex(lastGuessGamer.getTokenIndex());
							gambling.setCurrentGuessGamerId(lastGuessGamer.getUid());
						}
						GamblingCache.getInstance().add(gambling);
					}else{
						result = new MinaResult(EEchoCode.ERROR.getCode(),"还没有轮到您，无法开盅");
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"还没有玩家竞猜点数，无法叫开");
			}
		}
		return result;
	}

	private void handleScore(Collection<Gamer> gamers, Gamer openGamer,
			Gamer lastGuessGamer, Integer score, boolean isLastGamerWin) {
		IScoreService scoreService = SpringConstant.getBean("scoreServiceImpl");
		IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
		for(Gamer gamer : gamers){
			//最后一个叫点用户
			if(gamer.getId() == lastGuessGamer.getId() && isLastGamerWin){
				if(isLastGamerWin){
					scoreService.settleScore(score - 10, 0, gamer.getId());
				}else{
					scoreService.settleScore(-(score + 10), 0, gamer.getId());
				}
			}else if(gamer.getId() == openGamer.getId()){
				//当前叫开用户
				if(isLastGamerWin){
					scoreService.settleScore(-(score + 10), 0, gamer.getId());
				}else{
					scoreService.settleScore(score - 10, 0, gamer.getId());
				}
			}else{
				//其他参与用户
				scoreService.settleScore(-10, 0, gamer.getId());
			}
			Member member = memberService.getMemberById(gamer.getId());
			MemberCache.getInstance().updateMember(gamer.getUid(), member);
		}
	}
}
