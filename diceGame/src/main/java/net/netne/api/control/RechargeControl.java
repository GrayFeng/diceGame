package net.netne.api.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.netne.api.service.IRechargeService;
import net.netne.common.Constant;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.RechargeOrder;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;
import net.netne.pay.weixin.AccessTokenRequestHandler;
import net.netne.pay.weixin.ClientRequestHandler;
import net.netne.pay.weixin.PackageRequestHandler;
import net.netne.pay.weixin.PrepayIdRequestHandler;
import net.netne.pay.weixin.util.ConstantUtil;
import net.netne.pay.weixin.util.WXUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;


@Controller
@RequestMapping("/api/recharge")
public class RechargeControl {
	
	private Logger log = LoggerFactory.getLogger(RechargeControl.class);
	
	@Autowired
	private IRechargeService rechargeService;
	
	
	@ResponseBody
	@RequestMapping("createOrder")
	public String createOrder(String uid,String params
			,HttpServletRequest request,HttpServletResponse response){
		Result result = null;
		Map<String,Object> resultMap = null;
		LoginInfo loginInfo = MemberCache.getInstance().get(uid);
		if(loginInfo != null && loginInfo.getMember() != null 
				&& StringUtils.isNotEmpty(params)){
			JSONObject paramsObj = JSON.parseObject(params);
			String fee = paramsObj.getString("fee");
			if(NumberUtils.isNumber(fee)){
				try{
					RechargeOrder order = rechargeService.createRechargeOrder(loginInfo.getMember(),fee);

					//获取token值 
					String token = AccessTokenRequestHandler.getAccessToken();
					if (StringUtils.isNotEmpty(token)) {
						String noncestr = WXUtil.getNonceStr();
						String timestamp = WXUtil.getTimeStamp();
						String packageValue = createPackageValue(request,response,order.getOrderNo(),fee);
						//吐回给客户端的参数
						String prepayid = createPrePayOrder(request, response,packageValue,uid,token,noncestr,timestamp);
						if (StringUtils.isNotEmpty(prepayid)) {
							resultMap = Maps.newHashMap();
							resultMap.put("prepayid", prepayid);
							resultMap.put("timestamp", timestamp);
							resultMap.put("noncestr", noncestr);
							resultMap.put("sign", getClientSign(request, response,noncestr,prepayid,timestamp));
							result = new Result(EEchoCode.SUCCESS.getCode(), "成功");
							result.setRe(resultMap);
							log.error("生产微信预支付订单成功");
						} else {
							log.error("错误：获取prepayId失败");
						}
					} else {
						log.error("错误：获取不到Token");
					}
				}catch(Exception e){
					log.error(e.getMessage(),e);
				}
			}
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(), "创建充值订单失败!");
		}
		return ResultUtil.getJsonString(result);
	}
	
	private String createPrePayOrder(HttpServletRequest request
			,HttpServletResponse response,String packageValue,String uid,
			String token,String noncestr,String timestamp) throws Exception{
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
		
		////设置获取prepayid支付参数
		prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
		prepayReqHandler.setParameter("appkey", ConstantUtil.APP_KEY);
		prepayReqHandler.setParameter("noncestr", noncestr);
		prepayReqHandler.setParameter("package", packageValue);
		prepayReqHandler.setParameter("timestamp", timestamp);
		prepayReqHandler.setParameter("traceid", uid);

		//生成获取预支付签名
		String sign = prepayReqHandler.createSHA1Sign();
		//增加非参与签名的额外参数
		prepayReqHandler.setParameter("app_signature", sign);
		prepayReqHandler.setParameter("sign_method",
				ConstantUtil.SIGN_METHOD);
		String gateUrl = ConstantUtil.GATEURL + token;
		prepayReqHandler.setGateUrl(gateUrl);

		return  prepayReqHandler.sendPrepay();
	}
	
	
	private String createPackageValue(HttpServletRequest request
			,HttpServletResponse response,String orderNo,String fee) throws Exception{
		PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);//生成package的请求类 
		packageReqHandler.setKey(ConstantUtil.PARTNER_KEY);
		//设置package订单参数
		packageReqHandler.setParameter("bank_type", "WX");//银行渠道
		packageReqHandler.setParameter("body", "夜店骰王-积分充值"); //商品描述   
		packageReqHandler.setParameter("notify_url", ConstantUtil.NOTIFY_URL); //接收财付通通知的URL  
		packageReqHandler.setParameter("partner", ConstantUtil.PARTNER); //商户号    
		packageReqHandler.setParameter("out_trade_no", orderNo); //商家订单号   
		packageReqHandler.setParameter("total_fee", fee); //商品金额,以分为单位  
		packageReqHandler.setParameter("spbill_create_ip",Constant.getClientIp(request)); //订单生成的机器IP，指用户浏览器端IP  
		packageReqHandler.setParameter("fee_type", "1"); //币种，1人民币   66
		packageReqHandler.setParameter("input_charset", "GBK"); //字符编码
		return packageReqHandler.getRequestURL();
	}
	
	private String getClientSign(HttpServletRequest request
			,HttpServletResponse response,String noncestr,String prepayid,String timestamp){
		ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);//返回客户端支付参数的请求类
		//输出参数列表
		clientHandler.setParameter("appid", ConstantUtil.APP_ID);
		clientHandler.setParameter("appkey", ConstantUtil.APP_KEY);
		clientHandler.setParameter("noncestr", noncestr);
		clientHandler.setParameter("package", "Sign=WXPay");
		clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
		clientHandler.setParameter("prepayid", prepayid);
		clientHandler.setParameter("timestamp", timestamp);
		//生成签名
		return clientHandler.createSHA1Sign();
		//clientHandler.setParameter("sign", sign);
	}
}
