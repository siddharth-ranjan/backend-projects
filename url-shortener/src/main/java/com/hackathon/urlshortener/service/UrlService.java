package com.hackathon.urlshortener.service;

import com.hackathon.urlshortener.entity.Url;
import com.hackathon.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    private final SecureRandom random = new SecureRandom();

    public String shortenUrl(String originalUrl) {

        Optional<Url> existingUrlOpt = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrlOpt.isPresent()) {
            return existingUrlOpt.get().getShortUrl();
        }

        String shortUrl = generateShortUrl();

        Optional<Url> existingOriginalUrl = urlRepository.findByShortUrl(shortUrl);

        while (existingOriginalUrl.isPresent()) {
            shortUrl = generateShortUrl();
            existingOriginalUrl = urlRepository.findByShortUrl(shortUrl);
        }

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        Optional<Url> url = urlRepository.findByShortUrl(shortUrl);
        return url.isPresent() ? url.get().getOriginalUrl() : null;
    }

    private String generateShortUrl() {
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARSET.length());
            shortUrl.append(CHARSET.charAt(index));
        }
        return shortUrl.toString();
    }
}