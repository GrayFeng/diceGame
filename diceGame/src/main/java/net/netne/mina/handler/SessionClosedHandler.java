package net.netne.mina.handler;

import java.util.List;
import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.GamerStatus;
import net.netne.common.pojo.LoginInfo;
import net.netne.mina.broadcast.BroadcastThreadPool;
import net.netne.mina.broadcast.checkGameCanBegin4Quit;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.MinaResult;
import net.netne.mina.pojo.broadcast.BroadcastTO;
import net.netne.mina.utils.MessageUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
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
				if(gambling != null && loginInfo != null){
					Gamer offlineGamer = GamerCache.getInstance().getOne(gid, uid);
					if(offlineGamer != null){
						gambling.setGamerNum(gambling.getGamerNum() - 1);
						GamerCache.getInstance().removeOne(gid,offlineGamer.getUid());
						if(gambling.getGamerNum() == 0){
							GamblingCache.getInstance().remove(gambling.getId());
							if(offlineGamer.getUid().equals(gambling.getCurrentGuessGamerId())
									&& (offlineGamer.getGamestatus() == GamerStatus.SHOOK.getCode() 
										|| offlineGamer.getGamestatus() == GamerStatus.GUESSED.getCode())){
								// 如果掉线用户是当前竞猜用户则通知其他玩家
							}
						}else{
							GamblingCache.getInstance().add(gambling);
							List<Gamer> gamers = GamerCache.getInstance().getGamers(gid);
							//检测当前掉线人数
							for(Gamer gamer :gamers){
								//设置掉线用户状态
								if(gamer.getGamestatus() != GamerStatus.OFF_LINE.getCode() 
										&& gamer.getSession().isConnected()){
									//通知其他玩家当前用户掉线
									BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_OFF_LINE.getCode(),"玩家掉线");
									Map<String,Object> resultMap = Maps.newHashMap();
									resultMap.put("id",offlineGamer.getId());
									broadcastTO.setContent(resultMap);
									MessageUtils.sendMsg(gamer.getSession(),JSON.toJSONString(broadcastTO));
								}
							}
							BroadcastThreadPool.execute(new checkGameCanBegin4Quit(gambling.getId()));
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
