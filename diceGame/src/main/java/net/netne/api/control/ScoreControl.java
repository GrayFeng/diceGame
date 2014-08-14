package net.netne.api.control;

import net.netne.api.service.IScoreService;
import net.netne.common.pojo.Account;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/score")
public class ScoreControl {
	
	@Autowired
	private IScoreService scoreService;
	
	@RequestMapping("recharge")
	@ResponseBody
	public String recharge(String mobile){
		Result result = Result.getSuccessResult();
		Account account = scoreService.addScore(mobile, 600);
		result.setRe(account);
		return ResultUtil.getJsonString(result);
	}

}
