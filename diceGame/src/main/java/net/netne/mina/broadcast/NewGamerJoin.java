package net.netne.mina.broadcast;

import java.util.List;

import net.netne.common.cache.GamerCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.broadcast.BroadcastTO;
import net.netne.mina.pojo.broadcast.NewGamerJoinTO;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;

/**
 * diceGame
 * 
 * @date 2014-8-12 下午10:32:39
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class NewGamerJoin implements IBroadcastThread {

	private String gamblingId;

	private Gamer gamer;

	public NewGamerJoin(String gamblingId, Gamer gamer) {
		this.gamblingId = gamblingId;
		this.gamer = gamer;
	}

	@Override
	public void run() {
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gamblingId);
		if (gamers != null) {
			BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAMER_JOININ.getCode(),"新玩家加入");
			NewGamerJoinTO newGamerJoin = new NewGamerJoinTO();
			newGamerJoin.setNewGamerName(gamer.getName());
			newGamerJoin.setId(gamer.getId());
			newGamerJoin.setSex(gamer.getSex());
			broadcastTO.setContent(newGamerJoin);
			for (Gamer mGamer : gamers) {
				IoSession session = mGamer.getSession();
				if (session.isConnected() 
						&& !mGamer.getUid().equals(gamer.getUid())) {
					session.write(JSON.toJSONString(broadcastTO));
				}
			}
		}
	}

}
