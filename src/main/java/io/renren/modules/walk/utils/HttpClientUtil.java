package io.renren.modules.walk.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientUtil {
	
	 private static final int DEFAULT_MAX_CONN_PER_ROUTE = 200;
	    private static final int DEFAULT_MAX_CONN_TOTAL = 400;
	    /**
	     * 设置超时时间
	     */
	    private static final int DEFAULT_CONNECTION_TIMEOUT = 1000;
	    private static final int DEFAULT_SO_TIMEOUT = 500;
	 
	    private static final String PARAMETER_ENCODING = "UTF-8";
	 
	    private static CloseableHttpClient client = null;
	    private static PoolingHttpClientConnectionManager ccm;
	 
	    /**
	     * 创建HttpClientBuilder
	     */
	    private static HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	 
	    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	 
	    static {
	        // TODO 根据不同的请求路线设置不同的线程池大小，因为每个路线的耗时情况不同
	        ccm = new PoolingHttpClientConnectionManager();
	 
	        ccm.setMaxTotal(DEFAULT_MAX_CONN_TOTAL);
	        ccm.setDefaultMaxPerRoute(DEFAULT_MAX_CONN_PER_ROUTE);
	 
	        RequestConfig defaultRequestConfig = RequestConfig.custom()
	                .setSocketTimeout(DEFAULT_SO_TIMEOUT)
	                .setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT)
	                .setConnectionRequestTimeout(DEFAULT_CONNECTION_TIMEOUT)
	                .build();
	        		client = httpClientBuilder
	                .setConnectionManager(ccm)
	                .setDefaultRequestConfig(defaultRequestConfig)
	                .build();
	    }
	 
	    /**
	     * 发送Get请求
	     *
	     * @param url 请求地址
	     * @param paramMap 请求参数
	     * @param headerMap Header参数
	     * @param encoding 响应的编码
	     * @return
	     */
	    public static String get(String url, Map<String, String> paramMap, Map<String, String> headerMap, String encoding) {
	        if (paramMap != null && !paramMap.isEmpty()) {
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            for (String key : paramMap.keySet()) {
	                params.add(new BasicNameValuePair(key, paramMap.get(key)));
	            }
	            String queryString = URLEncodedUtils.format(params, PARAMETER_ENCODING);
	            if (url.indexOf("?") > -1) {
	                url += "&" + queryString;
	            } else {
	                url += "?" + queryString;
	            }
	        }
	 
	        // 创建Get实例
	        HttpGet httpGet = new HttpGet(url);
	 
	        // 是否需要设置Header
	        if (headerMap != null && !headerMap.isEmpty()) {
	            Set<String> keySet = headerMap.keySet();
	            for (String key : keySet) {
	                httpGet.setHeader(key, headerMap.get(key));
	            }
	        }
	 
	        String result = null;
	        try {
	            HttpResponse response = client.execute(httpGet);
	            StatusLine status = response.getStatusLine();
	            HttpEntity entity = response.getEntity();
	            if (status.getStatusCode() == HttpStatus.SC_OK) {
	                result = EntityUtils.toString(entity, encoding);
	            } else {
	                httpGet.abort();
	                logger.error("Unable to fetch page {}, status code: {}, error", httpGet.getURI(), status.getStatusCode());
	            }
	        } catch (Exception e) {
	            logger.error("url: {}, error: {}", url, e.getMessage());
	            logger.debug("{}", e);
	            if (httpGet != null) {
	                httpGet.abort();
	            }
	        } finally {
	            if (httpGet != null) {
	                httpGet.releaseConnection();
	            }
	        }
	        return result;
	    }
	 
	    /**
	     * 发送Post请求
	     *
	     * @param url Post请求URL
	     * @param paramMap 参数
	     * @param headerMap Header参数
	     * @param encoding 响应的编码
	     * @return
	     */
	    public static String post(HttpClient client, String url, Map<String, String> paramMap, Map<String, String> headerMap, String encoding) {
	        // 创建Post实例
	        HttpPost httpPost = new HttpPost(url);
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        if (paramMap != null && !paramMap.isEmpty()) {
	            Set<String> keySet = paramMap.keySet();
	            for (String key : keySet) {
	                params.add(new BasicNameValuePair(key, paramMap.get(key)));
	            }
	        }
	 
	        // 是否需要设置Header
	        if (headerMap != null && !headerMap.isEmpty()) {
	            Set<String> keySet = headerMap.keySet();
	            for (String key : keySet) {
	                httpPost.addHeader(key, headerMap.get(key));
	            }
	        }
	 
	        String result = null;
	        try {
	            httpPost.setEntity(new UrlEncodedFormEntity(params, encoding));
	            HttpResponse response = client.execute(httpPost);
	            StatusLine status = response.getStatusLine();
	            HttpEntity entity = response.getEntity();
	            if (status.getStatusCode() == HttpStatus.SC_OK) {
	                result = EntityUtils.toString(entity, encoding);
	            } else {
	                httpPost.abort();
	                result = EntityUtils.toString(entity, encoding);
	                logger.error("Unable to fetch page {}, status code: {}", httpPost.getURI(), status.getStatusCode());
	            }
	        } catch (Exception e) {
	            logger.error("url: {}, error: {}", url, e.getMessage());
	            logger.debug("", e);
	            if (httpPost != null) {
	                httpPost.abort();
	            }
	        } finally {
	            if (httpPost != null) {
	                httpPost.releaseConnection();
	            }
	        }
	        return result;
	    }
	    
	    public static String sendJsonHttpPost(String url, String json,String token) {

	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        String responseInfo = null;
	        try {
	            HttpPost httpPost = new HttpPost(url);
	            if(StringUtils.isNotBlank(token)) {
	            	httpPost.setHeader("Authorization", token);
	            }
	            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
				httpPost.addHeader("Accept", "application/json");
	            ContentType contentType = ContentType.create("application/json", CharsetUtils.get("UTF-8"));
	            httpPost.setEntity(new StringEntity(json, contentType));
	            CloseableHttpResponse response = httpclient.execute(httpPost);
	            HttpEntity entity = response.getEntity();
	            int status = response.getStatusLine().getStatusCode();
	            if (status >= 200 && status < 500) {
	                if (null != entity) {
	                    responseInfo = EntityUtils.toString(entity);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                httpclient.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return responseInfo;
	    }
	    
	    public static String doPost(String uri, String param){
	        CloseableHttpClient httpClient = HttpClients.createDefault();

	        HttpPost post = new HttpPost(uri);
	        post.setHeader("Content-Type", "application/json");

			post.setHeader("Accept", "application/json");
	        StringEntity entity = new StringEntity(param, StandardCharsets.UTF_8);
	        post.setEntity(entity);

	        HttpResponse response;
			try {
				response = httpClient.execute(post);
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

	    }
	 
	    /**
	     * 二进制流数据进行POST通信
	     * @param url
	     * @param bytes
	     * @param headerMap
	     * @return
	     */
	    public static byte[] post(String url, byte[] bytes, Map<String,String> headerMap) {
	 
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.setEntity(new ByteArrayEntity(bytes));
	        HttpResponse httpResponse = null;
	        byte[] resBytes = null;
	        // 是否需要设置Header
	        if (headerMap != null && !headerMap.isEmpty()) {
	            Set<String> keySet = headerMap.keySet();
	            for (String key : keySet) {
	                httpPost.addHeader(key, headerMap.get(key));
	            }
	        }
	 
	        try {
	            httpResponse = client.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            int contentLength = (int)httpEntity.getContentLength();
	            resBytes = new byte[contentLength];
	            // 由于返回的结果可能一次性读不完，所以用buff保存每次读取的数据,而当某次读取到的长度返回值为-1时表示读取结束
	            byte[] buff = new byte[contentLength];
	            int total = 0;
	            int len;
	            while ((len = httpEntity.getContent().read(buff)) != -1) {
	                    for(int i = 0; i < len; i++) {
	                        resBytes[total+i] = buff[i];
	                    }
	                    total = total + len;
	            }
	            if(total != contentLength) {
	                logger.error("Read http post response buffer error, [{}]", url);
	            }
	        } catch (Exception e) {
	            logger.error("Something went wrong when call http post url: [{}]", url, e);
	        }finally {
	            if(httpPost != null) {
	                httpPost.releaseConnection();
	            }
	        }
	 
	        return resBytes;
	    }
	    
	    /**
	     * 获取数据流
	     *
	     * @param url
	     * @param paraMap
	     * @return
	     */
	    public static String doImgPost(String url, Map<String, Object> paraMap) {
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.addHeader("Content-Type", "application/json");
	        try{
	            // 设置请求的参数
	            JSONObject postData = new JSONObject();
	            for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
	                postData.put(entry.getKey(), entry.getValue());
	            }
	            httpPost.setEntity(new StringEntity(postData.toString(), "UTF-8"));
	            HttpClient httpClient = HttpClientBuilder.create().build();
	            HttpResponse response = httpClient.execute(httpPost);
	            HttpEntity entity = response.getEntity();
	            InputStream inputStream = entity.getContent();
	            
	            String base64Img = "";
                byte[] data = null;
                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100];
                int rc = 0;
                while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                data = swapStream.toByteArray();
 
                base64Img = new String(Base64.getEncoder().encode(data));
                
                return base64Img;
	        }catch (ConnectionPoolTimeoutException e){
	            e.printStackTrace();
	        }catch (ConnectTimeoutException e){
	            e.printStackTrace();
	        }catch (SocketTimeoutException e){
	            e.printStackTrace();
	        }catch (Exception e){
	            e.printStackTrace();
	        }finally {
	            httpPost.releaseConnection();
	        }
			return "";
	    }
	    
	    
	    /**
	     * 发送GET请求（HTTP），K-V形式
	     * @param url
	     * @author Charlie.chen；
	     * @return
	     */
	    public static String doGet(String url, Map<String, Object> params){
	        URI uri = null;
	        // 创建默认的HttpClient实例.
	        try(CloseableHttpClient httpclient = HttpClients.createDefault();) {
	            if(params != null){
	                List<NameValuePair> nameParams = paramsToNameValuePair(params);
	                String queryString = URLEncodedUtils.format(nameParams, "utf-8");
	                uri = URIUtils.resolve(new URI(url),queryString);
	            }else{
	                uri = new URI(url);
	            }
	            // 定义一个get请求方法
	            HttpGet httpget = new HttpGet(uri);
	 
	            // 执行get请求，返回response服务器响应对象, 其中包含了状态信息和服务器返回的数据
	            CloseableHttpResponse httpResponse = httpclient.execute(httpget);
	 
	            // 使用响应对象, 获得状态码, 处理内容
	            int statusCode = httpResponse.getStatusLine().getStatusCode();
	            logger.info("Send a http get request and the response code is :"+statusCode);
	            if (statusCode == HttpStatus.SC_OK) {
	                // 使用响应对象获取响应实体
	                HttpEntity entity = httpResponse.getEntity();
	                // 将响应实体转为字符串
	                String response = EntityUtils.toString(entity, "utf-8");
	                return response;
	            }
	        } catch (Exception e) {
	            logger.info("Send a http get request occurred exception",e);
	        }
	        return null;
	    }
	    
	    /**
	     * 转换参数
	     * @param params
	     * @return
	     */
	    private static List<NameValuePair> paramsToNameValuePair(Map<String,Object> params){
	        List<NameValuePair> pairList = new ArrayList<>(params.size());
	        for (Map.Entry<String, Object> entry : params.entrySet()) {
	            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue()==null ? null : entry.getValue().toString());
	            pairList.add(pair);
	        }
	        return pairList;
	    }

}
