package com.hysm.util.img;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFChart;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTSerTx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTStrVal;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextFont;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

public class PPTToImageUtil {
	public static final int ppSaveAsPNG = 17;

	public static void main(String[] args) {

		doPPTtoImage2007(new File("D:\\1.pptx"), "D:\\ppt", "a", "png");
		// ppt2007Img("D:\\1.pptx", "D:\\ppt");

		// doPPTtoImage(new File("D:\\1.ppt"), "D:\\ppt", "a", "jpg");
	}

	/**
	 * 设置PPTX字体
	 * 
	 * @param slide
	 */
	private static void setFont(XSLFSlide slide) {
		for (XSLFShape shape : slide.getShapes()) {
			if (shape instanceof XSLFTextShape) {
				XSLFTextShape txtshape = (XSLFTextShape) shape;
				for (XSLFTextParagraph paragraph : txtshape.getTextParagraphs()) {
					List<XSLFTextRun> truns = paragraph.getTextRuns();

					for (XSLFTextRun trun : truns) {
						trun.setFontFamily("宋体");
						trun.setUnderline(false);
					}
				}
			}

		}
	}

	/**
	 * PPT转图片 （jpeg）(2007)
	 * 
	 * @param file
	 * @param path
	 *            存放路径
	 * @param picName
	 *            图片前缀名称 如 a 生成后为a_1,a_2 ...
	 * @param picType
	 *            转成图片的类型，无点 如 jpg bmp png ...
	 * @return true/false
	 */
	public static boolean doPPTtoImage2007(File file, String path,
			String picName, String picType) {
		try {
			boolean isppt = checkFile(file);

			if (!isppt) {

				return false;

			}
			FileInputStream is = new FileInputStream(file);
			XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
			XSLFSlide[] xslfSlides = xmlSlideShow.getSlides();
			Dimension pageSize = xmlSlideShow.getPageSize();

			is.close();
			for (int i = 0; i < xslfSlides.length; i++) {
				System.out.print("第" + i + "页。");
				XSLFSlide oneXSLFSlide = xslfSlides[i];
				setFont(oneXSLFSlide);
				BufferedImage img = new BufferedImage(pageSize.width,
						pageSize.height, BufferedImage.TYPE_INT_RGB);

				Graphics2D graphics = img.createGraphics();

				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
						RenderingHints.VALUE_FRACTIONALMETRICS_ON);

				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width,
						pageSize.height));

				oneXSLFSlide.draw(graphics);

