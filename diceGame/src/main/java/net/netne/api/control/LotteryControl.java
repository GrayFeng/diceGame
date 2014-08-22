package net.netne.api.control;

import java.util.List;

import net.netne.api.service.IPrizeService;
import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.LoginInfo;
import net.netne.common.pojo.Prize;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * diceGame
 * @date 2014-8-18 上午12:15:56
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/api/prize")
public class LotteryControl {
	
	@Autowired
	private IPrizeService prizeService;
	
	@RequestMapping("list")
	@ResponseBody
	public String list(String uid){
		List<Prize> prizeList = prizeService.getAllPrizeList();
		Result result = Result.getSuccessResult();
		if(prizeList != null){
			result.setRe(prizeList);
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("lottery")
	@ResponseBody
	public String lottery(String uid){
		Result result = null;
		LoginInfo loginInfo = MemberCache.getInstance().get(uid);
		if(loginInfo != null && loginInfo.getMember() != null){
			Prize prize = prizeService.lottery(loginInfo.getMember());
			if(prize != null){
				result = Result.getSuccessResult();
				result.setRe(prize);
			}
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(), "很遗憾没能中奖");
		}
		return ResultUtil.getJsonString(result);
	}

}
