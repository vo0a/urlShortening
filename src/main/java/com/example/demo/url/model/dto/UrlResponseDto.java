package com.example.demo.url.model.dto;

import com.example.demo.url.domain.Url;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UrlResponseDto {

    private Long id;
    private String originalUrl;
    private String shortenUrl;

    @Builder
    public UrlResponseDto(Url entity) {
        this.id = entity.getId();
        this.originalUrl = entity.getOriginalUrl();
        this.shortenUrl = entity.getShortenUrl();
    }
}
