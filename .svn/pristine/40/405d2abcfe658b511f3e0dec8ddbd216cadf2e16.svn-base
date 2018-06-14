package com.hysm.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CommonUtil {

	/**
	 * JAVA生成的唯一号，用于记录的唯一键
	 * 
	 * @return 36位字符
	 */
	public static String Guid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * JAVA生成的唯一号，用于记录的监测终端唯一编码,去掉“-”
	 * 
	 * @return 32位字符
	 */
	public static String getT_UUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim())
				|| "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将表格中的NULL转为空字符
	 * 
	 * @param str
	 *            输入字符
	 * @return 输出字符
	 */
	public static String Null2Zero(Object str) {
		if (str == null) {
			str = "";
		} else {
			str = str.toString().trim();
		}
		return str.toString();
	}
	
	/**
	 * 删除List<String>重复记录
	 * 
	 * @param list
	 * @return
	 */

	public static List<String> removeRepeatStr(List<String> list) {

		List<String> list1 = new LinkedList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (!list1.contains(list.get(i))) {
				list1.add(list.get(i).toString());
			}
		}
		return list1;

	}

	
	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param src
	 *            字节数组
	 * @param src
	 *            字节数组长度
	 * @return
	 */
	public static String bytesToHexString(byte[] src, int byteLength) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || byteLength <= 0) {
			return null;
		}
		for (int i = 0; i < byteLength; i++) {
			if (i > 0 && i % 16 == 0) {
				stringBuilder.append(" ");
			}
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
			stringBuilder.append(" ");
		}
		return stringBuilder.toString();
	}

	/**
	 * byte数组转换成16进制字符数组
	 * 
	 * @param src
	 *            字节数组
	 * @param src
	 *            字节数组长度
	 * @return
	 */
	public static String[] bytesToHexStrings(byte[] src, int byteLength) {
		if (src == null || byteLength <= 0) {
			return null;
		}
		String[] str = new String[byteLength];

		for (int i = 0; i < byteLength; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				str[i] = "0";
			}
			str[i] = hv;
		}
		return str;
	}

	public static String toStringHex2(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "ASCII");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 16进制字符串转UTF-8字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String toStringHex1(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
						i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> removeSameItem(List<String> list) {
		List<String> difList = new ArrayList<String>();
		for (String t : list) {
			if (t != null && !difList.contains(t)) {
				difList.add(t);
			}
		}
		return difList;
	}

	// 将List数组{"1000","2000","3000"}返回成 字符串“1000,2000,3000”格式
	public static String getStringArrayToString(List<String> strArray) {
		StringBuffer sb = new StringBuffer();
		for (Integer integer = 0, integer2 = strArray.size(); integer < integer2; integer++) {
			sb.append(strArray.get(integer));
			if (integer < integer2 - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	// 将字符串 param1,param2,param3 返回成'param1','param2','param3'格式
	public static String getAuthFiledValueString(String auth_filedValue)
			throws Exception {
		String[] auth_filedValue1 = auth_filedValue.trim().split(",");
		StringBuffer stringBuffer = new StringBuffer();
		for (Integer integer = 0, integer2 = auth_filedValue1.length; integer < integer2; integer++) {
			stringBuffer.append("'");
			stringBuffer.append(auth_filedValue1[integer]);
			stringBuffer.append("'");
			if (integer < integer2 - 1) {
				stringBuffer.append(",");
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 用户忘记密码，返回4位随机数字码
	 * 
	 * @return
	 */
	public static int random_code() {
		Random ran = new Random();
		int r = 0;
		m1: while (true) {
			int n = ran.nextInt(10000);
			r = n;
			int[] bs = new int[4];
			for (int i = 0; i < bs.length; i++) {
				bs[i] = n % 10;
				n /= 10;
			}
			Arrays.sort(bs);
			for (int i = 1; i < bs.length; i++) {
				if (bs[i - 1] == bs[i]) {
					continue m1;
				}
			}
			break;
		}
		return r;
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位
	 * 
	 * @param round_mode
	 *            表示用户指定的舍入模式
	 * 
	 * @return 两个参数的商
	 * 
	 */
	public static double divide(double v1, double v2, int scale, int round_mode) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, round_mode).doubleValue();
	}

	// 默认除法运算精度

	private static final int DEFAULT_DIV_SCALE = 10;

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的和
	 * 
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的加法运算
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数数学加和，以字符串格式返回
	 * 
	 */

	public static String add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);

		BigDecimal b2 = new BigDecimal(v2);

		return b1.add(b2).toString();
	}

	/**
	 * 
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的差
	 * 
	 */

	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的减法运算
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数数学差，以字符串格式返回
	 * 
	 */

	public static String subtract(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);

		BigDecimal b2 = new BigDecimal(v2);

		return b1.subtract(b2).toString();
	}

	/**
	 * 
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的积
	 * 
	 */

	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));

		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的乘法运算
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的数学积，以字符串格式返回
	 * 
	 */

	public static String multiply(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);

		BigDecimal b2 = new BigDecimal(v2);

		return b1.multiply(b2).toString();
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的商
	 * 
	 */
	public static double divide(double v1, double v2) {
		return divide(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 * 
	 */
	public static double divide(double v1, double v2, int scale) {
		return divide(v1, v2, scale, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 
	 * 小数点以后10位，以后的数字四舍五入,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @return 两个参数的商，以字符串格式返回
	 * 
	 */

	public static String divide(String v1, String v2)

	{
		return divide(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位
	 * 
	 * @return 两个参数的商，以字符串格式返回
	 * 
	 */

	public static String divide(String v1, String v2, int scale) {
		return divide(v1, v2, DEFAULT_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 
	 * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
	 * 
	 * @param v1
	 * 
	 * @param v2
	 * 
	 * @param scale
	 *            表示需要精确到小数点以后几位
	 * 
	 * @param round_mode
	 *            表示用户指定的舍入模式
	 * 
	 * @return 两个参数的商，以字符串格式返回
	 * 
	 */

	public static String divide(String v1, String v2, int scale, int round_mode) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, round_mode).toString();
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 * 
	 */

	public static double round(double v, int scale)

	{
		return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @param round_mode
	 *            指定的舍入模式
	 * 
	 * @return 四舍五入后的结果
	 * 
	 */
	public static double round(double v, int scale, int round_mode) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		return b.setScale(scale, round_mode).doubleValue();
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理,舍入模式采用ROUND_HALF_EVEN
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @return 四舍五入后的结果，以字符串格式返回
	 * 
	 */
	public static String round(String v, int scale)

	{
		return round(v, scale, BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * 
	 * 提供精确的小数位四舍五入处理
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * 
	 * @param scale
	 *            小数点后保留几位
	 * 
	 * @param round_mode
	 *            指定的舍入模式
	 * 
	 * @return 四舍五入后的结果，以字符串格式返回
	 * 
	 */

	public static String round(String v, int scale, int round_mode) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		return b.setScale(scale, round_mode).toString();
	}
}
