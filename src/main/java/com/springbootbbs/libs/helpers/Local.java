package com.springbootbbs.libs.helpers;

import com.springbootbbs.libs.I18nUtil;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public record Local(String lang) implements TemplateMethodModelEx {

    @Override
    public Object exec(List list) throws TemplateModelException {
        String message = I18nUtil.getMessage(list.get(0).toString(), this.lang);

        return message;
    }
}
