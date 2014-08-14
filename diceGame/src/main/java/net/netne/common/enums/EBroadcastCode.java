package net.netne.common.enums;

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
	GAMER_READY{
		public String getCode(){
			return "902";
		}
	},
	GAMER_UNREADY{
		public String getCode(){
			return "903";
		}
	},
	GAMER_SHOOK{
		public String getCode(){
			return "904";
		}
	},
	GAMER_START_GUESS{
		public String getCode(){
			return "905";
		}
	},
	GAMER_GUESSED{
		public String getCode(){
			return "906";
		}
	},
	GAMER_OFF_LINE{
		public String getCode(){
			return "910";
		}
	},
	GAMER_QUIT{
		public String getCode(){
			return "920";
		}
	},
	GAME_OVER{
		public String getCode(){
			return "930";
		}
	};
	
	abstract public String getCode();
}
