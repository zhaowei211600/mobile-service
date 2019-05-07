package com.third.mobile.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 大象能力平台HttpClient工具类
 */
public class HttpClientUtil {    

	private static PoolingHttpClientConnectionManager connMgr;  
	private static RequestConfig requestConfig;  
	private static final int MAX_TIMEOUT = 7000;  

	static {  
		// 设置连接池  
		connMgr = new PoolingHttpClientConnectionManager();  
		// 设置连接池大小  
		connMgr.setMaxTotal(100);  
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());  

		RequestConfig.Builder configBuilder = RequestConfig.custom();  
		// 设置连接超时  
		configBuilder.setConnectTimeout(MAX_TIMEOUT);  
		// 设置读取超时  
		configBuilder.setSocketTimeout(MAX_TIMEOUT);  
		// 设置从连接池获取连接实例的超时  
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);  
		// 在提交请求之前 测试连接是否可用  
		configBuilder.setStaleConnectionCheckEnabled(true);  
		requestConfig = configBuilder.build();  
	}

	/**
	 * POST请求
	 */
	public static String doPost(String url, Map<String, String> param) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (null != param) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						paramList, "UTF-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 * GET请求
	 */
	public static String doGet(String url, Map<String, String> param) {
		// 创建HttpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建URI
			URIBuilder builder = new URIBuilder(url);
			if (null != param) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			// 执行请求
			response = httpClient.execute(httpGet);
			// 判断返回状态是否为200
			if (200 == response.getStatusLine().getStatusCode()) {
				resultString = EntityUtils.toString(response.getEntity(),
				"UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	/**
	 * https Post请求
	 */
	public static String doPostSSL(String url,Map<String,String> map,String charset){  
		HttpClient httpClient = null;  
		HttpPost httpPost = null;  
		String result = null;  
		try{  
			httpClient = new SSLClient();  
			httpPost = new HttpPost(url);  
			//设置参数  
			List<NameValuePair> list = new ArrayList<NameValuePair>();  
			Iterator iterator = map.entrySet().iterator();  
			while(iterator.hasNext()){  
				Entry<String,String> elem = (Entry<String, String>) iterator.next();  
				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
			}  
			if(list.size() > 0){  
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
				httpPost.setEntity(entity);  
			}  
			HttpResponse response = httpClient.execute(httpPost);  
			if(response != null){  
				HttpEntity resEntity = response.getEntity();  
				if(resEntity != null){  
					result = EntityUtils.toString(resEntity,charset);  
				}  
			}  
		}catch(Exception ex){  
			ex.printStackTrace();  
		}  
		return result;  
	}  
}
