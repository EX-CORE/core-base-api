package com.core.base.corebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan
@EnableWebMvc
@ImportAutoConfiguration(FeignAutoConfiguration.class)
@EntityScan(basePackages = {"com.core.base.corebase"})
@EnableJpaRepositories("com.core.base.corebase")
@EnableJpaAuditing
public class CoreBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreBaseApplication.class, args);
    }
}
