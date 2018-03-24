package com.streamacho.api.config.template;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TemplateConfig implements ApplicationContextAware {

     private final MessageSource messageSource;
     private ApplicationContext applicationContext;

     @Bean
     public TemplateEngine templateEngine() {
          SpringTemplateEngine engine = new SpringTemplateEngine();
          engine.setTemplateResolver(templateResolver());
          engine.setMessageSource(messageSource);
          return engine;
     }

     @Bean
     public ITemplateResolver templateResolver() {
          SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
          resolver.setApplicationContext(applicationContext);
          resolver.setPrefix("classpath:/templates/");
          resolver.setSuffix(".html");
          resolver.setTemplateMode(TemplateMode.HTML);
          return resolver;
     }

     @Override
     public void setApplicationContext(ApplicationContext applicationContext) {
          this.applicationContext = applicationContext;
     }
}
