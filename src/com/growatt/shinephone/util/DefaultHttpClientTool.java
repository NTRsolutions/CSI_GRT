package com.growatt.shinephone.util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;




public class DefaultHttpClientTool 
{
	
	private static HttpClient instance = null;
	  protected DefaultHttpClientTool() {

	  }
	  @SuppressWarnings("deprecation")
	public static HttpClient getInstance() {
	    if(instance == null) {
	    	synchronized (DefaultHttpClientTool.class) {
	    		if(instance==null){
	               instance =new DefaultHttpClient();
	    		}
	      }
	    }
	    return instance;
	 }
	  
//	  public static CloseableHttpClient getInstance(){
//
//		  try {
//
//		               @SuppressWarnings("deprecation")
//					SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//
//		                   //信任所有
//
//		                   public boolean isTrusted(X509Certificate[] chain,
//
//		                                   String authType) throws CertificateException {
//
//		                       return true;
//
//		                   }
//
//		               }).build();
//
//		               SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
//
//		               return HttpClients.custom().setSSLSocketFactory(sslsf).build();
//
//		           } catch (KeyManagementException e) {
//
//		               e.printStackTrace();
//
//		           } catch (NoSuchAlgorithmException e) {
//
//		               e.printStackTrace();
//
//		           } catch (KeyStoreException e) {
//
//		               e.printStackTrace();
//
//		           }
//
//		           return  HttpClients.createDefault();
//
//		  }

}
