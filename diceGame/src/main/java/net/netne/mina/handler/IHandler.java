package net.netne.mina.handler;

import net.netne.common.pojo.Result;

import org.apache.mina.core.session.IoSession;

/**
 * diceGame
 * @date 2014-8-10 下午9:32:32
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public interface IHandler {
	
	public Result handle(IoSession session,String params);
	
	public Result execute(IoSession session,String params);

}
