package com.zhuru.constant;

/**
 * API响应码常量类
 */
public class ResponseCode {

    /**
     * 成功
     */
    public static final Integer SUCCESS = 0;

    /**
     * 失败
     */
    public static final Integer FAIL = 1;

    /**
     * 参数错误
     */
    public static final Integer PARAM_ERROR = 400;

    /**
     * 未授权
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * 禁止访问
     */
    public static final Integer FORBIDDEN = 403;

    /**
     * 资源不存在
     */
    public static final Integer NOT_FOUND = 404;

    /**
     * 服务器内部错误
     */
    public static final Integer INTERNAL_SERVER_ERROR = 500;

    /**
     * 业务错误
     */
    public static final Integer BUSINESS_ERROR = 501;
}