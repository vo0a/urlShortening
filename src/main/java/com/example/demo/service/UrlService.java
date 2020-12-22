package com.example.demo.service;

import com.example.demo.domain.Url;
import com.example.demo.domain.UrlRepository;
import com.example.demo.web.dto.UrlRequestDto;
import com.example.demo.web.dto.UrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Encoder;

@RequiredArgsConstructor
@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public UrlResponseDto save(UrlRequestDto requestDto){

        // 중복관점 - read먼저해서 있으면 돌려주고 없으면 쓰기
        // 캐시와 마스터 에 있는지 먼저 확인.

        // print original Url
        logger.info(requestDto.getOriginalUrl());

        // 더 짧게 만들기
        String shortenUrl = Base64Utils.encodeToString(requestDto.getOriginalUrl().getBytes());

        // print shorten Url
        System.out.println(shortenUrl);

        Url entity = Url.builder()
                .originalUrl(requestDto.getOriginalUrl())
                .shortenUrl(shortenUrl)
                .build();

        // try catch 결과 확인 예외처리.
        // 중복 예외처리 - 없을 때만 쓰기
        urlRepository.save(entity);
        UrlResponseDto ret = findByShortenUrl(shortenUrl);

        return ret;
    }


    public UrlResponseDto findByShortenUrl(String shortenUrl){
        Url entity = urlRepository.findByShortenUrl(shortenUrl)
                .orElseThrow(() -> new IllegalArgumentException("URL not found : " + shortenUrl));

        return new UrlResponseDto(entity);
    }
}
