package net.netne.admin.control;

import javax.servlet.http.HttpServletRequest;

import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * diceGame
 * @date 2014-8-13 下午10:56:14
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/gm")
public class AdminLoginControl {

	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("index");
	}
	
	@RequestMapping("login")
	@ResponseBody
	public String login(String password,String userName,HttpServletRequest request){
		Result result = null;
		if(StringUtils.isNotEmpty(password) 
				&& StringUtils.isNotEmpty(userName)){
			if("admin".equals(userName) && "admin".equals(password)){
				request.getSession().setAttribute("adminName", "admin");
				result = Result.getSuccessResult();
			}
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(),"用户名或密码错误");
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request){
		request.getSession().removeAttribute("adminName");
		return index();
	}
}
