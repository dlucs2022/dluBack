package com.dlu.dluBack.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CrossDomainFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 允许所有的请求域名访问我们的跨域资源，可以固定单个或者多个内容
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://192.168.46.127:90");//
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://192.168.46.186:90");//
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://192.168.46.150:90");//
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:90");
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");//
        // httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:9090");// 允许所有的请求域名访问我们的跨域资源，可以固定单个或者多个内容
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");// 允许何种请求方法访问该跨域资源服务器
        httpResponse.setHeader("Access-Control-Max-Age", "3600");// 预检请求的有效期，单位为秒。有效期内，不会重复发送预检请求
        httpResponse.addHeader("Access-Control-Allow-Headers",
                "Accept, Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma," +
                        " Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");// 允许所有的请求header访问，可以自定义设置任意请求头信息
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");// 是否允许用户发送、处理cookie
        httpResponse.setHeader("Access-Control-Allow-Credential", "true");// 是否允许用户发送、处理cookie
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type,         Accept,Access-Token");

        chain.doFilter(req, httpResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("-----CrossDomainFilter init -------");
    }

    @Override
    public void destroy() {

    }

}

