package com.springbootbbs.libs;

import com.springbootbbs.config.MyLocaleResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
public class I18nUtil {

    @Value("${spring.messages.basename}")
    private String basename;

    private final MyLocaleResolver resolver;

    private static MyLocaleResolver customLocaleResolver;

    private static String path;


    public I18nUtil(MyLocaleResolver resolver) {
        this.resolver = resolver;
    }


    @PostConstruct
    public void init() {
        setBasename(basename);
        setCustomLocaleResolver(resolver);
    }

    /**
     * 获取 国际化后内容信息
     *
     * @param code 国际化key
     * @return 国际化后内容信息
     */
    public static String getMessage(String code) {
        Locale locale = customLocaleResolver.getLocal();
        return getMessage(code, null, code, locale);
    }

    /**
     * 获取指定语言中的国际化信息，如果没有则走英文
     *
     * @param code 国际化 key
     * @param lang 语言参数
     * @return 国际化后内容信息
     */
    public static String getMessage(String code, String lang) {
        Locale locale;
        if (StringUtils.isEmpty(lang)) {
            locale = Locale.US;
        } else {
            try {
                var split = lang.split("-");
                locale = new Locale(split[0], split[1]);
            } catch (Exception e) {
                locale = Locale.US;
            }
        }
        return getMessage(code, null, code, locale);
    }

    /**
     * 获取站内信指定语言 目前只支持 中文与英文两类 默认英文
     *
     * @param code 国际化 key
     * @param lang 语言参数
     * @return 国际化后内容信息
     */
    public static String getStationLetterMessage(String code, String lang) {
        Locale locale = Objects.equals(lang, "zh-CN") ? Locale.SIMPLIFIED_CHINESE : Locale.US;
        return getMessage(code, null, code, locale);
    }


    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        messageSource.setBasename(path);
        String content;
        try {
            content = messageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            log.error("国际化参数获取失败===>{},{}", e.getMessage(), e);
            content = defaultMessage;
        }
        return content;

    }

    public static void setBasename(String basename) {
        I18nUtil.path = basename;
    }

    public static void setCustomLocaleResolver(MyLocaleResolver resolver) {
        I18nUtil.customLocaleResolver = resolver;
    }

}
