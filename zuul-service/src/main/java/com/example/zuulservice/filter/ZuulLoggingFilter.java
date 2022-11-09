package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class ZuulLoggingFilter extends ZuulFilter {

    @Override
    public Object run() throws ZuulException {
        log.info("********** printing logs ** : Started");

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        log.info("request uri -> {}", request.getRequestURI());
        log.info("********** printing logs ** : Ended\n");
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

}
