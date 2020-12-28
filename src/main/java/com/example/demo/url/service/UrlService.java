package com.example.demo.url.service;

import com.example.demo.url.domain.Url;
import com.example.demo.url.domain.UrlRepository;
import com.example.demo.url.model.dto.UrlRequestDto;
import com.example.demo.url.model.dto.UrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

@RequiredArgsConstructor
@Service
public class UrlService {

    @Value("${service.url}")
    private String baseUrl;

    private final UrlRepository urlRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public UrlResponseDto save(UrlRequestDto requestDto) {

        // Todo : 중복관점 - read먼저해서 있으면 돌려주고 없으면 쓰기
        // 캐시와 마스터 에 있는지 먼저 확인.

        // print original Url
        logger.info(requestDto.getOriginalUrl());

        // Todo : 더 짧게 만들기
        String encodeToString = Base64Utils.encodeToString(requestDto.getOriginalUrl().getBytes());
        String shortenUrl = baseUrl + encodeToString;

        // print shorten Url
        logger.info(shortenUrl);

        Url entity = Url.builder()
                .originalUrl(requestDto.getOriginalUrl())
                .shortenUrl(shortenUrl)
                .encodedUrl(encodeToString)
                .build();

        // Todo : try catch 결과 확인 예외처리.
        urlRepository.save(entity);
        UrlResponseDto ret = findByEncodedUrl(encodeToString);

        return ret;
    }


    public UrlResponseDto findByEncodedUrl(String encodeToString) {
        Url entity = urlRepository.findByEncodedUrl(encodeToString)
                .orElseThrow(() -> new IllegalArgumentException("URL not found : " + encodeToString));

        return new UrlResponseDto(entity);
    }
}
