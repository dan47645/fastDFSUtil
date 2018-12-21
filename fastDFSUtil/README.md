FastDFSUtil<Br/>
===========
    fastDFSUtil是一个为fastDFSUtil提供的工具类。在项目中引用jar包后需要在resources下新建fastDFS.properties文件，文件配置如下：<Br/>
    -----------------------------------------------------------
    #连接池最大连接数<Br/>
    fastDFS.maxPoolSize=100<Br/>
    #fastDFS连接超时时间<Br/>
    fastDFS.connectTime=2000<Br/>
    #fastDFS网络超时时间<Br/>
    fastDFS.networkTimeOut=3000<Br/>
    #tracker的http端口<Br/>
    fastDFS.tracker.http_port=8080<Br/>
    #tracker存储的ip地址，可以有多个，以逗号分割如：<Br/>
    #127.0.0.1:22122，127.0.0.2:22122<Br/>
    fastDFS.trackerIps=127.0.0.1:22122<Br/>
1.FastDfsUtil<Br/>
--------------------
    FastDfsUtil是一个类，类中包含文件上传、下载、删除、和获取文件元信息四种方法。<Br/>
    调用方式<Br/>
        FastDfsUtil fdu = new FastDfsUtil ();<Br/>
        fdu.方法<Br/>
1.1 upload<Br/>
--------------------
    public String upload(String groupName, byte[] fileBytes,String extName,NameValuePair[] metaList)<Br/>
                            throws AppException<Br/>
    参数:<Br/>
    @param - groupName(文件要上传的组名称)<Br/>
    @param - fileBytes(文件数组)<Br/>
    @param - extName(文件后缀名字)<Br/>
    @param - metaList(文件元信息:如文件名称，文件大小等)<Br/>
    @return -<Br/>
    @throws - AppException 设定文件<Br/>
    返回:<Br/>
    String (例如：group1/M00/00/00/wKizA1wbyPSAe8kMAANz87JhQ3M736.png ) 返回类型<Br/>
    抛出:<Br/>
    AppException<Br/>
1.2 downFile<Br/>
--------------------
    public byte[] downFile(String path)<Br/>
                    throws AppException<Br/>
    参数:<Br/>
    @param - path(文件地址)<Br/>
    @return – byte[]<Br/>
    @throws - AppException 设定文件<Br/>
    返回:<Br/>
    byte[] 返回类型<Br/>
    抛出:<Br/>
    AppException<Br/>

1.3 deleteFile<Br/>
--------------------
    public int deleteFile(java.lang.String path)<Br/>
                throws AppException<Br/>
    参数:<Br/>
    @param - path<Br/>
    @throws - AppException 设定文件<Br/>
    返回:<Br/>
    int(0:文件删除成功，2：文件不存在 ，其它：文件删除出错 ) 返回类型<Br/>
    抛出:<Br/>
    AppException<Br/>
1.4 getNameValuePair<Br/>
--------------------
    public  NameValuePair[] getNameValuePair(String path)<Br/>
                                                        throws AppException<Br/>
    参数:<Br/>
    @param - path<Br/>
    @throws - AppException 设定文件<Br/>
    返回:<Br/>
    NameValuePair[] 返回类型<Br/>
    抛出:<Br/>
    AppException<Br/>
2.FileUtils<Br/>
------------
2.1 byte2File<Br/>
-----------------
    public static void byte2File(byte[] bfile, String filePath, String fileName)<Br/>
    参数:<Br/>
    @param - bfile<Br/>
    @param - filePath<Br/>
    @param - fileName 设定文件<Br/>

2.2 file2Byte<Br/>
--------------------
    public static byte[] file2Byte(String filePath)<Br/>
    参数:<Br/>
    @param – filePath 本地文件路径<Br/>
    @return - 设定文件<Br/>
    返回:<Br/>
    byte[] 返回类型<Br/>
2.3 byte2Input<Br/>
--------------------
    public static final java.io.InputStream byte2Input(byte[] buf)<Br/>
    参数:<Br/>
    @param – buf 文件数组<Br/>
    @return - 设定文件<Br/>
    返回:<Br/>
    InputStream 返回类型<Br/>
2.4 input2byte<Br/>
--------------------
    public static final byte[] input2byte(java.io.InputStream inStream)<Br/>
                                throws java.io.IOException<Br/>
    参数:<Br/>
    @param - inStream<Br/>
    @return – byte[]<Br/>
    @throws - IOException 设定文件<Br/>
    返回:<Br/>
    byte[] 返回类型<Br/>
    抛出:<Br/>
    java.io.IOException<Br/>
