package net.netne.common.uitls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResultUtil {
	
	public static String getJsonString(Object result){
		return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
	}

}
