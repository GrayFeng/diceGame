package net.netne.api.control;

import java.util.Map;

import net.netne.api.service.IRechargeService;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.RechargeOrder;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;

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
	
	@Autowired
	private IRechargeService rechargeService;
	
	
	@ResponseBody
	@RequestMapping("createOrder")
	public String createOrder(String uid,String params){
		Result result = null;
		LoginInfo loginInfo = MemberCache.getInstance().get(uid);
		if(loginInfo != null && loginInfo.getMember() != null 
				&& StringUtils.isNotEmpty(params)){
			JSONObject paramsObj = JSON.parseObject(params);
			String fee = paramsObj.getString("fee");
			if(NumberUtils.isNumber(fee)){
				RechargeOrder order = rechargeService.createRechargeOrder(loginInfo.getMember(), Integer.valueOf(fee));
				Map<String,Object> resultMap = Maps.newHashMap();
				resultMap.put("orderNo", order.getOrderNo());
				result = new Result(EEchoCode.SUCCESS.getCode(), "成功");
				result.setRe(resultMap);
			}
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(), "缺少参数，创建充值订单失败!");
		}
		return ResultUtil.getJsonString(result);
	}
	
	@ResponseBody
	@RequestMapping("test")
	public String test(){
		rechargeService.paymentCallback("1411023189017", "100", 1, 3);
		return "ok";
	}

}
