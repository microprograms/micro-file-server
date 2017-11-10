package com.github.microprograms.micro_file_server.utils;

public class Config {
    private String localStoragePath;
    private String urlFormat;

    public String getLocalStoragePath() {
        return localStoragePath;
    }

    public void setLocalStoragePath(String localStoragePath) {
        this.localStoragePath = localStoragePath;
    }

    public String getUrlFormat() {
        return urlFormat;
    }

    public void setUrlFormat(String urlFormat) {
        this.urlFormat = urlFormat;
    }
}
