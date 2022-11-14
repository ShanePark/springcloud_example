package com.example.mysecondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/second-service")
@Slf4j
public class SecondServiceController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to my second Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        log.info("header: {}", header);
        return "Hello World in Second Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        return "Hi there. This is a message from Second Service:" + request.getServerPort();
    }

}
