package com.lai.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(
        basePackages = "com.lai.app",
        excludeFilters = {
                @ComponentScan.Filter(type= FilterType.ANNOTATION, value = EnableWebMvc.class),
                @ComponentScan.Filter(Controller.class)
        }
)
public class SpringConfig {
}
