package net.netne.mina.pojo;

import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Result;

public class MinaResult extends Result{
	
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public MinaResult(String status,String msg){
		super(status, msg);
	}
	
	public static MinaResult getSuccessResult(){
		return new MinaResult(EEchoCode.SUCCESS.getCode(),"成功");
	}
	
}
