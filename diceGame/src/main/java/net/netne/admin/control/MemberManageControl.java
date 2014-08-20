package net.netne.admin.control;

import net.netne.api.service.IMemberService;
import net.netne.api.service.IScoreService;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Page;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private IScoreService scoreService;
	
	@RequestMapping("list")
	public ModelAndView userList(@RequestParam(defaultValue="1") Integer pageNum){
		Page<Member> page = memberService.getMemberList(pageNum);
		ModelAndView mav = new ModelAndView("main");
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("update")
	public ModelAndView userList(@ModelAttribute Member member,Integer score){
		ModelAndView mav = new ModelAndView("redirect:/gm/user/list.do");
		if(member.getId() != null){
			if(StringUtils.isNotEmpty(member.getName())){
				memberService.updateMember(member);
			}
			if(StringUtils.isNotEmpty(member.getPassword())){
				memberService.modifyPassword(member.getId(), member.getPassword());
			}
			if(score != null){
				scoreService.addScore(member.getId(), score);
			}
		}
		return mav;
	}
	
	

}
