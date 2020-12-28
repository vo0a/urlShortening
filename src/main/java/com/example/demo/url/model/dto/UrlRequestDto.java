package com.example.demo.url.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlRequestDto {
    private String originalUrl;

    @Builder
    public UrlRequestDto(String originalUrl) {
        this.originalUrl = originalUrl;
    }

}
