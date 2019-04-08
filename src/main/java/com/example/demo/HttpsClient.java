package com.example.demo;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by lenovo on 08/04/2019.
 */
public class HttpsClient {
  public static String doGet(String url) {
    SSLContext sslContext;

    URL serverUrl;
    HttpsURLConnection conn;
    InputStream is;
    BufferedReader bufferedReader;
    try {
      sslContext = SSLContext.getInstance("SSL", "SunJSSE");
      sslContext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
      serverUrl = new URL(url);
      conn = (HttpsURLConnection) serverUrl.openConnection();
      conn.setSSLSocketFactory(sslContext.getSocketFactory());
//      conn.setHostnameVerifier(new TrustAny);
      conn.setHostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
          return true;
        }
      });
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "applicatoin/json");
      conn.setInstanceFollowRedirects(false);
      conn.connect();
      StringBuffer buffer = new StringBuffer();
      is = conn.getInputStream();
      InputStreamReader isr = new InputStreamReader(is, "UTF-8");
      bufferedReader = new BufferedReader(isr);
      String temp = null;
      while ((temp = bufferedReader.readLine()) != null) {
        buffer.append(temp);
      }
      String result = buffer.toString();
      return result;
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    }

  return null;
  }


  public static void main(String[] args) throws Exception {
    String data = HttpsClient.doGet("https://baidu.com");
    System.out.println(data);
  }
}
