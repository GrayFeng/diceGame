package net.netne.test.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * diceGame
 * 
 * @date 2014-8-15 下午8:34:37
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class TestUpload {

	public static void main(String[] args) {
			String url1 = "http://localhost:8011/diceGame/api/upload.do?uid=m-a9fa8f92-0663-4040-8f33-b5a1e74ee533";
			String fileurl = "c://Koala.jpg";
		String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(url1);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            File uploadfile = new File(fileurl);
            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);

            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + uploadfile.getName() + "\"" + end);
            dos.writeBytes(end);

            // 将要上传的内容写入流中

            InputStream srcis = new FileInputStream(uploadfile);
            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            // 读取文件
            while ((count = srcis.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
            }
            srcis.close();

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            // 上传返回值
            String sl;
            String result="";
            while((sl = br.readLine()) != null)
            result = result+sl; 
            br.close(); 
            is.close(); 
            System.out.println(result);
            //dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}

}
