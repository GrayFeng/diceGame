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
//�Ƹ�֧ͨ��֪ͨ����̨֪ͨ��ʾ�����̻����մ��ĵ����п�������
//---------------------------------------------------------
//����֧��Ӧ�����
ResponseHandler resHandler = new ResponseHandler(request, response);
resHandler.setKey(ConstantUtil.PARTNER_KEY);

//�ж�ǩ��
if(resHandler.isTenpaySign()) {
	
	//֪ͨid
	String notify_id = resHandler.getParameter("notify_id");
	
	//�����������
	RequestHandler queryReq = new RequestHandler(null, null);
	//ͨ�Ŷ���
	TenpayHttpClient httpClient = new TenpayHttpClient();
	//Ӧ�����
	ClientResponseHandler queryRes = new ClientResponseHandler();
	
	//ͨ��֪ͨID��ѯ��ȷ��֪ͨ�����Ƹ�ͨ
	queryReq.init();
	queryReq.setKey(ConstantUtil.PARTNER_KEY);
	queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
	queryReq.setParameter("partner", ConstantUtil.PARTNER);
	queryReq.setParameter("notify_id", notify_id);
	
	//ͨ�Ŷ���
	httpClient.setTimeOut(5);
	//������������
	httpClient.setReqContent(queryReq.getRequestURL());
	//��̨����
	if(httpClient.call()) {
		//���ý������
		queryRes.setContent(httpClient.getResContent());
		queryRes.setKey(ConstantUtil.PARTNER_KEY);
			
		//��ȡ���ز���
		String retcode = queryRes.getParameter("retcode");
		String trade_state = queryRes.getParameter("trade_state");
	
		String trade_mode = queryRes.getParameter("trade_mode");
			
		//�ж�ǩ�������
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
			//����ʱ�����ؽ��δǩ������¼retcode��retmsg��ʧ�����顣
			log.error("��ѯ��֤ǩ��ʧ�ܻ�ҵ�����");
			log.error("retcode:" + queryRes.getParameter("retcode")+
					" retmsg:" + queryRes.getParameter("retmsg"));
		}
	} else {
		log.error("��̨����ͨ��ʧ��");
		log.error(httpClient.getResponseCode() + "");
		log.error(httpClient.getErrInfo());
		//�п�����Ϊ����ԭ�������Ѿ�������δ�յ�Ӧ��
	}
}
else{
	log.error("֪ͨǩ����֤ʧ��");
}

%>

