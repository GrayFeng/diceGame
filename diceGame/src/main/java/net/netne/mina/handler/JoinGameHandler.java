package net.netne.mina.handler;

import java.util.List;

import net.netne.api.service.IMemberService;
import net.netne.common.SpringConstant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.enums.GamerStatus;
import net.netne.common.pojo.LoginInfo;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.NewGamerJoin;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.param.JoinGameParam;
import net.netne.mina.pojo.result.GamerVO;
import net.netne.mina.pojo.result.JoinGameResult;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

public class JoinGameHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(JoinGameHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = null;
		try{
			JoinGameParam joinGameParam = JSONObject
					.parseObject(params, JoinGameParam.class);
			Gambling gambling = GamblingCache.getInstance().get(joinGameParam.getGamblingId());
			//游戏状态为等待中且人数未满可加入游戏
			if(gambling != null && gambling.getStatus() == GameStatus.WAIT.getCode() 
					&& gambling.getGamerNum() < gambling.getMaxGamerNum()){
				if(GamerCache.getInstance().getOne(joinGameParam.getUid()) == null){
					IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
					LoginInfo loginInfo = MemberCache.getInstance().get(joinGameParam.getUid());
					if(loginInfo != null && loginInfo.getMember() != null){
						Integer memberId = loginInfo.getMember().getId();
						if(memberService.checkScore(memberId, gambling.getScore())){
							memberService.freezeScore(memberId, gambling.getScore());
							gambling.setGamerNum(gambling.getGamerNum() + 1);
							GamblingCache.getInstance().add(gambling);
							//建立新玩家
							Gamer newGamer = createNewGamer(joinGameParam.getUid(),session,loginInfo);
							newGamer.setTokenIndex(gambling.getGamerNum() - 1);
							JoinGameResult jonGameResult = new JoinGameResult();
							List<Gamer> gamers = GamerCache.getInstance().getGamers(gambling.getId());
							//读取现有玩家信息
							List<GamerVO> gamerVOList = getOldGamerList(gamers);
							jonGameResult.setGamers(gamerVOList);
							result = MinaResult.getSuccessResult();
							result.setRe(jonGameResult);
							GamerCache.getInstance().addOne(gambling.getId(),newGamer);
							session.setAttribute("gbId", gambling.getId());
							//广播通知其他玩家
							BroadcastThreadPool.execute(new NewGamerJoin(gambling.getId(), newGamer));
						}else{
							result = new MinaResult(EEchoCode.ERROR.getCode(),"您的积分不足无法加入游戏");
						}
					}else{
						result = new MinaResult(EEchoCode.ERROR.getCode(),"缺少用户信息");
					}
				}else{
					result = new MinaResult(EEchoCode.ERROR.getCode(),"您已在游戏游戏中");
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"人数已满，游戏已不可加入");
			}
		}
		return result;
	}
	
	private Gamer createNewGamer(String uid,IoSession session,LoginInfo loginInfo){
		Gamer newGamer = new Gamer();
		newGamer.setUid(uid);
		newGamer.setId(loginInfo.getMember().getId());
		newGamer.setSession(session);
		newGamer.setName(loginInfo.getMember().getName());
		newGamer.setSex(loginInfo.getMember().getSex());
		newGamer.setGamestatus(GamerStatus.NEW_JOIN.getCode());
		newGamer.setPhotoUrl(loginInfo.getMember().getPhotoUrl());
		return newGamer;
	}
	
	private List<GamerVO> getOldGamerList(List<Gamer> gamers){
		List<GamerVO> gamerVOList = Lists.newArrayList();
		if(gamers != null && gamers.size() > 0){
			for(Gamer gamer : gamers){
				GamerVO gamerVO = new GamerVO();
				gamerVO.setId(gamer.getId());
				gamerVO.setName(gamer.getName());
				gamerVO.setSex(gamer.getSex());
				gamerVO.setPhotoUrl(gamer.getPhotoUrl());
				gamerVOList.add(gamerVO);
			}
		}
		return gamerVOList;
	}

}
