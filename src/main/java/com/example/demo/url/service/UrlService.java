package com.example.demo.url.service;

import com.example.demo.url.UrlEncoder;
import com.example.demo.url.domain.Url;
import com.example.demo.url.domain.UrlDao;
import com.example.demo.url.model.dto.UrlRequestDto;
import com.example.demo.url.model.dto.UrlResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UrlService {

    @Value("${service.url}")
    private String URL_PREFIX;
    private final UrlValidator urlValidator = new UrlValidator();
    private final UrlEncoder urlEncoder;
    private final UrlDao urlDao;

    @Transactional(rollbackFor = Exception.class)
    public UrlResponseDto save(UrlRequestDto requestDto) {

        String url = requestDto.getOriginalUrl();
        String encodeUrl = "";
        // 파라미터 유효성 검사
        if (!url.isEmpty() && isUrl(url)) {

            // TODO : 사용자도 같은지 확인
            // 입력된 Url이 저장된 축약 Url이거나 원본 URL이 존재하지 않으면 = 저장된 정보가 없으면
            // 새롭게 생성 후 sequence(id)를 구하고, id를 인코딩해 데이터베이스에 저장한다.
            // Todo : 중복관점 - 캐시에 있는지 확인
            // if()
            if (!urlDao.exists(url)) {
                Url entity = Url.builder()
                        .originalUrl(requestDto.getOriginalUrl())
                        .build();
                // try catch 결과 확인 예외처리.
                Url shortenUrl = urlDao.save(entity);
                try {
                    // id(시퀀스)를 Base62로 인코딩
                    encodeUrl = encodingUrl(String.valueOf(shortenUrl.getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    shortenUrl.setShortenUrl(URL_PREFIX + encodeUrl, encodeUrl);
                    urlDao.save(shortenUrl);
                }
            } else encodeUrl = urlDao.findByUrl(url).getEncodedUrl();
        }
        UrlResponseDto result = findEncodedUrl(encodeUrl);

        return result;
    }

    public UrlResponseDto findEncodedUrl(String url) {
        Url entity = urlDao.findByEncodedUrl(url)
                .orElseThrow(() -> new IllegalArgumentException("URL not found : " + url));


        return new UrlResponseDto(entity);
    }

    public String encodingUrl(String seqStr) throws Exception {
        return urlEncoder.urlEncoder((seqStr));
    }

    public boolean isUrl(String url) {
        return urlValidator.isValid(url);
    }
}
