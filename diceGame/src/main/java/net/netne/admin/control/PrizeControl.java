package net.netne.admin.control;

import net.netne.api.service.IPrizeService;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.Prize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * diceGame
 * @date 2014-8-17 下午11:25:55
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/gm/prize")
public class PrizeControl {
	
	@Autowired
	private IPrizeService prizeService;

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(defaultValue="1")Integer pageNum){
		ModelAndView mav = new ModelAndView("prize");
		Page<Prize> page = prizeService.getPrizeList(pageNum);
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("add")
	public ModelAndView list(@ModelAttribute Prize prize){
		ModelAndView mav = new ModelAndView("redirect:/gm/prize/list.do");
		prizeService.addPrize(prize);
		return mav;
	}
	
	@RequestMapping("modify")
	public ModelAndView modify(@ModelAttribute Prize prize){
		ModelAndView mav = new ModelAndView("redirect:/gm/prize/list.do");
		prizeService.modifyPrize(prize);
		return mav;
	}
}
