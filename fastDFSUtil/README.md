FastDFSUtil
    fastDFSUtil是一个为fastDFSUtil提供的工具类。在项目中引用jar包后需要在resources下新建fastDFS.properties文件，文件配置如下：
    #连接池最大连接数
    fastDFS.maxPoolSize=100
    #fastDFS连接超时时间
    fastDFS.connectTime=2000
    #fastDFS网络超时时间
    fastDFS.networkTimeOut=3000
    #tracker的http端口
    fastDFS.tracker.http_port=8080
    #tracker存储的ip地址，可以有多个，以逗号分割如：
    #127.0.0.1:22122，127.0.0.2:22122
    fastDFS.trackerIps=127.0.0.1:22122
1.	FastDfsUtil
    FastDfsUtil是一个类，类中包含文件上传、下载、删除、和获取文件元信息四种方法。
    调用方式
        FastDfsUtil fdu = new FastDfsUtil ();
        fdu.方法
1.1 upload
    public String upload(String groupName, byte[] fileBytes,String extName,NameValuePair[] metaList)
                            throws AppException
    参数:
    @param - groupName(文件要上传的组名称)
    @param - fileBytes(文件数组)
    @param - extName(文件后缀名字)
    @param - metaList(文件元信息:如文件名称，文件大小等)
    @return -
    @throws - AppException 设定文件
    返回:
    String (例如：group1/M00/00/00/wKizA1wbyPSAe8kMAANz87JhQ3M736.png ) 返回类型
    抛出:
    AppException
1.2 downFile
    public byte[] downFile(String path)
                    throws AppException
    参数:
    @param - path(文件地址)
    @return – byte[]
    @throws - AppException 设定文件
    返回:
    byte[] 返回类型
    抛出:
    AppException

1.3 deleteFile
    public int deleteFile(java.lang.String path)
                throws AppException
    参数:
    @param - path
    @throws - AppException 设定文件
    返回:
    int(0:文件删除成功，2：文件不存在 ，其它：文件删除出错 ) 返回类型
    抛出:
    AppException
1.4 getNameValuePair
    public  NameValuePair[] getNameValuePair(String path)
                                                        throws AppException
    参数:
    @param - path
    @throws - AppException 设定文件
    返回:
    NameValuePair[] 返回类型
    抛出:
    AppException
2. FileUtils
2.1 byte2File
    public static void byte2File(byte[] bfile, String filePath, String fileName)
    参数:
    @param - bfile
    @param - filePath
    @param - fileName 设定文件

2.2 file2Byte
    public static byte[] file2Byte(String filePath)
    参数:
    @param – filePath 本地文件路径
    @return - 设定文件
    返回:
    byte[] 返回类型
2.3 byte2Input
    public static final java.io.InputStream byte2Input(byte[] buf)
    参数:
    @param – buf 文件数组
    @return - 设定文件
    返回:
    InputStream 返回类型
2.4 input2byte
    public static final byte[] input2byte(java.io.InputStream inStream)
                                throws java.io.IOException
    参数:
    @param - inStream
    @return – byte[]
    @throws - IOException 设定文件
    返回:
    byte[] 返回类型
    抛出:
    java.io.IOException
