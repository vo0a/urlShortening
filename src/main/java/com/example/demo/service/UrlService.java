package com.example.demo.service;

import com.example.demo.domain.Url;
import com.example.demo.domain.UrlRepository;
import com.example.demo.web.dto.UrlRequestDto;
import com.example.demo.web.dto.UrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@RequiredArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlResponseDto save(UrlRequestDto requestDto){

        // print original Url
        System.out.println(requestDto.getOriginalUrl());

        String shortenUrl = Base64Utils.encodeToString(requestDto.getOriginalUrl().getBytes());

        // print shorten Url
        System.out.println(shortenUrl);

        Url entity = Url.builder()
                .originalUrl(requestDto.getOriginalUrl())
                .shortenUrl(shortenUrl)
                .build();

        return new UrlResponseDto(urlRepository.save(entity));
    }


    public UrlResponseDto findByShortenUrl(String shortenUrl){
        Url entity = urlRepository.findByShortenUrl(shortenUrl)
                .orElseThrow(() -> new IllegalArgumentException("URL not found : " + shortenUrl));

        return new UrlResponseDto(entity);
    }
}
