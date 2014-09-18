package net.netne.common.enums;

public enum ERechargeStatus {
	
	WAIT_2_PAY{
		public Integer getCode(){
			return 1;
		}
	},
	FAIL{
		public Integer getCode(){
			return 2;
		}
	},
	SUCCESS{
		public Integer getCode(){
			return 3;
		}
	};
	
	abstract public Integer getCode();

}