				FileOutputStream out = new FileOutputStream(path + "/"
						+ +(i + 1) + "." + picType);
				javax.imageio.ImageIO.write(img, "png", out);
				out.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean checkFile(File file) {
		int pos = file.getName().lastIndexOf(".");
		String extName = "";
		if (pos >= 0) {
			extName = file.getName().substring(pos);
		}
		if (".ppt".equalsIgnoreCase(extName)
				|| ".pptx".equalsIgnoreCase(extName)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据ppt文件生成图片
	 * 
	 * @param string
	 * @param pdfFile
	 */
	@SuppressWarnings("deprecation")
	public static synchronized void ppt2007Img(String sourceFile, String pdfFile) {
		try {
			FileInputStream is = new FileInputStream(sourceFile);
			XMLSlideShow ppt = new XMLSlideShow(is);
			is.close();
			Dimension pgsize = ppt.getPageSize();
			XSLFSlide[] slides = ppt.getSlides();
			for (int i = 0; i < slides.length; i++) {
				System.out.print("第" + i + "页。");
				// 设置字体为宋体，解决中文乱码问题
				XSLFSlide oneXSLFSlide = slides[i];
				CTSlide xmlObject = oneXSLFSlide.getXmlObject();
				CTGroupShape spTree = xmlObject.getCSld().getSpTree();
				CTShape[] spArray = spTree.getSpArray();
				for (CTShape shape : spArray) {
					CTTextBody txBody = shape.getTxBody();
					if (txBody == null) {
						continue;
					}
					CTTextParagraph[] pArray = txBody.getPArray();
					CTTextFont font = CTTextFont.Factory
							.parse("<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">"
									+ "<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> "
									+ "<a:latin typeface=\"+mj-ea\"/> "
									+ "</a:rPr>" + "</xml-fragment>");
					for (CTTextParagraph textParagraph : pArray) {
						CTRegularTextRun[] textRuns = textParagraph.getRArray();
						for (CTRegularTextRun textRun : textRuns) {
							CTTextCharacterProperties properties = textRun
									.getRPr();
							properties.setLatin(font);
						}
					}
				}
				BufferedImage img = new BufferedImage(pgsize.width,
						pgsize.height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = img.createGraphics();
				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width,
						pgsize.height));
				oneXSLFSlide.draw(graphics);
				FileOutputStream out = new FileOutputStream(new File(pdfFile
						+ File.separator + i + ".png"));
				ImageIO.write(img, "png", out);
				out.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		}
	}

	public static void renameFile(String path, String newName, String olName) {
		if (!olName.equals(newName)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + olName);
			File newfile = new File(path + "/" + newName);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newName + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同...");
		}
	}

	public static String formatNum(String name) {
		int index = 0;
		Pattern pattern = Pattern.compile("[0-9]");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			index = name.indexOf(matcher.group());
		}
		name = name.substring(index);
		name = name.substring(0, name.lastIndexOf("."));
		System.out.println(name);
		return name;
	}

	
	/**
	 * 获取数据
	 * @param localFile
	 * @param imgrealpath
	 * @param imgpath 
	 * @param file_code 
	 * @return
	 */
	public static JSONArray parsePptx(File localFile, String realpath, String imgpath, String file_code) {
		try {
			boolean isppt = checkFile(localFile);

			if (!isppt) {

				return null;

			}
			
			
			JSONArray arr=new JSONArray();
			
			FileInputStream is = new FileInputStream(localFile);
			XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
			XSLFSlide[] xslfSlides = xmlSlideShow.getSlides();
			Dimension pageSize = xmlSlideShow.getPageSize();

			is.close();
			for (int i = 0; i < xslfSlides.length; i++) {
				System.out.print("第" + i + "页。");
				
				
				XSLFSlide oneXSLFSlide = xslfSlides[i];
				JSONObject onejson=new JSONObject();
				
				String info=getTextDetail(oneXSLFSlide);
				onejson.put("info", info);
				
				
				setFont(oneXSLFSlide);
				BufferedImage img = new BufferedImage(pageSize.width,
						pageSize.height, BufferedImage.TYPE_INT_RGB);

				Graphics2D graphics = img.createGraphics();

				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
						RenderingHints.VALUE_FRACTIONALMETRICS_ON);

				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width,
						pageSize.height));

				oneXSLFSlide.draw(graphics);
				String outimg=imgpath+file_code+"_"
						+(i + 1) + ".jpg";
				FileOutputStream out = new FileOutputStream(realpath+outimg);
				
				
				javax.imageio.ImageIO.write(img, "jpg", out);
				out.close();
				onejson.put("img", outimg);
				
				
				arr.add(onejson);
				
				
			}
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getTextDetail(XSLFSlide oneXSLFSlide) {
		StringBuffer sb=new StringBuffer();
		
		CTSlide rawSlide = oneXSLFSlide.getXmlObject();
		                CTGroupShape gs = rawSlide.getCSld().getSpTree();
		                 CTShape[] shapes = gs.getSpArray();
		                 for(CTShape shape:shapes){
		                     CTTextBody tb = shape.getTxBody();
		                     if(null==tb){
		                         continue;
		                     }
		                    CTTextParagraph[] paras = tb.getPArray();
		                    for(CTTextParagraph textParagraph:paras){
		                        CTRegularTextRun[] textRuns = textParagraph.getRArray();
		                        for(CTRegularTextRun textRun:textRuns){
		                            sb.append(textRun.getT());
		                         }
		                    }
		                }
		                 
		                 
		                 return sb.toString();
	}

}