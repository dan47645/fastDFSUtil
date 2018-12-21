package com.fast.common;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;  
  

/**
 * 
 * @ClassName: FileUtils 
 * @Description: TODO(文件操作工具) 
 * @author  Luo  
 * @date 2018年12月20日 下午5:13:29 
 *
 */
public class FileUtils {  
  
	
	/**
	 * 
	 * @Title: byte2Input 
	 * @Description: TODO(byte数组转成input) 
	 * @param @param buf
	 * @param @return    设定文件 
	 * @return InputStream    返回类型 
	 * @date 2018年12月20日 下午5:13:02
	 * @author Luo
	 * @throws
	 */
    public static final InputStream byte2Input(byte[] buf) {  
        return new ByteArrayInputStream(buf);  
    }  
  
    /**
     * 
     * @Title: input2byte 
     * @Description: TODO(input转byte数组) 
     * @param @param inStream
     * @param @return
     * @param @throws IOException    设定文件 
     * @return byte[]    返回类型 
     * @date 2018年12月20日 下午5:12:39
     * @author Luo
     * @throws
     */
    public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }  
    
    
    /**
     * 
     * @Title: getFile 
     * @Description: TODO(根据byte数组生成文件) 
     * @param @param bfile
     * @param @param filePath
     * @param @param fileName    设定文件 
     * @return void    返回类型 
     * @date 2018年12月20日 下午5:11:30
     * @author Luo
     * @throws
     */
    public static void byte2File(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }
    
    /**
     * 
     * @Title: getBytes 
     * @Description: TODO(根据文件生成byte数组) 
     * @param @param filePath
     * @param @return    设定文件 
     * @return byte[]    返回类型 
     * @date 2018年12月20日 下午5:12:13
     * @author Luo
     * @throws
     */
    @SuppressWarnings("unused")
	public static byte[] file2Byte(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
  
}