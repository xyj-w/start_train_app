package com.zhuru.common;

import lombok.Data;

/**
 * API统一响应格式
 */
@Data
public class ApiResponse<T> {

    /**
     * 响应码，0表示成功，非0表示失败
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 构造方法
     */
    public ApiResponse() {
        this.code = 0;
        this.message = "success";
    }

    /**
     * 构造方法
     */
    public ApiResponse(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    /**
     * 构造方法
     */
    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 构造方法
     */
    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>();
    }

    /**
     * 成功响应带数据
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    /**
     * 失败响应
     */
    public static <T> ApiResponse<T> fail(Integer code, String message) {
        return new ApiResponse<>(code, message);
    }

    /**
     * 失败响应带数据
     */
    public static <T> ApiResponse<T> fail(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}