package net.netne.api.control;


import java.sql.SQLIntegrityConstraintViolationException;
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
					if(StringUtils.isEmpty(member.getName())){
						member.setName(null);
					}
					if(member.getSex() == null){
						member.setSex(0);
					}
					memberService.addMember(member);
					LoginInfo loginInfo = new LoginInfo();
					loginInfo.setMember(member);
					MemberCache.getInstance().add(uid, loginInfo);
					result = Result.getSuccessResult();
					Map<String,Object> memberInfo = Maps.newHashMap();
					memberInfo.put("mobile", member.getMobile());
					memberInfo.put("scoreAmount", 500);
					result.setRe(memberInfo);
				}
			}
		}catch (Exception e) {
			if(e.getCause() instanceof SQLIntegrityConstraintViolationException){
				result = new Result(EEchoCode.ERROR.getCode(),"手机号码重复!");
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
							memberInfo.put("sex",member.getSex());
							memberInfo.put("name", member.getName());
							memberInfo.put("photoUrl",member.getPhotoUrl());
							long scoreAmount = 0;
							if(member.getAccount() != null){
								long realScore = member.getAccount().getScoreAmount() 
										- member.getAccount().getFreezeAmount();
								if(realScore > 0){
									scoreAmount = realScore;
								}
							}
							memberInfo.put("scoreAmount", scoreAmount);
							result.setRe(memberInfo);
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"手机号或密码错误!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("updateMember")
	@ResponseBody
	public String updateMember(String uid,String params){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(uid) 
					&& MemberCache.getInstance().isLogin(uid)){
				LoginInfo loginInfo = MemberCache.getInstance().get(uid);
				JSONObject jsonObject = JSON.parseObject(params);
				String name = jsonObject.getString("name");
				Integer sex = jsonObject.getInteger("sex");
				if(StringUtils.isEmpty(name)){
					name = loginInfo.getMember().getName();
				}
				if(sex == null){
					sex = loginInfo.getMember().getSex();
				}
				Member member = new Member();
				member.setId(loginInfo.getMember().getId());
				member.setSex(sex);
				member.setName(name);
				memberService.updateMember(member);
				loginInfo.getMember().setName(name);
				loginInfo.getMember().setSex(sex);
				MemberCache.getInstance().add(uid, loginInfo);
				result = Result.getSuccessResult();
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"用户尚未登录");
			}
		}catch (Exception e) {
			if(e.getCause() instanceof SQLIntegrityConstraintViolationException){
				result = new Result(EEchoCode.ERROR.getCode(),"昵称重复!");
				log.error(e.getMessage());
			}else{
				log.error(e.getMessage(),e);
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("modifyPassword")
	@ResponseBody
	public String modifyPassword(String uid,String params){
		Result result = null;
		try{
			if(StringUtils.isNotEmpty(uid) 
					&& MemberCache.getInstance().isLogin(uid)){
				LoginInfo loginInfo = MemberCache.getInstance().get(uid);
				JSONObject jsonObject = JSON.parseObject(params);
				String oldPassword = jsonObject.getString("oldPassword");
				String newPassword = jsonObject.getString("newPassword");
				if(StringUtils.isNotEmpty(oldPassword) 
						&& StringUtils.isNotEmpty(newPassword)){
					if(oldPassword.equals(loginInfo.getMember().getPassword())){
						memberService.modifyPassword(loginInfo.getMember().getId(), newPassword);
						loginInfo.getMember().setPassword(newPassword);
						MemberCache.getInstance().add(uid, loginInfo);
						result = Result.getSuccessResult();
					}else{
						result = new Result(EEchoCode.ERROR.getCode(),"旧密码不正确");
					}
				}else{
					result = new Result(EEchoCode.ERROR.getCode(),"信息不全无法修改");
				}
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"用户尚未登录");
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
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
