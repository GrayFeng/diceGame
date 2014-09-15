package net.netne.admin.control;

import javax.servlet.http.HttpServletRequest;

import net.netne.api.service.IMemberService;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Result;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * diceGame
 * @date 2014-8-13 下午10:56:14
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/gm")
public class AdminLoginControl {
	
	@Autowired
	private IMemberService memberService;

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
			Member member = memberService.sysLogin(userName);
			if(member != null && password.equals(member.getPassword())){
				request.getSession().setAttribute("adminName", member.getName());
				result = Result.getSuccessResult();
			}
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(),"用户名或密码错误");
		}
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request){
		request.getSession().removeAttribute("adminName");
		return index();
	}
}
