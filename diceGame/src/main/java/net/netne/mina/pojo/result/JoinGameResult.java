package net.netne.mina.pojo.result;

import java.util.List;

/**
 * diceGame
 * @date 2014-8-10 下午9:29:04
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class JoinGameResult implements IResult{
	
	private List<GamerVO> gamers;

	public List<GamerVO> getGamers() {
		return gamers;
	}

	public void setGamers(List<GamerVO> gamers) {
		this.gamers = gamers;
	}
	
}
