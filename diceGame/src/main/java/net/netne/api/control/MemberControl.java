package net.netne.api.control;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.netne.api.service.IMemberService;
import net.netne.api.service.IVersionService;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Result;
import net.netne.common.pojo.VersionInfo;
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
	@Autowired
	private IVersionService versionService;
	
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
	public  String login(String uid,String params,HttpServletRequest request){
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
						Member member = memberService.login(mobile,password,getClientIp(request));
						if(member != null){
							LoginInfo loginInfo = new LoginInfo();
							loginInfo.setMember(member);
							MemberCache.getInstance().add(uid, loginInfo);
							result = Result.getSuccessResult();
							Map<String,Object> memberInfo = getMemberMap(member);
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
					&& MemberCache.getInstance().isLogin(uid)
					&& StringUtils.isNotEmpty(params)){
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
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常!");
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
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("getMemberInfo")
	@ResponseBody
	public String modifyPassword(String uid){
		Result result = null;
		try{
			LoginInfo loginInfo = MemberCache.getInstance().get(uid);
			if(loginInfo != null && loginInfo.getMember() != null){
				Member member = memberService.getMemberById(loginInfo.getMember().getId());
				if(member != null){
					Map<String,Object> memberInfo = getMemberMap(member);
					result = Result.getSuccessResult();
					result.setRe(memberInfo);
				}
			}else{
				result = new Result(EEchoCode.ERROR.getCode(),"用户登录超时!");
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new Result(EEchoCode.ERROR.getCode(),"系统异常!");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	private Map<String,Object> getMemberMap(Member member){
		Map<String,Object> memberInfo = Maps.newHashMap();
		if(member != null){
			memberInfo.put("mobile", member.getMobile());
			memberInfo.put("id", member.getId());
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
		}
		return memberInfo;
	}
	
	
	@RequestMapping("start")
	@ResponseBody
	public String start(String uid,HttpServletRequest request){
		Result result = Result.getSuccessResult();
		Map<String,Object> resultMap = Maps.newHashMap();
		boolean isFirstLogin = false;
		if(StringUtils.isEmpty(uid) 
				|| !MemberCache.getInstance().isHave(uid)){
			uid = "m-"+UUID.randomUUID().toString();
			LoginInfo loginInfo = new LoginInfo();
			MemberCache.getInstance().add(uid, loginInfo);
		}else{
			LoginInfo loginInfo = MemberCache.getInstance().get(uid);
			if(loginInfo != null && loginInfo.getMember() != null){
				isFirstLogin = memberService.checkMemberIsFirstLogin(loginInfo.getMember(), getClientIp(request));
			}
		}
		resultMap.put("isFirstLogin", isFirstLogin ? 1 : 0);
		resultMap.put("firstLoginScore", isFirstLogin ? 400 : 0);
		resultMap.put("uid", uid);
		resultMap.put("sysTime", System.currentTimeMillis());
		result.setRe(resultMap);
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("checkVersion")
	@ResponseBody
	public String checkVersion(String uid,String params){
		Result result = null;
		Map<String,Object> resultMap = Maps.newHashMap();
		if(StringUtils.isNotEmpty(params)){
			JSONObject jsonObject = JSON.parseObject(params);
			String cid = jsonObject.getString("cid");
			VersionInfo versionInfo = versionService.checkVersion(cid);
			if(versionInfo != null && versionInfo.isUpgrade()){
				result = Result.getSuccessResult();
				resultMap.put("address", versionInfo.getAddress());
				resultMap.put("ver",versionInfo.getVersion_name());
				resultMap.put("msg", versionInfo.getMsg());
				resultMap.put("upgrade",1);
				result.setRe(resultMap);
			}
		}
		if(result == null){
			result = Result.getSuccessResult();
			resultMap.put("address",null);
			resultMap.put("ver",null);
			resultMap.put("msg","无可更新版本");
			resultMap.put("upgrade",0);
			result.setRe(resultMap);
			
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("share")
	@ResponseBody
	public String share(String uid,String params){
		Result result = null;
		Map<String,Object> resultMap = Maps.newHashMap();
		if(StringUtils.isNotEmpty(params)){
			JSONObject jsonObject = JSON.parseObject(params);
			String cid = jsonObject.getString("cid");
			VersionInfo versionInfo = versionService.checkVersion(cid);
			result = Result.getSuccessResult();
			resultMap.put("picUrl","http://www.yedianshaiwang.com");
			resultMap.put("msg", "最近在玩一个叫夜店骰王的游戏，超级好玩！还可以抽取大奖哦！");
			if(versionInfo == null){
				resultMap.put("url", "http://www.yedianshaiwang.com");
			}else{
				resultMap.put("url", versionInfo.getAddress());
			}
			result.setRe(resultMap); 
		}
		return ResultUtil.getJsonString(result);
	}
	
	private String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
