package net.netne.mina.handler;

import net.netne.api.service.IMemberService;
import net.netne.common.SpringConstant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.enums.GameStatus;
import net.netne.common.pojo.LoginInfo;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.MinaResult;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionClosedHandler extends AbstractHandler implements IHandler{
	
	private Logger log = LoggerFactory.getLogger(SessionClosedHandler.class);

	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = null;
		try{
			String uid = (String)session.getAttribute("uid");
			String gid = (String)session.getAttribute("gbId");
			if(StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(gid)){
				log.error("gamer off line .uid:" + uid + ",gid:" + gid);
				Gambling gambling = GamblingCache.getInstance().get(gid);
				LoginInfo loginInfo = MemberCache.getInstance().get(uid);
				if(gambling != null && loginInfo != null){
					if(gambling.getStatus() == GameStatus.WAIT.getCode()){
						IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
						memberService.unFreezeScore(loginInfo.getMember().getId(), gambling.getScore());
					}
					//通知其他玩家 用户掉线
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"用户无需确认准备");
			}
		}
		return result;
	}
}
