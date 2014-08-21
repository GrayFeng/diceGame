package net.netne.admin.control;

import java.util.List;

import net.netne.common.cache.GamblingCache;
import net.netne.common.cache.GamerCache;
import net.netne.common.pojo.GamblingVO;
import net.netne.common.pojo.Page;
import net.netne.mina.pojo.Gambling;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * diceGame
 * @date 2014-8-17 下午11:25:55
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
@Controller
@RequestMapping("/gm/game")
public class GameManageControl {
	
	@RequestMapping("roomList")
	public ModelAndView list(@RequestParam(defaultValue="1")Integer pageNum){
		ModelAndView mav = new ModelAndView("room");
		Page<GamblingVO> page = getPageList(pageNum);
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("clear")
	public ModelAndView clear(){
		ModelAndView mav = new ModelAndView("room");
		GamblingCache.getInstance().removeAll();
		GamerCache.getInstance().removeAll();
		return mav;
	}
	
	private Page<GamblingVO> getPageList(Integer pageNum){
		Page<GamblingVO> page = new Page<GamblingVO>();
		List<Gambling> roomList = GamblingCache.getInstance().getAll();
		if(roomList != null && roomList.size() > 0){
			page.setSize(20);
			page.setTotal(roomList.size());
			if(pageNum != null && pageNum > page.getTotalPages()){
				pageNum =  page.getTotalPages();
			}
			page.setNumber(pageNum);
			List<Gambling> pageList = Lists.newArrayList(roomList.subList(page.getStartNum(), page.getEndNum()));
			if(pageList != null && pageList.size() > 0){
				List<GamblingVO> gamblingVOList = Lists.transform
						(pageList, new Function<Gambling,GamblingVO>(){
					@Override
					public GamblingVO apply(Gambling input) {
						GamblingVO gamblingVO = new GamblingVO();
						BeanUtils.copyProperties(input, gamblingVO);
						return gamblingVO;
					}
				});
				page.setContent(gamblingVOList);
			}
		}
		return page;
	}
}
