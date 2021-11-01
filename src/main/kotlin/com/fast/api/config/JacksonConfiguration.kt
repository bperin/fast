package com.fast.api.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


@Configuration
class JacksonConfiguration {
    @Bean
    fun jackson2ObjectMapperBuilder(): Jackson2ObjectMapperBuilder {
        return Jackson2ObjectMapperBuilder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    }
}