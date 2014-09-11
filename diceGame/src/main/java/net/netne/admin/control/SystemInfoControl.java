package net.netne.admin.control;

import java.util.List;

import net.netne.api.service.IVersionService;
import net.netne.common.pojo.VersionInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	public String modifyPassword(String oldPassword,String newPassword){
		return null;
	}

}
