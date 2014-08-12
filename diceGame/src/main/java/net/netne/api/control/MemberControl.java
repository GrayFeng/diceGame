package net.netne.api.control;


import java.util.Map;
import java.util.UUID;

import net.netne.api.Result;
import net.netne.api.service.IMemberService;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Member;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
/**
 * diceGame
 * @date 2014-8-11 下午9:55:06
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/api")
public class MemberControl {
	
	private Logger log = LoggerFactory.getLogger(MemberControl.class);
	
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping("reg")
	@ResponseBody
	public String reg(String params){
		Result result = null;
		try{
			JSONObject paramsObj = JSON.parseObject(params);
			String uid = paramsObj.getString("uid");
			if(StringUtils.isNotEmpty(uid) 
					&& MemberCache.getInstance().isHave(uid)){
				Member member = JSON.parseObject(paramsObj.getString("memberInfo"), Member.class);
				if(member != null){
					memberService.addMember(member);
					MemberCache.getInstance().add(uid, member);
					result = new Result();
					result.setCode(EEchoCode.SUCCESS.getCode());
					result.setMsg("注册成功!");
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result();
				result.setCode(EEchoCode.ERROR.getCode());
				result.setCode("用户信息不完整，注册失败!");
			}
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("login")
	@ResponseBody
	public String login(String params){
		Result result = null;
		try{
			JSONObject paramsObj = JSON.parseObject(params);
			String uid = paramsObj.getString("uid");
			if(StringUtils.isNotEmpty(uid) 
					&& MemberCache.getInstance().isHave(uid)){
				String mobile = paramsObj.getString("mobile");
				String password = paramsObj.getString("password");
				if(StringUtils.isNotEmpty(mobile) 
						&& StringUtils.isNotEmpty(password)){
					Member member = memberService.login(mobile,password);
					if(member != null){
						System.out.println(JSON.toJSONString(member));
						MemberCache.getInstance().add(uid, member);
						result = new Result();
						result.setCode(EEchoCode.SUCCESS.getCode());
						result.setMsg("登录成功!");
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result();
				result.setCode(EEchoCode.ERROR.getCode());
				result.setCode("手机号或密码错误!");
			}
		}
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("start")
	@ResponseBody
	public String start(){
		Map<String,String> resultMap = Maps.newHashMap();
		String uid = "m-"+UUID.randomUUID().toString();
		MemberCache.getInstance().add(uid, null);
		resultMap.put("code", EEchoCode.SUCCESS.getCode());
		resultMap.put("uid", uid);
		return JSON.toJSONString(resultMap);
	}

}
