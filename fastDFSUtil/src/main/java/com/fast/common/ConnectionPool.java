package com.fast.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

/**
 * 
 * @ClassName: ConnectionPool 
 * @Description: TODO(连接池) 
 * @author  Luo  
 * @date 2018年12月20日 下午2:57:15 
 *
 */
public class ConnectionPool {

	//最大连接数
	private int size;
	//连接超时时间
	private int connectTime;
	//网络超时时间
	private int networkTimeOut;
	//tracker的http端口
	private int trackerHttpPort;
	//tracker的ip地址和端口
	private String trackerIps;
	
    //被使用的连接
    private ConcurrentHashMap<StorageClient1,Object> busyConnectionPool = null;
    //空闲的连接
    private ArrayBlockingQueue<StorageClient1> idleConnectionPool = null;
    
    private Object obj = new Object();
    
    private static ConnectionPool instance = new ConnectionPool();
    
    public static ConnectionPool getConnectionPool(){
        return instance;
    }
    /**
     * 
     * @Title: checkout 
     * @Description: TODO(取出连接池中的链接) 
     * @param @param waitTime
     * @param @return    设定文件 
     * @return StorageClient1    返回类型 
     * @date 2018年12月18日 上午9:08:41
     * @author Luo
     * @throws
     */
    public StorageClient1 checkout(int waitTime){
        StorageClient1 storageClient1 = null;
        try {
            storageClient1 = idleConnectionPool.poll(waitTime, TimeUnit.SECONDS);
            if(storageClient1 != null){
                busyConnectionPool.put(storageClient1, obj);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            storageClient1 = null;
            e.printStackTrace();
        }
        return storageClient1;
    }
    
    /**
     * 
     * @Title: checkin 
     * @Description: TODO(用完后将链接回收) 
     * @param @param storageClient1    设定文件 
     * @return void    返回类型 
     * @date 2018年12月18日 上午9:07:14
     * @author Luo
     * @throws
     */
    public void checkin(StorageClient1 storageClient1){
        if(busyConnectionPool.remove(storageClient1) != null){
            idleConnectionPool.add(storageClient1);
        }
    }
    
    /**
     * 
     * @Title: drop 
     * @Description: TODO(连接无效时，删除这个链接，在新建链接补充到连接池中) 
     * @param @param storageClient1    设定文件 
     * @return void    返回类型 
     * @date 2018年12月18日 上午9:07:47
     * @author Luo
     * @throws
     */
    public void drop(StorageClient1 storageClient1){
        if(busyConnectionPool.remove(storageClient1) != null){
            TrackerServer trackerServer = null;
            TrackerClient trackerClient = new TrackerClient();
            try {
                trackerServer = trackerClient.getConnection();
                StorageClient1 newStorageClient1 = new StorageClient1(trackerServer,null);
                idleConnectionPool.add(newStorageClient1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                if(trackerServer != null){
                    try {
                        trackerServer.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    /**
     * @Description: TODO(初始化各类参数) 
     */
    private ConnectionPool(){
//    	fastDFS.maxPoolSize=5
//		fastDFS.connectTime
//		fastDFS.networkTimeOut
//		fastDFS.tracker.http_port
//		fastDFS.trackerIps
    	
    	String connectTime = PropertiesConfig.getResourceValue("fastDFS.connectTime");
    	this.connectTime = Integer.parseInt(connectTime);
    	
    	String networkTimeOut = PropertiesConfig.getResourceValue("fastDFS.networkTimeOut");
    	this.networkTimeOut = Integer.parseInt(networkTimeOut);
    	
    	String httpPort = PropertiesConfig.getResourceValue("fastDFS.tracker.http_port");
    	this.trackerHttpPort = Integer.parseInt(httpPort);
    	
    	String maxPoolSize = PropertiesConfig.getResourceValue("fastDFS.maxPoolSize");
    	this.size = Integer.parseInt(maxPoolSize);
    	
    	String trackerIps = PropertiesConfig.getResourceValue("fastDFS.trackerIps");
    	this.trackerIps = trackerIps;
    	
    	
    	busyConnectionPool = new ConcurrentHashMap<StorageClient1, Object>();
        idleConnectionPool = new ArrayBlockingQueue<StorageClient1>(size);
        init(size);
    }
    
    /**
     * 
     * @Title: init 
     * @Description: TODO(初始化连接池，将fastdfs的连接放入到连接池中) 
     * @param @param size    设定文件 
     * @return void    返回类型 
     * @date 2018年12月18日 上午9:02:35
     * @author Luo
     * @throws
     */
    private void init(int size){
        initClientGlobal();
        TrackerServer trackerServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            //只需要一个tracker server连接
            trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient1 storageClient1 = null;
            for(int i=0; i<size; i++){
                storageClient1 = new StorageClient1(trackerServer,storageServer);
                idleConnectionPool.add(storageClient1);
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(trackerServer != null){
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 
     * @Title: initClientGlobal 
     * @Description: TODO(初始化FastDFS的参数,也可以加载配置文件) 
     * @param     设定文件 
     * @return void    返回类型 
     * @date 2018年12月18日 上午9:01:10
     * @author Luo
     * @throws
     */
    private void initClientGlobal(){
        //连接超时时间
        ClientGlobal.setG_connect_timeout(connectTime);
        //网络超时时间
        ClientGlobal.setG_network_timeout(networkTimeOut);
        ClientGlobal.setG_anti_steal_token(false);
        // 字符集
        ClientGlobal.setG_charset("UTF-8");
        ClientGlobal.setG_secret_key(null);
        // HTTP访问服务的端口号
        ClientGlobal.setG_tracker_http_port(trackerHttpPort);
         
        String [] ips = trackerIps.split(",");
        InetSocketAddress[] trackerServers = new InetSocketAddress[ips.length];
        
        for(int i=0;i<ips.length;++i){
    		String ip = ips[i].split(":")[0];
    		int port = Integer.parseInt(ips[i].split(":")[1]);
    		trackerServers[i]= new InetSocketAddress(ip,port);
        }
        TrackerGroup trackerGroup = new TrackerGroup(trackerServers);
        //tracker server 集群
        ClientGlobal.setG_tracker_group(trackerGroup);
    }
    
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(int connectTime) {
		this.connectTime = connectTime;
	}

	public int getNetworkTimeOut() {
		return networkTimeOut;
	}

	public void setNetworkTimeOut(int networkTimeOut) {
		this.networkTimeOut = networkTimeOut;
	}

	public int getTrackerHttpPort() {
		return trackerHttpPort;
	}

	public void setTrackerHttpPort(int trackerHttpPort) {
		this.trackerHttpPort = trackerHttpPort;
	}

	public String getTrackerIps() {
		return trackerIps;
	}

	public void setTrackerIps(String trackerIps) {
		this.trackerIps = trackerIps;
	}
    
}