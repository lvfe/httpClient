package com.example.demo;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lenovo on 08/04/2019.
 */
public class HttpsClient {
  public String doGet(String url) {
    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
    sslContext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
    URL serverUrl;
    HttpsURLConnection conn;
    InputStream is;
    BufferedReader bufferedReader;
    try {
      serverUrl = new URL(url);
      conn = (HttpsURLConnection) serverUrl.openConnection();
      conn.setSSLSocketFactory(sslContext.getSocketFactory());
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "applicatoin/json");
      conn.setInstanceFollowRedirects(false);
      conn.connect();
      StringBuffer buffer = new StringBuffer();
      is = conn.getInputStream();
      InputStreamReader isr = new InputStreamReader(is, "UTF-8");
      bufferedReader = new BufferedReader(isr);
      String temp = null;
      while((temp = bufferedReader.readLine())!=null) {
        buffer.append(temp);
      }
      String result = buffer.toString();
      return result;
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }



  }
  public static void main(String[] args) throws Exception {
    String data = HttpsClient.goGet();
  }
}
