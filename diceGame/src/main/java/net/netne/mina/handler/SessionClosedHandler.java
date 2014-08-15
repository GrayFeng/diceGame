package net.netne.mina.handler;

import java.util.List;
import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.GamerStatus;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.uitls.ResultUtil;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.broadcast.BroadcastTO;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class SessionClosedHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(SessionClosedHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		try{
			String uid = (String)session.getAttribute("uid");
			String gid = (String)session.getAttribute("gbId");
			if(StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(gid)){
				log.error("gamer off line .uid:" + uid + ",gid:" + gid);
				Gambling gambling = GamblingCache.getInstance().get(gid);
				LoginInfo loginInfo = MemberCache.getInstance().get(uid);
				List<Gamer> gamers = GamerCache.getInstance().getGamers(gid);
				if(gambling != null && loginInfo != null){
					//如果游戏尚未开始则解冻用户本局积分
//					if(gambling.getStatus() == GameStatus.WAIT.getCode()){
//						IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
//						memberService.unFreezeScore(loginInfo.getMember().getId(), gambling.getScore());
//					}
					int offLineGamerCount = 0;
					Gamer offlineGamer = GamerCache.getInstance().getOne(gid, uid);
					//检测当前掉线人数
					for(Gamer gamer :gamers){
						//设置掉线用户状态
						if(uid.equals(gamer.getId())){
							offLineGamerCount += 1;
							offlineGamer.setGamestatus(GamerStatus.OFF_LINE.getCode());
							if(gamer.getTokenIndex() == gambling.getTokenIndex() 
									&& (gamer.getGamestatus() == GamerStatus.SHOOK.getCode() 
										|| gamer.getGamestatus() == GamerStatus.GUESSED.getCode())){
								// 如果掉线用户是当前竞猜用户则通知其他玩家
							}
						}else if(gamer.getGamestatus() == GamerStatus.OFF_LINE.getCode()){
							offLineGamerCount += 1;
						}else if(gamer.getGamestatus() != GamerStatus.OFF_LINE.getCode()){
							//通知其他玩家当前用户掉线
							BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_OFF_LINE.getCode(),"玩家掉线");
							Map<String,Object> resultMap = Maps.newHashMap();
							resultMap.put("id",offlineGamer.getId());
							broadcastTO.setContent(resultMap);
							gamer.getSession().write(ResultUtil.getJsonString(broadcastTO));
						}
					}
					if(offLineGamerCount == gambling.getGamerNum()){
						GamblingCache.getInstance().remove(gid);
						GamerCache.getInstance().remove(gid);
						log.error("gamer over,all gamer is offline gid:" + gid);
					}else{
						GamerCache.getInstance().addOne(gid,offlineGamer);
						//如果仅剩一个有效玩家，则自动结束游戏
						if(offLineGamerCount == gambling.getGamerNum() - 1){
							BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAME_OVER.getCode(),"游戏结束");
							for(Gamer gamer :gamers){
								if(gamer.getGamestatus() != GamerStatus.OFF_LINE.getCode()){
									gamer.getSession().write(ResultUtil.getJsonString(broadcastTO));
									break;
								}
							}
							GamblingCache.getInstance().remove(gid);
							GamerCache.getInstance().remove(gid);
							log.error("gamer over,all gamer is offline gid:" + gid);
						}
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
}
