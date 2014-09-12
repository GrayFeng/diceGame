package net.netne.common.uitls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResultUtil {
	
	public static String getJsonString(Object result){
		String jsonStr = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
		if(AESEncrypter.isDecryption){
			jsonStr = new AESEncrypter().encrypt(jsonStr);
		}
		return jsonStr;
	}
}
