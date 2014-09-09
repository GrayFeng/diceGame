package net.netne.api.control;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.netne.api.service.IMemberService;
import net.netne.api.service.IPrizeService;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.Prize;
import net.netne.common.pojo.PrizePhoto;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * diceGame
 * @date 2014-8-18 上午12:15:56
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/api/prize")
public class LotteryControl {
	
	private Logger logger = LoggerFactory.getLogger(LotteryControl.class);
	
	@Autowired
	private IPrizeService prizeService;
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping("list")
	@ResponseBody
	public String list(String uid){
		List<Prize> prizeList = prizeService.getAllPrizeList();
		Result result = Result.getSuccessResult();
		if(prizeList != null){
			List<Map<String,Object>> prizeInfoList = Lists.newArrayList();
			Map<String,Object> infoMap = Maps.newHashMap();
			int i = 0;
			for(Prize prize : prizeList){
				if(i > 4){
					break;
				}
				i++;
				Map<String,Object> prizeInfoMap = Maps.newHashMap();
				prizeInfoMap.put("name", prize.getName());
				prizeInfoMap.put("id", prize.getId());
				prizeInfoMap.put("photo", prize.getPhotoUrl());
				prizeInfoList.add(prizeInfoMap);
			}
			infoMap.put("prizeList", prizeInfoList);
			infoMap.put("score", 200);
			result.setRe(infoMap);
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("lottery")
	@ResponseBody
	public String lottery(String uid){
		Result result = null;
		LoginInfo loginInfo = MemberCache.getInstance().get(uid);
		if(loginInfo != null && loginInfo.getMember() != null){
			if(memberService.checkScore(loginInfo.getMember().getId(), 200)){
				Prize prize = prizeService.lottery(loginInfo.getMember());
				result = Result.getSuccessResult();
				Map<String,Object> infoMap = Maps.newHashMap();
				if(prize != null){
					infoMap.put("isorder", 1);
					infoMap.put("name", prize.getName());
					infoMap.put("receiveKey", prize.getReceiveKey());
					infoMap.put("id", prize.getId());
					infoMap.put("photo", prize.getPhotoUrl());
				}else{
					result.setMsg("很抱歉没有中奖");
					infoMap.put("isorder", 2);
				}
				result.setRe(infoMap);
			}else{
				result = new Result(EEchoCode.ERROR.getCode(), "积分不足，无法参与抽奖");
			}
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(), "用户认证失败，无法参与抽奖");
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("updateReceivePrizeInfo")
	@ResponseBody
	public String updateReceivePrizeInfo(String uid,String receiveKey,String mobile,String address){
		Result result = null;
		LoginInfo loginInfo = MemberCache.getInstance().get(uid);
		if(loginInfo != null && loginInfo.getMember() != null 
				&& StringUtils.isNotEmpty(receiveKey)
				&& StringUtils.isNotEmpty(address)){
			if(StringUtils.isEmpty(mobile)){
				mobile = loginInfo.getMember().getMobile();
			}
			Map<String,Object> infoMap = Maps.newHashMap();
			infoMap.put("memberMobile", mobile);
			infoMap.put("memberAddress",address);
			infoMap.put("memberId",loginInfo.getMember().getId());
			infoMap.put("key", receiveKey);
			prizeService.updateMemberInfo4Prize(infoMap);
			result = new Result(EEchoCode.SUCCESS.getCode(), "更新成功");
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(), "信息不全，更新失败!");
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping(value = "/img", method = RequestMethod.GET)
	public void getImg(Integer key,HttpServletRequest request, HttpServletResponse response) {
		OutputStream outputStream = null;
		try{
			if(key != null){
				PrizePhoto prizePhoto = prizeService.getPrizePhoto(key);
				if(prizePhoto != null){
					outputStream = response.getOutputStream();
					outputStream.write(prizePhoto.getPhoto());
					outputStream.flush();
					response.flushBuffer();
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally {
			IOUtils.closeQuietly(outputStream);
		}
	}

}
