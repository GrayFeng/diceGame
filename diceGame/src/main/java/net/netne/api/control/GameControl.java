package net.netne.api.control;

import java.util.List;
import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.pojo.GamblingVO;
import net.netne.common.pojo.Page;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;
import net.netne.mina.pojo.Gambling;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


@Controller
@RequestMapping("/api/game")
public class GameControl {
	
	private Logger log = LoggerFactory.getLogger(GameControl.class);
	
	@RequestMapping("searchRoom")
	@ResponseBody
	public String searchRoom(String uid,String params){
		Result result = Result.getSuccessResult();
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			String roomNo = jsonObject.getString("roomNo");
			if(StringUtils.isNotEmpty(roomNo)){
				Gambling gambling = GamblingCache.getInstance().getByRoomNo(roomNo);
				if(gambling != null){
					GamblingVO gamblingVO = new GamblingVO();
					BeanUtils.copyProperties(gambling, gamblingVO);
					result.setRe(gamblingVO);
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(result.getRe() == null){
				result.setMsg("无此房间信息");
			}
		}
		return ResultUtil.getJsonString(result);
	}
	
	@RequestMapping("roomList")
	@ResponseBody
	public String roomList(String uid,String params){
		Result result = Result.getSuccessResult();
		Map<String,Object> resultMap  = Maps.newHashMap();;
		try{
			Integer pageNum = 1;
			if(StringUtils.isNotEmpty(params)){
				JSONObject jsonObject = JSON.parseObject(params);
				pageNum = jsonObject.getInteger("pageNum") == null 
						? 1 : jsonObject.getInteger("pageNum");
			}
			Page<GamblingVO> page = getPageList(pageNum);
			List<GamblingVO> roomList = page.getContent();
			if(roomList != null && roomList.size() > 0){
				resultMap.put("totalPage", page.getTotalPages());
				resultMap.put("totalCount", page.getTotal());
				resultMap.put("pageSize", roomList.size());
				resultMap.put("pageNum", pageNum);
				resultMap.put("roomList", roomList);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			if(resultMap.isEmpty()){
				resultMap.put("totalPage", 0);
				resultMap.put("totalCount", 0);
				resultMap.put("pageSize", 0);
				resultMap.put("pageNum", 1);
				resultMap.put("roomList", null);
			}
			result.setRe(resultMap);
		}
		return ResultUtil.getJsonString(result);
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
