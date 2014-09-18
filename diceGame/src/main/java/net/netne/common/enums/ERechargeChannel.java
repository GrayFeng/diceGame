package net.netne.common.enums;

public enum ERechargeChannel {
	
	WEIXIN{
		public Integer getCode(){
			return 1;
		}
	},
	ALIPAY{
		public Integer getCode(){
			return 2;
		}
	};
	
	abstract public Integer getCode();

}
