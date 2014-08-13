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
		String params = "{'actionCode':'130','dicePoint':'1-3-1-4-2','gamblingId':'gb-f185924b-570e-48a6-8c2f-9e0a5ff9ec87','uid':'m-90e17404-bc8a-4231-995c-c4a74fdc9d2b'}";
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
