package com.fast.exception;


public class AppException extends Exception {  
    private static final long serialVersionUID = -1848618491499044704L;  
  
    private String code;  
    private String description;  
  
  
    public AppException(String code, String message) {  
        super(message);  
        this.code = code;  
    }  
  
    public AppException( String code, String message, String description) {  
        super(message);  
        this.code = code;  
        this.description = description;  
    }  

  
    /**
     *  
     * @Title: getCode 
     * @Description: TODO(错误码) 
     * @param @return    设定文件 
     * @return String    返回类型 
     * @date 2018年12月20日 下午4:42:45
     * @author Luo
     * @throws
     */
    public String getCode() {  
        return code;  
    }  
  
    /**
     *   
     * @Title: getDescription 
     * @Description: TODO(描述信息) 
     * @param @return    设定文件 
     * @return String    返回类型 
     * @date 2018年12月20日 下午4:42:27
     * @author Luo
     * @throws
     */
    public String getDescription() {  
        return description;  
    }  
  
    @Override  
    public String toString() {  
        StringBuilder sb = new StringBuilder();  
        sb.append(getClass().getName());  
        sb.append(": [");  
        sb.append("] - ");  
        sb.append(code);  
        sb.append(" - ");  
        sb.append(getMessage());  
        if (getDescription() != null) {  
            sb.append(" - ");  
            sb.append(getDescription());  
        }  
        return sb.toString();  
    }  
}  