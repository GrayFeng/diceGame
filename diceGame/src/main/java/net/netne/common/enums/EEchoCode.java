package net.netne.common.enums;

public enum EEchoCode {
	

	SUCCESS{
		public String getCode(){
			return "200";
		}
	},
	NOT_LOGIN{
		public String getCode(){
			return "910";
		}
	},
	MISSING{
		public String getCode(){
			return "204";
		}
	},
	ERROR{
		public String getCode(){
			return "205";
		}
	},
	SCORE_NOT_ENOUTH{
		public String getCode(){
			return "206";
		}
	};
	
	abstract public String getCode();
}
