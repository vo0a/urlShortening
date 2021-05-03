package com.example.demo.url.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UrlDao {

    private final UrlRepository urlRepository;

    public boolean exists(String url) {
        return urlRepository.existsByOriginalUrlOrShortenUrl(url, url);
    }

    public Url findByUrl(String url) {
        return urlRepository.findFirstByShortenUrlOrOriginalUrlOrderByIdDesc(url, url);
    }

    public Optional<Url> findByEncodedUrl(String encodedUrl) {
        return urlRepository.findByEncodedUrl(encodedUrl);
    }

    public Url save(Url shorenUrl) {
        return urlRepository.save(shorenUrl);
    }
}
