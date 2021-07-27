package com.stockmarket.company.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
//                "http://localhost:3000","http://stoxapp.herokuapp.com/"
                registry.addMapping("/**").allowedOrigins("http://localhost:3000","http://stoxapp.herokuapp.com/")
                        .allowedHeaders("*")
                        .allowedMethods("*");
            }
        };
    }
}