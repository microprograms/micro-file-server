package com.github.microprograms.micro_file_server.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.microprograms.micro_api_runtime.exception.MicroApiExecuteException;
import com.github.microprograms.micro_file_server.public_api.ErrorCodeEnum;
import com.github.microprograms.micro_file_server.public_api.File_Upload_Api.Resp;
import com.github.microprograms.micro_file_server.public_api.UploadedFile;

public class HttpRequestHandler extends AbstractHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        doPost(httpRequest, httpResponse);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Resp resp = new Resp();
        resp.setData(new ArrayList<>());
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        try {
            String localStoragePath = MicroFileServer.getConfig().getLocalStoragePath();
            String localTempPath = MicroFileServer.getConfig().getLocalTempPath();
            File localStorageFile = new File(localStoragePath);
            if (!localStorageFile.exists()) {
                localStorageFile.mkdirs();
            }
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(20 * 1024 * 1024);
            diskFileItemFactory.setRepository(new File(localTempPath));
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setHeaderEncoding("UTF-8");
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            Iterator<FileItem> iterator = fileItems.iterator();
            while (iterator.hasNext()) {
                FileItem fileItem = iterator.next();
                if (!fileItem.isFormField()) {
                    if (fileItem.getSize() == 0) {
                        throw new MicroApiExecuteException(ErrorCodeEnum.file_size_cannot_be_zero);
                    }
                    UploadedFile uploadedFile = new UploadedFile();
                    uploadedFile.setFieldName(fileItem.getFieldName());
                    uploadedFile.setContentType(fileItem.getContentType());
                    uploadedFile.setSize(fileItem.getSize());
                    String originFileName = fileItem.getName();
                    if (StringUtils.isBlank(originFileName)) {
                        throw new MicroApiExecuteException(ErrorCodeEnum.the_original_file_name_cannot_be_obtained);
                    }
                    uploadedFile.setOriginFileName(originFileName);
                    String newFileName = null;
                    if (originFileName != null) {
                        String suffix = originFileName.substring(originFileName.lastIndexOf("."));
                        newFileName = UUID.randomUUID().toString().concat(suffix);
                        uploadedFile.setNewFileName(newFileName);
                    }
                    String urlFormat = MicroFileServer.getConfig().getUrlFormat();
                    uploadedFile.setUrl(String.format(urlFormat, newFileName));
                    resp.getData().add(uploadedFile);
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = fileItem.getInputStream();
                        os = new FileOutputStream(new File(localStoragePath, newFileName));
                        IOUtils.copy(is, os);
                    } catch (Exception e) {
                        throw e;
                    } finally {
                        IOUtils.closeQuietly(is);
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } catch (MicroApiExecuteException e) {
            resp.setCode(e.getResponseCode().getCode());
            resp.setMessage(e.getResponseCode().getMessage());
        } catch (Throwable e) {
            log.error("upload fail", e);
            resp.setCode(ErrorCodeEnum.upload_fail.getCode());
            resp.setMessage(ErrorCodeEnum.upload_fail.getMessage());
            resp.setStackTrace(ExceptionUtils.getStackTrace(e));
        } finally {
            out.print(JSON.toJSONString(resp));
            out.flush();
            out.close();
        }
    }
}
