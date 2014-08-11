package net.netne.mina.pojo.result;
/**
 * diceGame
 * @date 2014-8-10 下午9:29:50
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CommonResult {
	
	private String code;
	
	private String msg;
	
	private IResult content;

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

	public IResult getContent() {
		return content;
	}

	public void setContent(IResult content) {
		this.content = content;
	}
	
}
