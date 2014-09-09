package net.netne.mina.handler;

import net.netne.common.enums.EEchoCode;
import net.netne.mina.pojo.MinaResult;

import org.apache.mina.core.session.IoSession;

public class HeartbeatHandler extends AbstractHandler implements IHandler{
	
	@Override
	public MinaResult execute(IoSession session, String params) {
		MinaResult result = new MinaResult(EEchoCode.SUCCESS.getCode(),"yyl_test_ok");
		return result;
	}
}
