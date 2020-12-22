package com.example.demo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlRequestDto {
    // "originalUrl" : "http://" - json sequlize
    private String originalUrl;

    @Builder
    public UrlRequestDto(String originalUrl){
        this.originalUrl = originalUrl;
    }

}
