package com.jarvis.usercenterbackend.common;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {

        return new BaseResponse<>(0, data, "ok");
    }

    public static BaseResponse error(ErrorCode errorcode) {

        return new BaseResponse(errorcode);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 失败
     * @param errorcode
     * @param message
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorcode, String message, String description) {
        return new BaseResponse(errorcode.getCode(), null, message, description);
    }

    /**
     * 失败
     * @param errorcode
     * @param description
     * @return
     */
    public static BaseResponse error(ErrorCode errorcode, String description) {
        return new BaseResponse(errorcode.getCode(), errorcode.getMessage(), description);
    }

}
