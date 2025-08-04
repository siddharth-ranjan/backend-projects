package com.hackathon.cachingproxy.config;

import com.hackathon.cachingproxy.cache.InMemoryHttpProxy;
import com.hackathon.cachingproxy.proxy.DirectHttpProxy;
import com.hackathon.cachingproxy.proxy.HttpProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ProjectConfig {
    @Bean
    HttpProxy httpProxy() {
        return new DirectHttpProxy();
    }

    @Bean
    @Primary
    HttpProxy cacheHttpProxy(HttpProxy proxy) {
        return new InMemoryHttpProxy(proxy);
    }

}