package com.example.demo.url.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUrl;
    private String shortenUrl;
    private String encodedUrl;

    @Builder
    public Url(String originalUrl, String shortenUrl, String encodedUrl) {
        this.originalUrl = originalUrl;
        this.shortenUrl = shortenUrl;
        this.encodedUrl = encodedUrl;
    }

}
