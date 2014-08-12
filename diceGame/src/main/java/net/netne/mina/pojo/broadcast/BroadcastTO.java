package net.netne.mina.pojo.broadcast;
/**
 * diceGame
 * @date 2014-8-12 下午11:22:32
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class BroadcastTO {
	
	public String code;
	
	public String msg;
	
	public Object content;
	
	public BroadcastTO(){}
	
	public BroadcastTO(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
}
