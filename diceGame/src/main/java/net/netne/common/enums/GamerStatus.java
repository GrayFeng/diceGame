package net.netne.common.enums;
/**
 * diceGame
 * @date 2014-8-12 下午10:15:39
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public enum GamerStatus {

	NEW_JOIN{
		public Integer getCode(){
			return 0;
		}
	},
	READY{
		public Integer getCode(){
			return 1;
		}
	},
	PLAYING{
		public Integer getCode(){
			return 2;
		}
	},
	QUIT{
		public Integer getCode(){
			return 3;
		}
	};
	
	abstract public Integer getCode();
}