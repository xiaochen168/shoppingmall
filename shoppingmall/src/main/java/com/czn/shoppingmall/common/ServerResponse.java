package com.czn.shoppingmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status){
        this.status = status;
    }

    private ServerResponse(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, T data){
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 判断返回消息是正确还是错误,并且该方法再返回参数序列化的时候无效
     * @return
     */
    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T>  ServerResponse<T>  createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccessMessageAndData(String msg, T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String msg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> ServerResponse<T> createByErrorResponseCode(int errorCode, String errorMsg){
        return new ServerResponse<T>(errorCode, errorMsg);
    }

    public static <T> ServerResponse<T> createByIllegalArgument(){
        return new ServerResponse<T>(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }
     public static <T> ServerResponse<T> createByIllegalArgument(String errorMsg){
        return new ServerResponse<T>(ResponseCode.ILLEGAL_ARGUMENT.getCode(), errorMsg);
    }

    public static <T> ServerResponse<T> createByNeedLogin(){
        return new ServerResponse<T>(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
    }

    public static <T> ServerResponse<T> createByNeedLogin(String errorMsg){
        return new ServerResponse<T>(ResponseCode.NEED_LOGIN.getCode(), errorMsg);
    }

    public static <T> ServerResponse<T> createByException(String errorMsg){
        return new ServerResponse<T>(ResponseCode.Exception.getCode(), errorMsg);
    }

}
