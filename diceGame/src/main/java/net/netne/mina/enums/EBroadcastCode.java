package net.netne.mina.enums;

public enum EBroadcastCode {
	

	GAMER_START{
		public String getCode(){
			return "900";
		}
	},
	GAMER_JOININ{
		public String getCode(){
			return "901";
		}
	},
	GAMER_STATUS_CHANGE{
		public String getCode(){
			return "902";
		}
	};
	
	abstract public String getCode();
}
