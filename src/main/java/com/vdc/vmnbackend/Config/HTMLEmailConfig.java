package com.vdc.vmnbackend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class HTMLEmailConfig {
    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean bean=new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/EmailTemplates");
        return bean;
    }

}
