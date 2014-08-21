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
		String params = "{'actionCode':'130','diceNum':3,'dicePoint':'4-3-5-3-1','gamblingId':'gb-2693514c-b633-442b-b4c6-d759b7e0abc0','uid':'m-50812589-94be-472c-9146-08ae94323d71'}";
		try{
			Socket client = new Socket(InetAddress.getByName("115.29.144.246"), 8088);
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
