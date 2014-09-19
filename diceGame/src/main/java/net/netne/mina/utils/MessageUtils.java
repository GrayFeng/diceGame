package net.netne.mina.utils;

import net.netne.common.uitls.AESEncrypter;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageUtils {
	
	private static Logger log = LoggerFactory.getLogger(MessageUtils.class);
	
	public static void sendMsg(IoSession session,String msg){
		if(AESEncrypter.isDecryption){
			msg = AESEncrypter.encrypt(msg);
		}
		log.info("mina-send:" + msg);
		session.write(msg);
	}

}
