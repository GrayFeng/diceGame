package net.netne.mina.handler;

import java.util.List;
import java.util.Map;

import net.netne.api.service.IMemberService;
import net.netne.common.SpringConstant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.GameStatus;
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
					if(gambling.getStatus() == GameStatus.WAIT.getCode()){
						IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
						memberService.unFreezeScore(loginInfo.getMember().getId(), gambling.getScore());
					}else{
						int offLineGamerCount = 0;
						Gamer offlineGamer = null;
						for(Gamer gamer :gamers){
							if(uid.equals(gamer.getId())){
								offlineGamer = gamer;
								offLineGamerCount += 1;
								offlineGamer.setGamestatus(GamerStatus.OFF_LINE.getCode());
							}else if(gamer.getGamestatus() == GamerStatus.OFF_LINE.getCode()){
								offLineGamerCount += 1;
							}else if(gamer.getGamestatus() != GamerStatus.OFF_LINE.getCode()){
								BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_OFF_LINE.getCode(),"玩家掉线");;
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
