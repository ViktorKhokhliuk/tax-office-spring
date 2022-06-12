package org.project.spring.tax_office.logic.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.DocumentBuilderFactory;

@Configuration
public class XmlParserConfig {
    @Bean
    public DocumentBuilderFactory documentBuilderFactory() {
        return DocumentBuilderFactory.newInstance();
    }
}
