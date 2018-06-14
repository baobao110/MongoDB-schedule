package com.hysm.util.mobile;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 * 
* <p>Title:解析MTS  字符串xml</p>
* <p>Description: </p>
* <p>Company: hysm</p> 
* @author myf
* @date 2016年6月28日
 */
public class Dom4jDemo {

	public static Map parse(String protocolXML) {
		Map map = new HashMap();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(
					protocolXML)));

			Element root = doc.getDocumentElement();
			NodeList eles = root.getChildNodes();
			if (eles != null) {
				for (int i = 0; i < eles.getLength(); i++) {
					Node eles2 = eles.item(i);
					NodeList eles3 = eles2.getChildNodes();
					if (eles3 != null && eles3.getLength() > 0) {
						for (int j = 0, len = eles3.getLength(); j < len; j++) {
							Node eles4 = eles3.item(j);
							NodeList eles5 = eles4.getChildNodes();
							if (eles5 != null && eles5.getLength() > 1) {
								for (int x = 0, lenx = eles5.getLength(); x < lenx; x++) {
									Node eles6 = eles5.item(x);
									map.put(eles6.getNodeName(),
											eles6.getTextContent());
								}
							} else {
								map.put(eles4.getNodeName(),
										eles4.getTextContent());
							}
						}
					} else {

						map.put(eles2.getNodeName(), eles2.getTextContent());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	
	public static Map parsexml(String protocolXML) {
		Map map = new HashMap();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(
					protocolXML)));

			Element root = doc.getDocumentElement();
			NodeList eles = root.getChildNodes();
			if (eles != null) {
				for (int i = 0; i < eles.getLength(); i++) {
					Node eles2 = eles.item(i);
					map.put(eles2.getNodeName(), eles2.getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}