package com.example.demo;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by lenovo on 08/04/2019.
 */
public class MyX509TrustManager implements X509TrustManager{
  X509TrustManager sunJSSEX509TrustManager;
  MyX509TrustManager() {
    try {



      KeyStore ks = KeyStore.getInstance("JKS");
      ks.load(new FileInputStream("cancert.keystore"), "changeit".toCharArray());
      TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
      tmf.init(ks);
      TrustManager tms[] = tmf.getTrustManagers();
      for (int i = 0; i < tms.length; i++) {
        if (tms[i] instanceof X509TrustManager) {
          sunJSSEX509TrustManager = (X509TrustManager) tms[i];
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (KeyStoreException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (CertificateException e) {
      e.printStackTrace();
    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void checkClientTrusted(X509Certificate certificates[],String authType) throws CertificateException {
    sunJSSEX509TrustManager.checkClientTrusted(certificates, authType);

  }

  @Override
  public void checkServerTrusted(X509Certificate[] ax509certificate,String s) throws CertificateException {
    sunJSSEX509TrustManager.checkServerTrusted(ax509certificate, s);
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    // TODO Auto-generated method stub
    return sunJSSEX509TrustManager.getAcceptedIssuers();
  }

}
