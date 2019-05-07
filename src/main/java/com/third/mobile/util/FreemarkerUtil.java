package com.third.mobile.util;

import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * @author zhaowei
 * 模板引擎工具类
 */
public class FreemarkerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerUtil.class);

    public static Template getTemplate(String name){
        try {
            //通过Freemarker的Configuration读取相应的ftl
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
            configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/templates");
            configuration.setObjectWrapper(new BeansWrapperBuilder(Configuration.VERSION_2_3_23).build());
            Template template = configuration.getTemplate(name);
            return template;
        } catch (IOException e) {
            LOGGER.error("获取模板文件失败：{} {}", e.getMessage(), e.getClass().getSimpleName());
        }
        return null;
    }

    public static String fillContent(String templateName, HashMap<String,Object> paraMap){
        Template template = getTemplate(templateName);
        if(template != null){
            StringWriter stringWriter = new StringWriter();
            try {
                template.process(paraMap,stringWriter);
                return stringWriter.toString();
            } catch (TemplateException e) {
                LOGGER.error("获取模板文件失败：{} {}", e.getMessage(), e.getClass().getSimpleName());
            } catch (IOException e) {
                LOGGER.error("填充内容出错：{} {}", e.getMessage(), e.getClass().getSimpleName());
            }
        }
        return null;
    }
}
