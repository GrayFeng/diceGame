package net.netne.api.control;

import java.util.List;

import net.netne.api.service.IPrizeService;
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
		Prize prize = prizeService.getPrizeById(1);
		Result result = Result.getSuccessResult();
		if(prize != null){
			result.setRe(prize);
		}
		return ResultUtil.getJsonString(result);
	}

}
