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
		String params = "{'actionCode':'131','diceNum':2,'dicePoint':2,'gamblingId':'gb-fbb50c23-320f-436d-b8a7-ddf2e6a595e4','uid':'m-d9b8a67b-5b40-43ff-b1ba-c34fd64fcd31'}";
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
