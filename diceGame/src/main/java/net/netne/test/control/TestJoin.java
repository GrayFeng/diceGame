package net.netne.test.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * diceGame
 * @date 2014-8-10 下午10:09:44
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class TestJoin {
	
	public static void main(String[] args) {
		String params = "{'actionCode':'101','diceNum':3,'dicePoint':4,'gamblingId':'gb-4e234786-afa7-4aa9-b37d-dde6a703df9b','uid':'m-1f99e9e4-6930-4617-b59f-c1ec75567cda'}";
		try{
			Socket client = new Socket(InetAddress.getByName("127.0.0.1"), 8088);
			client.setKeepAlive(true);//开启保持活动状态的套接字
			OutputStream os = client.getOutputStream();
			PrintWriter out = new PrintWriter(os, true);
				out.println(params);
				byte[] buffer = new byte[1024];
				int len = 0;
				InputStream is = client.getInputStream();
				while((len = is.read(buffer)) > -1){
					String msg = new String(buffer,0,len);
					System.out.println(msg);
				}
				out.println(params);
				while((len = is.read(buffer)) > -1){
					String msg = new String(buffer,0,len);
					System.out.println(msg);
				}
 		}catch (Exception e) {
 			e.printStackTrace();
		}
		
	}

}
