package com.example.demo.url.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    boolean existsByOriginalUrlOrShortenUrl(String originalUrl, String shortenUrl);

    Url findFirstByShortenUrlOrOriginalUrlOrderByIdDesc(String shortenUrl, String originalUrl);

    Optional<Url> findByEncodedUrl(String encodedUrl);
}
