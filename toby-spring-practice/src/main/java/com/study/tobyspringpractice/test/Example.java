package com.study.tobyspringpractice.test;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RestController
public class Example {
    private final HttpServletRequest request;

    public Example(final HttpServletRequest request) {
        log.info("HttpServletRequest 빈 등록");
        log.info("HttpServletRequest={}", request.getClass());  // 프록시 객체는 할당되는데, 실제 객체는 없다..?
//        log.info("HttpServletRequest={}", request.getHttpServletMapping());  // 에러남
        this.request = request;
    }


    @GetMapping("/test")
    public String test(HttpServletRequest request2) {
        final HttpServletRequest request3 = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info("request1={}", request.getClass());
        log.info("request2={}", request2.getClass());
        log.info("request3={}", request3.getClass());

        log.info("request1={}", request.getRequestURI());
        log.info("request2={}", request2.getRequestURI());
        log.info("request3={}", request3.getRequestURI());

        log.info("request2 equals request3 ? ={}", request2.equals(request3));

        return "hi";
    }
}
