package net.netne.common.uitls;

import net.netne.common.pojo.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResultUtil {
	
	public static String getJsonString(Result result){
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
	}

}
