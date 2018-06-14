package com.hysm.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.sf.json.JSONObject;

import org.bson.Document;

import com.hysm.db.mongo.MongoUtil;

public class FileTest {

	
	/**
	 * 文件模式
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* MongoUtil mu=MongoUtil.getThreadInstance(); Document
		  one=mu.findOne("c_messages", null); String dir="D:/3/"; String
		  info=one.toJson(); long s=System.currentTimeMillis(); for(int
		  i=0;i<10000;i++){ writeLog(new File(dir+i+".bi"),info); } long
		  s2=System.currentTimeMillis(); System.out.println(s2-s);
		*/

		long s = System.currentTimeMillis();
		String dir = "D:/3/";
		File[] f = new File(dir).listFiles();
		int max=f.length-1;
		//Arrays.sort(f, new FileTest.CompratorByLastModified());
		for (int i=0;i<10;i++) {
			File one=f[max-i];
			System.out.println(one.getName() + "====" + one.lastModified());
		}
		long s2 = System.currentTimeMillis();
		System.out.println(s2 - s);
		
		
		
		
	}
	
	/**
	 * 一个文件
	 * @param args
	 */
	/*public static void main(String[] args) {
		
		 MongoUtil mu=MongoUtil.getThreadInstance(); Document
		  one=mu.findOne("c_messages", null); String dir="D:/3/1.bi"; String
		  info=one.toJson(); long s=System.currentTimeMillis(); 
		  File ff=new  File(dir);
		  for(int
		  i=0;i<10000;i++){ writeLog(ff,info); } long
		  s2=System.currentTimeMillis(); System.out.println(s2-s);
		 

		long s = System.currentTimeMillis();
		String dir="D:/3/1.bi";
		readLastNLine(new File(dir), 10);
		long s2 = System.currentTimeMillis();
		System.out.println(s2 - s);
		
		
		
		
	}
*/
	/**
	 * 进行文件排序时间
	 * 
	 * @author 谈情
	 */
	private static class CompratorByLastModified implements Comparator<File> {
		public int compare(File f1, File f2) {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0)
				return -1;
			else if (diff == 0)
				return 0;
			else
				return 1;
		}

		public boolean equals(Object obj) {
			return true;
		}
	}

	public static void writeLog(File file, String str) {
		try {

			if (!file.exists())
				file.createNewFile();

			FileOutputStream out = new FileOutputStream(file, true); // 如果追加方式用true
			StringBuffer sb = new StringBuffer();

			sb.append(str+"\n");
			out.write(sb.toString().getBytes("utf-8"));// 注意需要转换对应的字符集
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}
	
	
	 /** 
     * 读取文件最后N行  
     *  
     * 根据换行符判断当前的行数， 
     * 使用统计来判断当前读取第N行 
     *  
     * PS:输出的List是倒叙，需要对List反转输出 
     *  
     * @param file 待文件 
     * @param numRead 读取的行数 
     * @return List<String> 
     */  
    public static List<String> readLastNLine(File file, long numRead)  
    {  
        // 定义结果集  
        List<String> result = new ArrayList<String>();  
        //行数统计  
        long count = 0;  
          
        // 排除不可读状态  
        if (!file.exists() || file.isDirectory() || !file.canRead())  
        {  
            return null;  
        }  
          
        // 使用随机读取  
        RandomAccessFile fileRead = null;  
        try  
        {  
            //使用读模式  
            fileRead = new RandomAccessFile(file, "r");  
            //读取文件长度  
            long length = fileRead.length(); 
          
            //如果是0，代表是空文件，直接返回空结果  
            if (length == 0L)  
            {  
                return result;  
            }  
            else  
            {  
                //初始化游标  
                long pos = length - 1;  
                while (pos > 0)  
                {  
                    pos--;  
                    //开始读取  
                    fileRead.seek(pos);  
                    //如果读取到\n代表是读取到一行  
                    if (fileRead.readByte() == '\n')  
                    {  
                        //使用readLine获取当前行  
                        String line = fileRead.readLine();  
                        //保存结果  
                        result.add(line);  
                          
                        //打印当前行  
                        System.out.println(line);  
                          
                        //行数统计，如果到达了numRead指定的行数，就跳出循环  
                        count++;  
                        /*if (count == numRead)  
                        {  
                            break;  
                        }  */
                    }  
                }  
                if (pos == 0)  
                {  
                    fileRead.seek(0);  
                    result.add(fileRead.readLine());  
                }  
            }  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            if (fileRead != null)  
            {  
                try  
                {  
                    //关闭资源  
                    fileRead.close();  
                }  
                catch (Exception e)  
                {  
                }  
            }  
        }  
          
        return result;  
    }  

}
