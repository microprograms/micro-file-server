package com.github.microprograms.micro_file_server.public_api;

import com.github.microprograms.micro_entity_definition_runtime.annotation.MicroEntityAnnotation;
import com.github.microprograms.micro_entity_definition_runtime.annotation.Comment;
import com.github.microprograms.micro_entity_definition_runtime.annotation.Required;

@MicroEntityAnnotation()
public class UploadedFile {

    @Comment(value = "表单字段名")
    @Required(value = true)
    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Comment(value = "文件类型")
    @Required(value = true)
    private String contentType;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Comment(value = "文件大小")
    @Required(value = true)
    private Long size;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Comment(value = "原始文件名")
    @Required(value = true)
    private String originFileName;

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    @Comment(value = "新文件名")
    @Required(value = true)
    private String newFileName;

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    @Comment(value = "访问路径")
    @Required(value = true)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
