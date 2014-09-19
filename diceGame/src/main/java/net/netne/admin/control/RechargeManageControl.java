package net.netne.admin.control;

import net.netne.api.service.IRechargeService;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.RechargeOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/gm/recharge")
public class RechargeManageControl {
	
	@Autowired
	private IRechargeService rechargeService;
	
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(defaultValue="1") Integer pageNum){
		ModelAndView mav = new ModelAndView("rechargeList");
		Page<RechargeOrder> page = rechargeService.getOrderList(pageNum);
		mav.addObject("page", page);
		return mav;
	}

}
