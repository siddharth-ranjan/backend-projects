package com.hackathon.cachingproxy.proxy;

import java.net.http.HttpResponse;

public interface HttpProxy {
    HttpResponse<byte[]> get(String url);

    default void clear() {}
}