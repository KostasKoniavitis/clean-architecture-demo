package com.finartix.cleanarchitecture.demo.infrastructure.spring;

import java.util.Objects;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

@Configuration
public class SpringConfiguration {
    @Bean
    BeanFactoryPostProcessor beanFactoryPostProcessor(ApplicationContext beanRegistry) {
        return beanFactory -> {
            genericApplicationContext(
                    (BeanDefinitionRegistry)
                            ((AnnotationConfigServletWebServerApplicationContext) beanRegistry)
                                    .getBeanFactory());
        };
    }

    void genericApplicationContext(BeanDefinitionRegistry beanRegistry) {
        ClassPathBeanDefinitionScanner beanDefinitionScanner =
                new ClassPathBeanDefinitionScanner(beanRegistry);
        beanDefinitionScanner.addIncludeFilter(removeDtosAndEntitiesFilter());
        beanDefinitionScanner.scan("com.finartix.cleanarchitecture.demo");
    }

    static TypeFilter removeDtosAndEntitiesFilter() {
        return (MetadataReader mr, MetadataReaderFactory mrf) -> {
            var metadata = mr.getClassMetadata();
            var resource = mr.getResource();

            return !metadata.getClassName().endsWith("Dto")
                    && !metadata.getClassName().endsWith("Model")
                    && !metadata.getClassName().endsWith("Exception")
                    && !Objects.requireNonNull(resource.getFilename()).contains("Domain");
        };
    }
}
