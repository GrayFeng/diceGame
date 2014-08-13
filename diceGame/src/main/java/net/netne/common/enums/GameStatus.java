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
	REPORT_NO{
		public Integer getCode(){
			return 3;
		}
	},
	OVER{
		public Integer getCode(){
			return 4;
		}
	},
	CLOSE{
		public Integer getCode(){
			return 5;
		}
	};
	
	abstract public Integer getCode();
}
