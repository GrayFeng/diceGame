package net.netne.common.enums;

public enum EActionCode {
	
	CREATE_GAME{
		public Integer getCode(){
			return 100;
		}
	},
	JOIN_GAME{
		public Integer getCode(){
			return 101;
		}
	},
	QUIT_GAME{
		public Integer getCode(){
			return 102;
		}
	},
	START_GAME{
		public Integer getCode(){
			return 103;
		}
	},
	GAMER_READY{
		public Integer getCode(){
			return 120;
		}
	},
	GAMER_UNREADY{
		public Integer getCode(){
			return 121;
		}
	},
	GAMER_SHOOK{
		public Integer getCode(){
			return 130;
		}
	},
	GAMER_GUESS{
		public Integer getCode(){
			return 131;
		}
	};
	
	abstract public Integer getCode();
}
