package net.netne.test.control;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class Test {
	
	public static void main(String[] args) {
		String params = "{'uid':'m-4e69ca9c-cc5d-4e9d-ac6e-94e937f23d96','actionCode':'100','gamerNum':5,'score':100}";
		try{
			Socket client = new Socket(InetAddress.getByName("www.yedianshaiwang.com"), 8088);
			client.setKeepAlive(true);//开启保持活动状态的套接字
			OutputStream os = client.getOutputStream();
			PrintWriter out = new PrintWriter(os, true);
			out.println(params);
			out.flush();
			InputStream is = client.getInputStream();
			new Thread(new writeThread(is)).start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
			while(true){
				String s = reader.readLine();
				out.println(s);
				out.flush();
			}
 		}catch (Exception e) {
		}
		
	}

}

class writeThread implements Runnable{
	
	private InputStream is;
	
	public writeThread(InputStream is){
		this.is = is;
	}

	@Override
	public void run() {
		try{
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = is.read(buffer)) > -1){
				String msg = new String(buffer,0,len);
				System.out.println(msg);
			}
		}catch(Exception e){
			
		}
		
	}
	
}
