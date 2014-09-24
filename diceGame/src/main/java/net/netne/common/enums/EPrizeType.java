package net.netne.common.enums;

public enum EPrizeType {
	
	VIRTUAL_GOLD{
		public Integer getCode(){
			return 1;
		}
	},
	ELECTRONIC_COUPONS{
		public Integer getCode(){
			return 2;
		}
	},
	PHYSICAL_PRIZE{
		public Integer getCode(){
			return 3;
		}
	};
	
	abstract public Integer getCode();
}
