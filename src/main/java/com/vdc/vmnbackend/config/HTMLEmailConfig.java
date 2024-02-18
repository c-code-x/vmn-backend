package com.vdc.vmnbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * Configuration class for setting up FreeMarker for HTML email generation.
 */
@Configuration
public class HTMLEmailConfig {

    /**
     * Creates a FreeMarker configuration bean for generating HTML emails.
     *
     * @return The FreeMarkerConfigurationFactoryBean instance configured for HTML email generation.
     */
    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/EmailTemplates");
        return bean;
    }
}
