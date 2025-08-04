package com.hackathon.markdownnotetakingapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "/tmp/markdownFiles_hackathon"; // A default value

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}