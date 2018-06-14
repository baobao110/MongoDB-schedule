package com.hysm.util.mobile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class SMSender
{

	/**
	 * 发送短信
	 * @author sicheng
	 * @param phone
	 * @param code
	 * @return
	 */
	public static String sendSM(String phone,String code) 
	{

        try
        {
        	
        System.out.println("to send sm:"+code+" to:"+phone);
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", "114CAKE"),new NameValuePair("Key", "54aee66d8d5e9b133c72"),new NameValuePair("smsMob",phone),new NameValuePair("smsText",code)};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
		System.out.println("send sm result:"+result); //打印返回消息状态


		post.releaseConnection();
		return result;	
		}
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        
        return "-1";
	}

	public static String sendSM_link(String phone,String link) 
	{

        try
        {
        	
        System.out.println("to send sm:"+link+" to:"+phone);
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", "114CAKE"),new NameValuePair("Key", "54aee66d8d5e9b133c72"),new NameValuePair("smsMob",phone),new NameValuePair("smsText",link)};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
		System.out.println(link);
		System.out.println("send sm result:"+result); //打印返回消息状态


		post.releaseConnection();
		return result;	
		}
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
        
        return "-1";
	}
	
	/**
	 * 短信模版内容替换
	 * @author sicheng
	 * @param code 短信模版
	 * @param url
	 * @param orderno订单号
	 * @param customerName用户名
	 * @param goods商品
	 * @param ammount总计
	 * @param goodsAmmount 商品合计
	 * @param freight运费
	 * @return
	 */
	
	public static String replaceSM(String code,String url,String orderno,String customerName,String goods,String ammount,String goodsAmmount,String freight){
		
		if(!url.equals("")){
			
			code=code.replaceAll("\\{url\\}",url);
		}
		if(!orderno.equals("")){
			
			code=code.replaceAll("\\{orderno\\}",orderno);
		}
		if(!customerName.equals("")){
			
			code=code.replaceAll("\\{customer\\}", customerName);
		}
		if(!goods.equals("")){
			
			code=code.replaceAll("\\{goods\\}", goods);
		}
		if(!ammount.equals("")){
			
			code=code.replaceAll("\\{ammount\\}", ammount);
		}
		if(!goodsAmmount.equals("")){
			code=code.replaceAll("\\{goodsAmmount\\}", goodsAmmount);
			
		}
		if(!freight.equals("")){
			
			code=code.replaceAll("\\{freight\\}", freight);
		}
		
		return code;
		
	}
	
	
	
	
	   /**
	    * post链接方法
	    * 以字符串形式返回url
	    * @author sicheng
	    * @param url
	    * @return
	    */
		public static String post(String url,JSONObject map){
			Gson gson = new Gson();
			//post 请求url
			HttpPost httpPost = new HttpPost(url);
			try {
				httpPost.setEntity(new StringEntity(gson.toJson(map)));
				HttpResponse response=new DefaultHttpClient().execute(httpPost);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					return EntityUtils.toString(response.getEntity());
				}
			} catch (IOException e) {	
				e.printStackTrace();
			}		
			return null;
		}
	
	public static String regist(){
		
		String urlone="http://hprpt2.eucp.b2m.cn:8080/sdkproxy/regist.action";
		String p="cdkey=8SDK-EMY-6699-RIVNK&password=901504";
		
		String ssss=sendPost(urlone,p);
		return ssss;
	}
	
	//http://hprpt2.eucp.b2m.cn:8080/sdkproxy/getmo.action?cdkey=2SDK-EMY-6688-AAAAA&password=123456
	
	
	public static void  find_sended(String phone){
		
		String url="http://hprpt2.eucp.b2m.cn:8080/sdkproxy/getmo.action";
//		String param="cdkey=8SDK-EMY-6699-RIVNK&password=901504";
		String param="cdkey=8SDK-EMY-6699-RIVNK&password=901504&phone="+phone;
		String ss=sendPost(url,param);
		Map s=Dom4jDemo.parsexml(ss);
		
		System.out.println(s);
		
	}
	
	
	
		public static String   zj_send(String phone,String code){
			
		
					String url="http://hprpt2.eucp.b2m.cn:8080/sdkproxy/sendsms.action";
					try {
						code = URLEncoder.encode(code, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String param="cdkey=8SDK-EMY-6699-RIVNK&password=901504&phone="+phone+"&message="+"【华群物业】"+code;
					
					String ss=sendPost(url,param);
					Map s=Dom4jDemo.parsexml(ss);
//					int sss=(Integer)s.get("error");
					if(Integer.valueOf((String)s.get("error"))!=0){
						//发送失败
						
					
						
						return "-1";
					}
					
					return "1";
				}
				
				
			/*	public static String zj_money(){
					
					
					String url="http://hprpt2.eucp.b2m.cn:8080/sdkproxy/querybalance.action";
					String p="cdkey=8SDK-EMY-6699-REUSO&password=499307";
					String ss=sendPost(url,p);
					
					
					return ss;
				}*/
				
		public static String zj_money(){
			
			
			String url="http://hprpt2.eucp.b2m.cn:8080/sdkproxy/querybalance.action";
			String p="cdkey=8SDK-EMY-6699-RIVNK&password=901504";
			String ss=sendPost(url,p);
			
			
			return ss;
		}
				
				
				
				
				
				 /**
			     * 向指定 URL 发送POST方法的请求
			     * 
			     * @param url
			     *            发送请求的 URL
			     * @param param
			     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
			     * @return 所代表远程资源的响应结果
			     */
			    public static String sendPost(String url, String param) {
			        PrintWriter out = null;
			        BufferedReader in = null;
			        String result = "";
			        try {
			            URL realUrl = new URL(url);
			            // 打开和URL之间的连接
			            URLConnection conn = realUrl.openConnection();
			            // 设置通用的请求属性
			            conn.setRequestProperty("accept", "*/*");
			            conn.setRequestProperty("connection", "Keep-Alive");
			            conn.setRequestProperty("user-agent",
			                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			            // 发送POST请求必须设置如下两行
			            conn.setDoOutput(true);
			            conn.setDoInput(true);
			            // 获取URLConnection对象对应的输出流
			            out = new PrintWriter(conn.getOutputStream());
			            // 发送请求参数
			            out.print(param);
			            // flush输出流的缓冲
			            out.flush();
			            // 定义BufferedReader输入流来读取URL的响应
			            in = new BufferedReader(
			                    new InputStreamReader(conn.getInputStream()));
			            String line;
			            while ((line = in.readLine()) != null) {
			                result += line;
			            }
			        } catch (Exception e) {
			            System.out.println("发送 POST 请求出现异常！"+e);
			            e.printStackTrace();
			        }
			        //使用finally块来关闭输出流、输入流
			        finally{
			            try{
			                if(out!=null){
			                    out.close();
			                }
			                if(in!=null){
			                    in.close();
			                }
			            }
			            catch(IOException ex){
			                ex.printStackTrace();
			            }
			        }
			        return result;
			    }    

				
	
	
	
	
	
	
	//测试	
	public static void main(String[] args) throws HttpException, IOException {
			String phone="15900848421";
			String code="测试";
//			System.out.println(SMSender.zj_money());
			SMSender.zj_send(phone, code);
//			System.out.println(zj_money());
//		SMSender.find_sended("18913021990");
		
	/*	System.out.println(SMSender.regist());*/
		}
/*public static void main(String[] args) {
	String code="{customer}先生(女士)您好,您的订单{orderno}已下单成功,请前往支付:{url}";
	System.out.println(SMSender.replaceSM(code, "www.dofei.net", "1111", "chensc", "", "", "", ""));
	
}*/
		/*public static void main(String[] args) {
			String url="http://202.102.95.130:9080/Z114Order/soaservice/FacadeServlet";
			
			String order="{'ORDER':{'ORDERNO':'m12121211s098','SHOPNAME':'测试店铺','SHOPADDRESS': '测试地址','STATE': '未支付','CUSTOMER': {'NAME': '测试人','SEX': '男', 'MOBILE': '15900848421','ADDRESS': '测试地址'},'CREATE_TIME': '2016年6月1日 16:54:35', 'BRAND': '测试品牌','ORDER_GOODS': [ {'GOODSNAME': '商品1', 'NUM': '2', 'PRICE': '11000', 'PROPERTIES': '慕斯蛋糕 7寸 '}],'FREIGHT': '1000','AMMOUNT_SUM': '23000'}}";
			JSONObject jo=(JSONObject)JSONObject.parse(order);
			
	
			try{
			SMSender.post(url, jo);
			System.out.println("test success");
			}catch (Exception e) {
			e.printStackTrace();
			}
		}*/
		
}
