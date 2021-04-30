package com.example.demo.url;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class UrlEncoder {

    @Value("${service.url}")
    private String URL_PREFIX;
    private final int BASE62 = 62;
    private final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String encoding(long param){
        StringBuffer sb = new StringBuffer();
        while(param > 0){
            sb.append(BASE62_CHAR.charAt((int) (param % BASE62)));
            param /= BASE62;
        }
        return URL_PREFIX + sb.toString();
    }

    private long decoding(String param) {
        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
            power *= BASE62;
        }
        return sum;
    }

    // 시퀀스를 인코딩
    public String urlEncoder(String seqStr) throws NoSuchAlgorithmException {
        String encodeStr = encoding(Integer.valueOf(seqStr));
        log.info("base62 encode result:" + encodeStr);
        return encodeStr;
    }

    // 디코딩
    public long urlDecoder(String encodeStr) throws NoSuchAlgorithmException {
        if(encodeStr.trim().startsWith(URL_PREFIX)){
            encodeStr = encodeStr.replace(URL_PREFIX, "");
        }
        long decodeVal = decoding(encodeStr);
        log.info("base62 decode result:" + decodeVal);
        return decodeVal;
    }

}
