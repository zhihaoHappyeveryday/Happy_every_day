package com.zhihao.common.orderpay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PayUtil {

	protected static Logger logger = LoggerFactory.getLogger(PayUtil.class);
	public static final int BUFFER_SIZE = 4096;
	
	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
		// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	/**
	 * 支付结果成功时，发给微信支付的参数
	 * @return
	 */
	public static String generatePaySuccessReplyXML() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<xml>").append("<return_code><![CDATA[SUCCESS]]></return_code>")
		.append("<return_msg><![CDATA[OK]]></return_msg>").append("</xml>");
		return stringBuffer.toString();
	}
	
	/**
	 * 支付结果失败时，发给微信支付的参数
	 * @return
	 */
	public static String generatePayErrorReplyXML() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<xml>").append("<return_code><![CDATA[FAIL]]></return_code>")
		.append("<return_msg><![CDATA[ERROR]]></return_msg>").append("</xml>");
		return stringBuffer.toString();
	}

	/**
	 * 支付宝结果成功时，发给支付宝的参数
	 * @return
	 */
	public static String generatePaySuccessReplyParams() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("success");
		return stringBuffer.toString();
	}
	/**
	 * 支付宝结果失败时，发给支付宝的参数
	 * @return
	 */
	public static String generatePayErrorReplyParams() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("failure");
		return stringBuffer.toString();
	}

	/**
	 * 解析alipay异步通知的参数
	 * @param requestParams 样例：
	 * {
	 *	"gmt_create": ["2017-07-14 14:38:54"],
	 *	"charset": ["utf-8"]
	 *	...
	 *	}
	 */
	public static Map<String, String> parseParams(Map<?, ?> requestParams){
		Map<String,String> params = new HashMap<>();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		return params;
	}
	
	/**
	 * 将元为单位的转换为分 （乘100）
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(Long amount) {
		return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
	}

	/**
	 *  将流转换为xml 
	 * @param in
	 * @param charset
	 * @throws IOException
	 */
	public static String copyToString(InputStream in, Charset charset) throws IOException {
		StringBuilder out = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(in, charset);
		char[] buffer = new char[BUFFER_SIZE];
		int bytesRead = -1;
		while ((bytesRead = reader.read(buffer)) != -1) {
			out.append(buffer, 0, bytesRead);
		}
		return out.toString();
	}
}