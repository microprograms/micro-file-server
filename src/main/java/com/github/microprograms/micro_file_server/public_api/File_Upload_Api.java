package com.github.microprograms.micro_file_server.public_api;

import com.github.microprograms.micro_entity_definition_runtime.annotation.Comment;
import com.github.microprograms.micro_api_runtime.annotation.MicroApiAnnotation;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_entity_definition_runtime.annotation.Required;

@Comment(value = "文件上传")
@MicroApiAnnotation(type = "read", version = "v1.0.0")
public class File_Upload_Api {

    public static Response execute(Request request) throws Exception {
        Resp resp = new Resp();
        return resp;
    }

    public static class Resp extends Response {

        @Comment(value = "已上传的文件列表")
        @Required(value = true)
        private java.util.List<UploadedFile> data;

        public java.util.List<UploadedFile> getData() {
            return data;
        }

        public void setData(java.util.List<UploadedFile> data) {
            this.data = data;
        }
    }
}
