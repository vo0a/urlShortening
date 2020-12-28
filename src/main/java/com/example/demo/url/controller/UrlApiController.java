package com.example.demo.url.controller;

import com.example.demo.url.model.UrlResponse;
import com.example.demo.url.service.UrlService;
import com.example.demo.url.model.dto.UrlRequestDto;
import com.example.demo.url.model.dto.UrlResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
public class UrlApiController {

    private final UrlService urlService;

    @PostMapping("/url")
    public ResponseEntity<UrlResponse<UrlResponseDto>> create(@RequestBody UrlRequestDto requestDto) {
        UrlResponseDto url = urlService.save(requestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UrlResponse.<UrlResponseDto>builder()
                        .code(HttpStatus.OK.value())
                        .message("Create shorten Url")
                        .data(url)
                        .build());

    }

    @GetMapping("/url/{shortenUrl}")
    public ModelAndView redirect(@PathVariable String shortenUrl) {
        UrlResponseDto url = urlService.findByShortenUrl(shortenUrl);
        return new ModelAndView("redirect:" + url.getOriginalUrl());
    }
}
