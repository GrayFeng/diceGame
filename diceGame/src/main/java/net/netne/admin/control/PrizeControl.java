package net.netne.admin.control;

import javax.servlet.http.HttpServletRequest;

import net.netne.api.service.IPrizeService;
import net.netne.api.service.IUploadService;
import net.netne.common.enums.EPrizeType;
import net.netne.common.enums.EUploadType;
import net.netne.common.pojo.ImageUploadResult;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.Prize;
import net.netne.common.pojo.PrizeMember;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	@Autowired
	private IUploadService uploadService;

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(defaultValue="1")Integer pageNum){
		ModelAndView mav = new ModelAndView("prize");
		Page<Prize> page = prizeService.getPrizeList(pageNum);
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("add")
	public ModelAndView list(@ModelAttribute Prize prize,MultipartFile photo
			,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("redirect:/gm/prize/list.do");
		if(prize != null 
				&& !EPrizeType.VIRTUAL_GOLD.getCode().equals(prize.getType())){
			prize.setParValue(0);
		}
		ImageUploadResult imageUploadResult = null;
		prizeService.addPrize(prize);
		if(photo != null && photo.getSize() > 0){
			imageUploadResult = uploadService.processupload(prize.getId(),photo,EUploadType.PRIZE_PHOTO);
		}
		if(imageUploadResult == null || imageUploadResult.isSuccess()){
			
		}else if(StringUtils.isNotEmpty(imageUploadResult.getMsg())){
			prizeService.deletePrize(prize.getId());
			request.getSession().setAttribute("errorMsg", imageUploadResult.getMsg());
		}else{
			prizeService.deletePrize(prize.getId());
			request.getSession().setAttribute("errorMsg","系统异常，添加奖品失败!");
		}
		return mav;
	}
	
	@RequestMapping("modify")
	public ModelAndView modify(@ModelAttribute Prize prize,MultipartFile photo,HttpServletRequest request){
		ModelAndView mav = new ModelAndView("redirect:/gm/prize/list.do");
		prizeService.modifyPrize(prize);
		ImageUploadResult imageUploadResult = null;
		if(photo != null && photo.getSize() > 0){
			imageUploadResult = uploadService.processupload(prize.getId(),photo,EUploadType.PRIZE_PHOTO);
		}
		if(imageUploadResult == null || imageUploadResult.isSuccess()){
			
		}else if(StringUtils.isNotEmpty(imageUploadResult.getMsg())){
			request.getSession().setAttribute("errorMsg", imageUploadResult.getMsg());
		}else{
			request.getSession().setAttribute("errorMsg","系统异常，修改奖品失败!");
		}
		return mav;
	}
	
	@RequestMapping("prizeMemberList")
	public ModelAndView prizeMemberList(@RequestParam(defaultValue="1") Integer pageNum){
		Page<PrizeMember> page = prizeService.getPrizeMemberList(pageNum);
		ModelAndView mav = new ModelAndView("prizeMember");
		mav.addObject("page", page);
		return mav;
	}
}
