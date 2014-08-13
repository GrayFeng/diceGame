package net.netne.api.control;

import java.util.List;
import java.util.Map;

import net.netne.common.cache.GamblingCache;
import net.netne.common.pojo.GamblingVO;
import net.netne.common.pojo.Result;
import net.netne.common.uitls.ResultUtil;
import net.netne.mina.pojo.Gambling;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


@Controller
@RequestMapping("/api/game")
public class GameControl {
	
	@RequestMapping("roomList")
	@ResponseBody
	public String roomList(String uid,String params){
		Result result = Result.getSuccessResult();
		Map<String,Object> resultMap = Maps.newHashMap();
		List<Gambling> roomList = GamblingCache.getInstance().getAll();
		if(roomList != null && roomList.size() > 0){
			List<GamblingVO> gamblingVOList = Lists.transform
					(roomList, new Function<Gambling,GamblingVO>(){
				@Override
				public GamblingVO apply(Gambling input) {
					GamblingVO gamblingVO = new GamblingVO();
					BeanUtils.copyProperties(input, gamblingVO);
					return gamblingVO;
				}
			});
			resultMap.put("count", gamblingVOList.size());
			resultMap.put("roomList", gamblingVOList);
		}else{
			resultMap.put("count", 0);
			resultMap.put("roomList", null);
		}
		result.setRe(resultMap);
		return ResultUtil.getJsonString(result);
	}

}
