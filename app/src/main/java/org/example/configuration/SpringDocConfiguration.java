package org.example.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info()
                .title(applicationContext.getId())
                .version(buildProperties.getVersion()));
    }
}
