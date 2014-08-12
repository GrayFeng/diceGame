package net.netne.mina.handler;

import net.netne.common.cache.MemberCache;
import net.netne.common.enums.EEchoCode;
import net.netne.common.pojo.Result;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.session.IoSession;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractHandler implements IHandler{
	
	public Result handle(IoSession session,String params){
		Result result = null;
		JSONObject jsonObject = JSONObject.parseObject(params);
		String uid = jsonObject.getString("uid");
//		if(StringUtils.isEmpty(uid)){
//			result = new Result(EEchoCode.MISSING.getCode(), "未授权访问，缺少UID信息!");
//			session.close(false);
//		}else if(!MemberCache.getInstance().isLogin(uid)){
//			result = new Result(EEchoCode.NOT_LOGIN.getCode(), "用户未登录");
//			session.close(false);
//		}else{
			result = execute(session, params);
//		}
		return result;
	}

}
