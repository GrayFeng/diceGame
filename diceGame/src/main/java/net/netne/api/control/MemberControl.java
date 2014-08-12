package net.netne.api.control;


import java.util.Map;
import java.util.UUID;

import net.netne.api.service.IMemberService;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;

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
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
	public String reg(String uid,String params){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(uid) 
					&& MemberCache.getInstance().isHave(uid)){
				Member member = JSON.parseObject(params, Member.class);
				if(member != null){
					memberService.addMember(member);
					LoginInfo loginInfo = new LoginInfo();
					loginInfo.setMember(member);
					MemberCache.getInstance().add(uid, loginInfo);
					result = Result.getSuccessResult();
				}
			}
		}catch (Exception e) {
			if(e.getCause() instanceof MySQLIntegrityConstraintViolationException){
				result = new Result(EEchoCode.ERROR.getCode(),"昵称或手机号码重复!");
				log.error(e.getMessage());
			}else{
				log.error(e.getMessage(),e);
			}
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"用户信息不完整，注册失败!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("login")
	@ResponseBody
	public  String login(String uid,String params){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(params)){
				JSONObject paramsObj = JSON.parseObject(params);
				if(StringUtils.isNotEmpty(uid) 
						&& MemberCache.getInstance().isHave(uid)){
					String mobile = paramsObj.getString("mobile");
					String password = paramsObj.getString("password");
					if(StringUtils.isNotEmpty(mobile) 
							&& StringUtils.isNotEmpty(password)){
						Member member = memberService.login(mobile,password);
						if(member != null){
							LoginInfo loginInfo = new LoginInfo();
							loginInfo.setMember(member);
							MemberCache.getInstance().add(uid, loginInfo);
							result = Result.getSuccessResult();
							Map<String,Object> memberInfo = Maps.newHashMap();
							memberInfo.put("mobile", member.getMobile());
							memberInfo.put("sex",member.getSex() == null || member.getSex() == 1 ? "男" : "女");
							memberInfo.put("name", member.getName());
							result.setRe(memberInfo);
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result();
				result.setStatus(EEchoCode.ERROR.getCode());
				result.setMsg("手机号或密码错误!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("start")
	@ResponseBody
	public String start(String uid){
		Result result = Result.getSuccessResult();
		Map<String,String> resultMap = Maps.newHashMap();
		if(StringUtils.isEmpty(uid) 
				|| !MemberCache.getInstance().isHave(uid)){
			uid = "m-"+UUID.randomUUID().toString();
			LoginInfo loginInfo = new LoginInfo();
			MemberCache.getInstance().add(uid, loginInfo);
		}
		resultMap.put("uid", uid);
		result.setRe(resultMap);
		return ResultUtil.getJsonString(result);
	}

}
