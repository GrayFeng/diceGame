/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package net.netne.mina;

import java.util.Map;

import net.netne.common.enums.EActionCode;
import net.netne.common.enums.EEchoCode;
import net.netne.common.uitls.ResultUtil;
import net.netne.mina.handler.CreateGamblingHandler;
import net.netne.mina.handler.GuessDiceHandler;
import net.netne.mina.handler.IHandler;
import net.netne.mina.handler.JoinGameHandler;
import net.netne.mina.handler.OpenHandler;
import net.netne.mina.handler.QuitGameHandler;
import net.netne.mina.handler.Ready2GameHandler;
import net.netne.mina.handler.SessionClosedHandler;
import net.netne.mina.handler.ShakeDiceHandler;
import net.netne.mina.pojo.MinaResult;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

public class MessagetHandler extends IoHandlerAdapter {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MessagetHandler.class);
	
	private Map<Integer,IHandler> handlerMap = Maps.newHashMap();
	
	private IHandler sessionClosedHandler = new SessionClosedHandler();

	public MessagetHandler(){
		handlerMap.put(EActionCode.CREATE_GAME.getCode(), new CreateGamblingHandler());
		handlerMap.put(EActionCode.JOIN_GAME.getCode(), new JoinGameHandler());
		handlerMap.put(EActionCode.GAMER_READY.getCode(), new Ready2GameHandler());
		handlerMap.put(EActionCode.GAMER_SHOOK.getCode(), new ShakeDiceHandler());
		handlerMap.put(EActionCode.GAMER_GUESS.getCode(), new GuessDiceHandler());
		handlerMap.put(EActionCode.GAMER_OPEN.getCode(), new OpenHandler());
		handlerMap.put(EActionCode.QUIT_GAME.getCode(), new QuitGameHandler());
	}
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		try {
			String params = String.valueOf(message);
			MinaResult result = execute(session,params);
			session.write(ResultUtil.getJsonString(result));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			session.close(true);
		}
	}
	
	//处理访问请求
	private MinaResult execute(IoSession session,String params){
		MinaResult result = null;
		try {
			JSONObject jsonObject = JSONObject.parseObject(params);
			Integer code = jsonObject.getInteger("actionCode");
			IHandler handler = getActionHandler(code);
			if(handler != null){
				result = handler.handle(session, params);
				if(result != null){
					result.setCode(String.valueOf(code));
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}finally{
			if(result == null){
				result = new MinaResult(EEchoCode.ERROR.getCode(),"request error");
				session.close(false);
			}
		}
		return result;
	}
	
	//获取指令处理器
	private IHandler getActionHandler(Integer code){
		if(code == null || !handlerMap.containsKey(code)){
			return null;
		}
		return handlerMap.get(code);
	}
	
	@Override
	public void sessionCreated(IoSession session) {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		session.setAttribute(SslFilter.USE_NOTIFICATION);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		sessionClosedHandler.execute(session, null);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
//		LOGGER.info("*** IDLE #" + session.getIdleCount(IdleStatus.BOTH_IDLE)
//				+ " ***");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close(true);
	}

}