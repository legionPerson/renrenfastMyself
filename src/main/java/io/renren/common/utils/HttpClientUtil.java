package io.renren.common.utils;



import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpClientUtil {
    public  static String transRequest(String url, NameValuePair[] datas) {

        // 响应内容
        String result = "";
        // 定义http客户端对象--httpClient
        HttpClient httpClient = new HttpClient();
        // 定义并实例化客户端链接对象-postMethod
        PostMethod postMethod = new PostMethod(url);
        try{
            // 设置http的头
            postMethod.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
            // 填入各个表单域的值
            //NameValuePair[] data = { new NameValuePair("type", type), new NameValuePair("message", message) };
            NameValuePair[] data=datas;
            // 将表单的值放入postMethod中
            postMethod.setRequestBody(data);
            // 定义访问地址的链接状态
            int statusCode = 0;
            try{
                // 客户端请求url数据
                statusCode = httpClient.executeMethod(postMethod);
            }
        catch (Exception e) {
                e.printStackTrace();
            }
            // 请求成功状态-200
            if (statusCode == HttpStatus.SC_OK) {
                try {
                    result = postMethod.getResponseBodyAsString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                log.error("请求返回状态：" + statusCode);
            }
        } catch (Exception e) {
              log.error("请求异常,异常原因："+ e);
        } finally {
            // 释放链接
            postMethod.releaseConnection();
            httpClient.getHttpConnectionManager().closeIdleConnections(0);
        }
        return result;
    }

    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("传递JSON类型参数post请求异常", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("传递JSON类型参数关闭post请求异常", e);
            }
        }
        return resultString;
    }

    public static String insureResponsePost(String url, String param) {
        log.info("url:"+url+"   param:"+param);
        PrintWriter out = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = "";
        HttpURLConnection conn = null;
        StringBuffer strBuffer = new StringBuffer();
        try {
            URL realUrl= new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod( "POST");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(300000);
            conn.setRequestProperty("Charset", "UTF-8");
            // 传输数据为json，如果为其他格式可以进行修改
            conn.setRequestProperty( "Content-Type", "application/json");
            conn.setRequestProperty( "Content-Encoding", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput( true);
            conn.setDoInput( true);
            conn.setUseCaches( false);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            is = conn.getInputStream();
            br = new BufferedReader( new InputStreamReader(is));
            String line = null;
            while ((line=br.readLine())!= null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            log.error( "发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            close(out,br,conn);
        }
        return result;
    }
    public static String postRaw(String urls,String json){
        try {
            org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls);
            StringEntity stringEntity = new StringEntity(json);
            post.setEntity(stringEntity);
            post.setHeader("Content-type","application/json");
            HttpResponse response=httpClient.execute(post);
            String content= EntityUtils.toString(response.getEntity());
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发生异常，异常原因："+e.getMessage());
        }
        return "";
    }
    public static String insureResponseBlockGet(String url) {

        PrintWriter out = null;
        String result = "";
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer strBuffer = new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(300000);  // 传输数据为json，如果为其他格式可以进行修改
            conn.setRequestProperty("Charset", "UTF-8");

            conn.setRequestProperty( "Content-Type", "application/json");
            conn.setRequestProperty( "Content-Encoding", "UTF-8");

            is = conn.getInputStream();
            br = new BufferedReader( new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line=br.readLine())!= null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            System.out.println( "发送 GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            close(out,br,conn);
        }
        return result;
    }

    public static void close(PrintWriter out,BufferedReader br,HttpURLConnection conn){
        try {
            if (out != null) {
                out.close();
            }
            if (br != null) {
                br.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static String getHttpParam(HttpServletRequest request) throws Exception {
    	BufferedReader br =  null;
    	InputStreamReader in = null;
    	try {
    		in = new InputStreamReader(request.getInputStream(),"UTF-8");
    		br = new BufferedReader(in);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
		} finally {
			if(in!=null) {
				in.close();
			}
			if(br!=null) {
				br.close();
			}
		}
    }



    public static String getHttpParamForCtp(HttpServletRequest request) throws Exception {
        BufferedReader br =  null;
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(request.getInputStream(),"GBK");
            br = new BufferedReader(in);
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            log.info("================getHttpParamForCtp==============, 参数: {}", sb.toString());

            return sb.toString();
        } finally {
            if(in!=null) {
                in.close();
            }
            if(br!=null) {
                br.close();
            }
        }
    }

    private static String toChinese(String strValue) {
        try {
            if (strValue == null) {
                return "";
            } else {
                strValue = new String(strValue.getBytes("GBK"), StandardCharsets.UTF_8);
                log.info("================toChinese==============, 参数: {}", strValue);
                return strValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}



