package com.hysm.util.img;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

public class ImageUtil {
	public static BufferedImage getSmallImage(int width, int height,BufferedImage bi) throws Exception {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		//image = g2d.getDeviceConfiguration().createCompatibleImage(width,height, Transparency.TRANSLUCENT);
		//image=g2d.get
		g2d = image.createGraphics();
		//g2d.clearRect(0, 0, width, height);
		g2d.setBackground(Color.WHITE);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
	//	g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP, 1f));
	//	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.drawImage(bi, x, y, observer)
		Float swidth=Float.valueOf(bi.getWidth());
		Float sheight=Float.valueOf(bi.getHeight());
		Float dwidth=Float.valueOf(width);
		Float dheight=Float.valueOf(height);
		int startx=0;
		int starty=0;
		Float newwidth=0f;
		Float newheight=0f;
		if(sheight/swidth>=dheight/dwidth){
			newheight=dheight;
			newwidth=newheight*swidth/sheight;
			startx=(int)((dwidth-newwidth)/2);
			starty=0;
			
		}else{
			newwidth=dwidth;
			newheight=newwidth*sheight/swidth;
			startx=0;
			starty=(int)((dheight-newheight)/2);
			
		}
		g2d.drawImage(bi, startx, starty, newwidth.intValue(), newheight.intValue(),null);

		g2d.dispose();
		return image;
	}
	
	 /**
     * @author hxy
     * @date : 2016-10-10 下午1:44:56<br>
     * @param imgBase64     BASE64编码的图片
     * @param path          需要保存的文件夹路径
     * @return              返回图片名称
     */ 
	public static String  saveHeadimg(String imgBase64,String path){
	    // 判断文件是否为空  
        String picName=System.currentTimeMillis()+".jpg";
        try 
        {
          boolean b=  generateImage(imgBase64,path+"/"+picName);
          if(b==false){
              return null;
          }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
        return picName;
    }
    
	
    //syj专用方法，
    // 店铺文件夹路径，图片父文件夹名，图片名，宽，高
    
    public static void handleImage(String p_path,String parentDir,String fileName,int width ,int height) throws Exception {
        //首先创建父文件夹。
        File dstDiretory = new File(p_path+"/"+parentDir);
        if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
            dstDiretory.mkdirs();
        }
        
        
        
        
        //将原图读取
         BufferedImage bi = ImageIO.read(new FileInputStream(p_path+"/original/"+fileName));  
         
         BufferedImage image = new BufferedImage(width, height,
                 BufferedImage.TYPE_INT_RGB);
         Graphics2D g2d = image.createGraphics();
         g2d = image.createGraphics();
         g2d.setBackground(Color.WHITE);
         g2d.setColor(Color.WHITE);
         g2d.fillRect(0, 0, width, height);
         Float swidth=Float.valueOf(bi.getWidth());
         Float sheight=Float.valueOf(bi.getHeight());
         Float dwidth=Float.valueOf(width);
         Float dheight=Float.valueOf(height);
         int startx=0;
         int starty=0;
         Float newwidth=0f;
         Float newheight=0f;
         if(sheight/swidth>=dheight/dwidth){
             newheight=dheight;
             newwidth=newheight*swidth/sheight;
             startx=(int)((dwidth-newwidth)/2);
             starty=0;
             
         }else{
             newwidth=dwidth;
             newheight=newwidth*sheight/swidth;
             startx=0;
             starty=(int)((dheight-newheight)/2);
             
         }
         g2d.drawImage(bi, startx, starty, newwidth.intValue(), newheight.intValue(),null);

         g2d.dispose();
         
         //将新图片保存到路径
         ImageIO.write(image,"jpg",new File(p_path+"/"+parentDir+"/"+fileName)); 
         System.out.println("转换尺寸成功！======"+p_path+"/"+parentDir+"/"+fileName+"尺寸为"+width+":"+height);
     }
    
    public static boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
        return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
        // Base64解码
            String sss=imgStr.substring(0,1);
            System.out.println(sss);
            byte[] b;
         if(sss.equals("\"")){
             b = decoder.decodeBuffer(imgStr.substring(24,imgStr.length()-2));
         }else{
             b = decoder.decodeBuffer(imgStr.substring(23));
         }  
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {// 调整异常数据
                b[i] += 256;
            }
        }
        // 生成jpeg图片
        String imgFilePath = imgFile;// 新生成的图片
        FileOutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();
        } catch (Exception e) {
        throw e;
        }
   return true;
    }
}
