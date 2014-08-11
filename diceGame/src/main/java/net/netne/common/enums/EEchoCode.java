package net.netne.common.enums;

public enum EEchoCode {
	

	SUCCESS{
		public String getCode(){
			return "200";
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
	};
	
	abstract public String getCode();
}
