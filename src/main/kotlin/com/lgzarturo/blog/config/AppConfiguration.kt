package com.lgzarturo.blog.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration {
    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}
