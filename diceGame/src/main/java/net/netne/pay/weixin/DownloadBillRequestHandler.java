package net.netne.pay.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.netne.pay.weixin.util.MD5Util;

public class DownloadBillRequestHandler extends RequestHandler {

	public DownloadBillRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		
	}
	
	/**
	 * ����md5ժҪ,������:������̶�˳���鴮,������ֵ�Ĳ���μ�ǩ��
	 */
	protected void createSign() {
		StringBuffer sb = new StringBuffer();
        sb.append("spid=" + this.getParameter("spid") + "&");
        sb.append("trans_time=" + this.getParameter("trans_time") + "&");
        sb.append("stamp=" + this.getParameter("stamp") + "&");
        sb.append("cft_signtype=" + this.getParameter("cft_signtype") + "&");
        sb.append("mchtype=" + this.getParameter("mchtype") + "&");
		sb.append("key=" + this.getKey());
		
		String enc = "";
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		
		this.setParameter("sign", sign);
		
		//debug��Ϣ
		this.setDebugInfo(sb.toString() + " => sign:" + sign);
		
	}
}