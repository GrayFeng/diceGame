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
	START{
		public Integer getCode(){
			return 1;
		}
	},
	SHAKE{
		public Integer getCode(){
			return 2;
		}
	},
	GUESS{
		public Integer getCode(){
			return 3;
		}
	},
	OPENING{
		public Integer getCode(){
			return 4;
		}
	},
	OVER{
		public Integer getCode(){
			return 5;
		}
	},
	CLOSE{
		public Integer getCode(){
			return 6;
		}
	};
	
	abstract public Integer getCode();
}
