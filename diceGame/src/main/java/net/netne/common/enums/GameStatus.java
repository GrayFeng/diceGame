package net.netne.common.enums;
/**
 * diceGame
 * @date 2014-8-12 下午10:11:20
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public enum GameStatus {

	WAIT{
		public Integer getCode(){
			return 0;
		}
	},
	PLAYING{
		public Integer getCode(){
			return 1;
		}
	};
	
	abstract public Integer getCode();
}
