<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="net.netne.pay.weixin.RequestHandler" %>
<%@ page import="net.netne.pay.weixin.ResponseHandler" %>   
<%@ page import="net.netne.pay.weixin.client.ClientResponseHandler" %>    
<%@ page import="net.netne.pay.weixin.client.TenpayHttpClient" %>
<%@ page import="net.netne.common.SpringConstant" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="net.netne.api.service.impl.RechargeServiceImpl" %>
<%@ page import="net.netne.pay.weixin.util.ConstantUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%

Logger log = LoggerFactory.getLogger("weixin_callback");
//---------------------------------------------------------
//财付通支付通知（后台通知）示例，商户按照此文档进行开发即可
//---------------------------------------------------------
//创建支付应答对象
ResponseHandler resHandler = new ResponseHandler(request, response);
resHandler.setKey(ConstantUtil.PARTNER_KEY);

//判断签名
if(resHandler.isTenpaySign()) {
	
	//通知id
	String notify_id = resHandler.getParameter("notify_id");
	
	//创建请求对象
	RequestHandler queryReq = new RequestHandler(null, null);
	//通信对象
	TenpayHttpClient httpClient = new TenpayHttpClient();
	//应答对象
	ClientResponseHandler queryRes = new ClientResponseHandler();
	
	//通过通知ID查询，确保通知来至财付通
	queryReq.init();
	queryReq.setKey(ConstantUtil.PARTNER_KEY);
	queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
	queryReq.setParameter("partner", ConstantUtil.PARTNER);
	queryReq.setParameter("notify_id", notify_id);
	
	//通信对象
	httpClient.setTimeOut(5);
	//设置请求内容
	httpClient.setReqContent(queryReq.getRequestURL());
	//后台调用
	if(httpClient.call()) {
		//设置结果参数
		queryRes.setContent(httpClient.getResContent());
		queryRes.setKey(ConstantUtil.PARTNER_KEY);
			
		//获取返回参数
		String retcode = queryRes.getParameter("retcode");
		String trade_state = queryRes.getParameter("trade_state");
	
		String trade_mode = queryRes.getParameter("trade_mode");
			
		//判断签名及结果
		if(queryRes.isTenpaySign() 
				&& "0".equals(retcode) 
				&& "0".equals(trade_state) 
				&& "1".equals(trade_mode)) {
			String orderNo = queryRes.getParameter("out_trade_no");
			String fee = queryRes.getParameter("total_fee");
			RechargeServiceImpl rechargeServiceImpl = (RechargeServiceImpl)SpringConstant.getBean("rechargeServiceImpl");
			rechargeServiceImpl.paymentCallback(orderNo, fee, 1, 3);
			resHandler.sendToCFT("Success");
		}
		else{
			//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
			log.error("查询验证签名失败或业务错误");
			log.error("retcode:" + queryRes.getParameter("retcode")+
					" retmsg:" + queryRes.getParameter("retmsg"));
		}
	} else {
		log.error("后台调用通信失败");
		log.error(httpClient.getResponseCode() + "");
		log.error(httpClient.getErrInfo());
		//有可能因为网络原因，请求已经处理，但未收到应答。
	}
}
else{
	log.error("通知签名验证失败");
}

%>

