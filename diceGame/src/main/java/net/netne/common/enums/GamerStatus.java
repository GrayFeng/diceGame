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
	SHOOK{
		public Integer getCode(){
			return 2;
		}
	},
	REPORTED{
		public Integer getCode(){
			return 3;
		}
	},
	QUIT{
		public Integer getCode(){
			return 4;
		}
	};
	
	abstract public Integer getCode();
}
