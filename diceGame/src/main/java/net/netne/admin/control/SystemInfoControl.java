package net.netne.admin.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.netne.api.service.IMemberService;
import net.netne.api.service.IVersionService;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Member;
import net.netne.common.pojo.Result;
import net.netne.common.pojo.VersionInfo;

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
 * @date 2014-9-11 下午10:04:30
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/gm/sys")
public class SystemInfoControl {
	
	@Autowired
	private IVersionService versionService;
	
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping("index")
	public ModelAndView systemInfo(){
		ModelAndView mav = new ModelAndView("systemInfo");
		List<VersionInfo> versionList = versionService.getVersionInfoList();
		mav.addObject("versionList", versionList);
		return mav;
	}
	
	@RequestMapping("addVersionInfo")
	public ModelAndView addVersionInfo(VersionInfo versionInfo){
		ModelAndView mav = new ModelAndView("systemInfo");
		versionService.addVersionInfo(versionInfo);
		List<VersionInfo> versionList = versionService.getVersionInfoList();
		mav.addObject("versionList", versionList);
		return mav;
	}
	
	@RequestMapping("modifyPassword")
	@ResponseBody
	public String modifyPassword(HttpServletRequest request,String oldPassword,String newPassword){
		Result result = null;
		String adminName = (String)request.getSession().getAttribute("adminName");
		if(StringUtils.isNotEmpty(adminName) 
				&& StringUtils.isNotEmpty(oldPassword) 
				&& StringUtils.isNotEmpty(newPassword)){
			Member adminMember = memberService.sysLogin(adminName);
			if(adminMember != null 
					&& oldPassword.equals(adminMember.getPassword())){
				adminMember.setPassword(newPassword);
				memberService.modifyAdminPwd(adminMember);
				result = Result.getSuccessResult();
			}else{
				result = new Result(EEchoCode.ERROR.getCode(), "原密码错误，修改失败");
			}
		}
		if(result == null){
			result = new Result(EEchoCode.ERROR.getCode(), "信息不全，修改失败");
		}
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
	}

}
