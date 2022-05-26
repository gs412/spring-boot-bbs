package com.springbootbbs.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
public class MyLocaleResolver implements LocaleResolver {

    @Autowired
    private HttpServletRequest request;

    public Locale getLocal() {
        return resolveLocale(request);
    }

    /**
     * 从HttpServletRequest中获取Locale
     *
     * @param httpServletRequest    httpServletRequest
     * @return                      语言Local
     */
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取请求中的语言参数
        String language = httpServletRequest.getParameter("lang");
        //如果没有就使用默认的（根据主机的语言环境生成一个 Locale
        Locale locale = Locale.getDefault();
        //如果请求的链接中携带了 国际化的参数
        if (!StringUtils.isEmpty(language)){
            //zh_CN
            String[] s = language.split("-");
            //国家，地区
            locale = new Locale(s[0], s[1]);
        }
        return locale;
    }

    /**
     * 用于实现Locale的切换。比如SessionLocaleResolver获取Locale的方式是从session中读取，但如果
     * 用户想要切换其展示的样式(由英文切换为中文)，那么这里的setLocale()方法就提供了这样一种可能
     *
     * @param request               HttpServletRequest
     * @param httpServletResponse   HttpServletResponse
     * @param locale                locale
     */
    @Override
    public void setLocale(@NonNull HttpServletRequest request, @Nullable HttpServletResponse httpServletResponse, @Nullable Locale locale) {

    }

}
