package com.fast.util;
import java.net.SocketTimeoutException;

import org.csource.common.NameValuePair;  
import org.csource.fastdfs.StorageClient1;  

import com.fast.common.ConnectionPool;
import com.fast.common.ERRORS;
import com.fast.exception.AppException;  
  
  
  
/** 
 *  
 * @ClassName: FastDfsUtil 
 * @Description: fastdfs文件操作工具类 1).初始化连接池； 2).实现文件的上传与下载; 
 * @author Luo 
 *  
 */  
public class FastDfsUtil {   
     
    private ConnectionPool connectionPool = null; 
    
    public FastDfsUtil(){
    	init();
    }
   
  
    /**
     *  
     * @Title: init 
     * @Description: TODO(初始化线程池) 
     * @param     设定文件 
     * @return void    返回类型 
     * @date 2018年12月20日 下午4:32:14
     * @author Luo
     * @throws
     */
    public void init() {  
        connectionPool =   ConnectionPool.getConnectionPool();
    }  
  
   /**
    *   
    * @Title: upload 
    * @Description: TODO(文件上传) 
    * @param @param groupName(文件要上传的组名称)
    * @param @param fileBytes(文件数组)
    * @param @param extName(文件后缀名字)
    * @param @param metaList(文件元信息:如文件名称，文件大小等)
    * @param @return
    * @param @throws AppException    设定文件 
    * @return String (例如：group1/M00/00/00/wKizA1wbyPSAe8kMAANz87JhQ3M736.png )   返回类型 
    * @date 2018年12月18日 上午10:28:02
    * @author Luo
    * @throws
    */
    public String upload(String groupName, byte[] fileBytes, String extName,NameValuePair[] metaList) throws AppException {  
          
        StorageClient1 client1  = null;
        try {  
  
        	client1 = connectionPool.checkout(3000);  
  
            String[] results = client1.upload_file(groupName, fileBytes,extName, metaList);  
            //上传完成后释放链接，放入到连接池中
            connectionPool.checkin(client1);  
      
            //results[0]:组名，results[1]:远程文件名 
            if (results != null && results.length == 2) {  
  
                return results[0] + "/" + results[1];  
            } else {  
                throw ERRORS.UPLOAD_RESULT_ERROR.ERROR();  
            }  
        } catch (AppException e) { 
            throw e;  
  
        } catch (SocketTimeoutException e) {  
            throw ERRORS.WAIT_IDLECONNECTION_TIMEOUT.ERROR();  
        } catch (Exception e) {
            connectionPool.drop(client1);  
            throw ERRORS.SYS_ERROR.ERROR();  
  
        }  
  
    }  
    
    /**
     * 
     * @Title: downFile 
     * @Description: TODO(文件下载) 
     * @param @param path(文件地址)
     * @param @return
     * @param @throws AppException    设定文件 
     * @return byte[]    返回类型 
     * @date 2018年12月18日 上午10:31:04
     * @author Luo
     * @throws
     */
    public byte[] downFile(String path) throws AppException {  
  
        StorageClient1 client1  = null;  
  
        try {  
            client1 = connectionPool.checkout(3000);  
            byte[] by = client1.download_file1(path);
            //上传完成后释放链接，放入到连接池中 
            connectionPool.checkin(client1); 
           
            if (by == null || by.length<=0) {
                throw ERRORS.NOT_EXIST_FILE.ERROR(); 
            } 
            return  by;
        } catch (AppException e) { 
            throw e;  
        } catch (SocketTimeoutException e) {  
            throw ERRORS.WAIT_IDLECONNECTION_TIMEOUT.ERROR();  
        } catch (Exception e) { 
            connectionPool.drop(client1);  
            throw ERRORS.SYS_ERROR.ERROR();  
  
        }  
    } 
  
    /**
     *  
     * @Title: deleteFile 
     * @Description: TODO(删除上传的文件) 
     * @param @param path
     * @param @throws AppException    设定文件 
     * @return int(0:文件删除成功，2：文件不存在 ，其它：文件删除出错  )    返回类型 
     * @date 2018年12月18日 上午10:33:33
     * @author Luo
     * @throws
     */
    public int deleteFile(String path) throws AppException {  
  
        StorageClient1 client1  = null;  
  
        try {  
            client1 = connectionPool.checkout(3000);  
            //删除文件,并释放 trackerServer 
            int result = client1.delete_file1(path);  
  
            //上传完毕及时释放连接   
            connectionPool.checkin(client1);  
    
            //0:文件删除成功，2：文件不存在 ，其它：文件删除出错  
           return result;  
  
        } catch (SocketTimeoutException e) {  
            throw ERRORS.WAIT_IDLECONNECTION_TIMEOUT.ERROR();  
        } catch (Exception e) { 
            connectionPool.drop(client1);  
            throw ERRORS.SYS_ERROR.ERROR();  
  
        }  
    }
    
    /**
     * 
     * @Title: getNameValuePair 
     * @Description: TODO(获取文件的元信息) 
     * @param @param path
     * @param @throws AppException    设定文件 
     * @return  NameValuePair[]    返回类型 
     * @date 2018年12月18日 上午10:37:56
     * @author Luo
     * @throws
     */
    public NameValuePair[] getNameValuePair(String path) throws AppException {  
    	  
        StorageClient1 client1  = null;  
  
        try {  
            //获取可用的tracker,并创建存储server  
            client1 = connectionPool.checkout(3000);  
            //删除文件,并释放 trackerServer 
            NameValuePair[] result = client1.get_metadata1(path);  
  
            //上传完毕及时释放连接  
            connectionPool.checkin(client1);  
  
            if (result==null || result.length<=0) {
                throw ERRORS.NOT_EXIST_FILE.ERROR();
            }
            return result;
  
        } catch (AppException e) {  
            throw e;  
        } catch (SocketTimeoutException e) {  
            throw ERRORS.WAIT_IDLECONNECTION_TIMEOUT.ERROR();  
        } catch (Exception e) {
            connectionPool.drop(client1);  
            throw ERRORS.SYS_ERROR.ERROR();  
  
        }  
    } 
  
    public ConnectionPool getConnectionPool() {  
        return connectionPool;  
    }  
  
    public void setConnectionPool(ConnectionPool connectionPool) {  
        this.connectionPool = connectionPool;  
    }  
  
} 