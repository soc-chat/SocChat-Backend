package io.dodn.springboot.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()

        config.allowCredentials = false
        config.allowedOrigins = mutableListOf("*")
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        config.exposedHeaders = listOf("Authorization", "Content-Type", "Accept")
        config.allowedHeaders = listOf("Authorization", "Content-Type", "Accept")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        return source
    }
}
