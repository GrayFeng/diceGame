package net.netne.admin.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * diceGame
 * @date 2014-8-13 下午10:59:55
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/gm/user")
public class MemberManageControl {
	
	@RequestMapping("list")
	public ModelAndView userList(){
		return new ModelAndView("main");
	}

}
