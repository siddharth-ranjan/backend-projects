package com.hackathon.realtimecollaborativetextapp.model;

public class DocumentModel {

    private int version;
    private String content;

    public DocumentModel() {
    }

    public DocumentModel(int version, String content) {
        this.version = version;
        this.content = content;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "DocumentModel{" +
                "version=" + version +
                ", content='" + content + '\'' +
                '}';
    }
}
