package com.example.demo.url.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UrlResponse<T> {

    private int code;
    private String message;
    private T data;
}
