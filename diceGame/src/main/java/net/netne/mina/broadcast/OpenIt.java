package net.netne.mina.broadcast;

import java.util.List;

import net.netne.api.service.IMemberService;
import net.netne.common.SpringConstant;
import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EBroadcastCode;
import net.netne.common.enums.GamerStatus;
import net.netne.common.pojo.Member;
import net.netne.mina.pojo.Gambling;
import net.netne.mina.pojo.Gamer;
import net.netne.mina.pojo.broadcast.BroadcastTO;
import net.netne.mina.pojo.broadcast.DiceInfo;
import net.netne.mina.pojo.broadcast.OpenItTO;

import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSON;

/**
 * diceGame
 * 
 * @date 2014-8-12 下午10:32:39
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class OpenIt implements IBroadcastThread {

	private String gamblingId;

	private Gamer loser;
	
	private Gamer winner;
	
	private List<DiceInfo> diceInfoList;

	public OpenIt(String gamblingId, Gamer loser,Gamer winner,List<DiceInfo> diceInfoList) {
		this.gamblingId = gamblingId;
		this.loser = loser;
		this.winner = winner;
		this.diceInfoList = diceInfoList;
	}

	@Override
	public void run() {
		Gambling gambling = GamblingCache.getInstance().get(gamblingId);
		List<Gamer> gamers = GamerCache.getInstance().getGamers(gamblingId);
		if (gamers != null) {
			BroadcastTO broadcastTO = new BroadcastTO(EBroadcastCode.GAME_OVER.getCode(),"游戏结束");
			OpenItTO openItTO = new OpenItTO();
			openItTO.setDiceList(diceInfoList);
			openItTO.setWin(0);
			openItTO.setScore(gambling.getScore());
			broadcastTO.setContent(openItTO);
			for (Gamer mGamer : gamers) {
				IoSession session = mGamer.getSession();
				if(mGamer.getUid().equals(winner.getUid())){
					openItTO.setWin(1);
				}else if(mGamer.getUid().equals(loser.getUid())){
					openItTO.setWin(2);
				}else{
					openItTO.setWin(0);
				}
				broadcastTO.setContent(openItTO);
				mGamer.setGamestatus(GamerStatus.NEW_JOIN.getCode());
				GamerCache.getInstance().addOne(gamblingId, mGamer);
				if (session.isConnected()) {
					session.write(JSON.toJSONString(broadcastTO));
				}
			}
		}
		IMemberService memberService = SpringConstant.getBean("memberServiceImpl");
		Member losemMember = memberService.getMemberById(loser.getId());
		MemberCache.getInstance().updateMember(loser.getUid(), losemMember);
		Member winMember = memberService.getMemberById(winner.getId());
		MemberCache.getInstance().updateMember(winner.getUid(), winMember);
	}

}
