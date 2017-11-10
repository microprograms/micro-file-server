package com.github.microprograms.micro_file_server.public_api;

import com.github.microprograms.micro_api_runtime.model.ResponseCode;

public enum ErrorCodeEnum implements ResponseCode {

    /**文件上传失败*/
    upload_fail(1010, "文件上传失败"), /**文件大小不能为0*/
    file_size_cannot_be_zero(1011, "文件大小不能为0"), /**无法获取原始文件名*/
    the_original_file_name_cannot_be_obtained(1012, "无法获取原始文件名");

    private ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final int code;

    public int getCode() {
        return code;
    }

    private final String message;

    public String getMessage() {
        return message;
    }
}
