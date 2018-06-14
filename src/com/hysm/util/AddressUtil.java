package com.hysm.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 根据IP地址判断IP地址的归属地
 * 
 * @author 陈思成 2015-1-26
 */
public class AddressUtil {
	/**
	 * 获取用户的真实ip地址
	 * 
	 * @param request
	 * @return ip
	 * @throws UnknownHostException 
	 */
	public static String getRemortIP(HttpServletRequest request){
//		 String ip = request.getHeader("x-forwarded-for");
//		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
//		 ip = request.getHeader("Proxy-Client-IP");
//		 }
//		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
//		 ip = request.getHeader("WL-Proxy-Client-IP");
//		 }
//		 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
//		 ip = request.getRemoteAddr();
//		 }
//		 return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
		if (request == null) {  
	        try {
				throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    }  
	    String ipString = request.getHeader("x-forwarded-for");  
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {  
	        ipString = request.getRemoteAddr();  
	    }  
	  
	    // 多个路由时，取第�?��非unknown的ip  
	    final String[] arr = ipString.split(",");  
	    for (final String str : arr) {  
	        if (!"unknown".equalsIgnoreCase(str)) {  
	            ipString = str;  
	            break;  
	        }  
	    }  
	  
	    return ipString.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ipString; 
	}
	/**
	 * 获取外网ip
	 * @return
	 */
	public static String getWebIp() {
		try {
			String strUrl = "http://www.ip138.com/ip2city.asp";
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(url
			.openStream()));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			String webContent = "";
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			webContent = sb.toString();
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");
			webContent = webContent.substring(start, end);
			return webContent;

		} catch (Exception e) {
			e.printStackTrace();
			return "error open url:" + null;
		}
	}
	
	/**
	 * 判断IP地址的归属地
	 * 
	 * @param content
	 * @param encodingString
	 * @return address
	 */
	public static String getAddresses(String content, String encodingString)
			throws UnsupportedEncodingException {
		// 这里调用pconline的接�?
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		String returnStr = getResult(urlStr, content, encodingString);
		// 从http://whois.pconline.com.cn取得IP�?��的省市区信息
		if (returnStr != null) {
			// 处理返回的省市区信息
			System.out.println(returnStr);
			String[] temp = returnStr.split(",");
			if (temp.length < 3) {
				return "0";// 无效IP，局域网测试
			}
			StringBuffer buffer = new StringBuffer();
			buffer.append(decodeUnicode((temp[5].split(":"))[1].replaceAll(
					"\"", "")));
			buffer.append(decodeUnicode((temp[7].split(":"))[1].replaceAll(
					"\"", "")));
			buffer.append(decodeUnicode((temp[9].split(":"))[1].replaceAll(
					"\"", "")));
			buffer.append(" "
					+ decodeUnicode((temp[11].split(":"))[1].replaceAll("\"",
							"")));
			String address = buffer.toString();
			return address;
		}
		return null;
	}

	/**
	 * 获取结果
	 * 
	 * @param urlStr
	 *            请求的地�?
	 * @param content
	 *            请求的参�?格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8�?
	 * @return buffer.toString();
	 */
	private static String getResult(String urlStr, String content,
			String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫�?
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫�?
			connection.setDoInput(true);// 是否打开输出�?true|false
			connection.setDoOutput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection
					.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数�?也就是提交表�?name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出�?
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));// �?��端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (Exception e) {
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	/**
	 * unicode转换成中�?
	 * 
	 * @param str
	 * @return outBuffer.toString();
	 */
	public static String decodeUnicode(String str) {
		char aChar;
		int len = str.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = str.charAt(x++);
			if (aChar == '\\') {
				aChar = str.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = str.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	public static void main(String[] args) {
		AddressUtil addressUtil = new AddressUtil();
		// 测试ip 219.136.134.157 中国=华南=广东�?广州�?越秀�?电信
		String ip = "219.136.134.157";
		String address = "";
		try {
			address = addressUtil.getAddresses("ip=" + ip, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(address);
		System.out.println(getWebIp());
		// 输出结果为：广东�?广州�?越秀�?
	}
}
