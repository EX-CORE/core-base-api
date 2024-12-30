package com.core.base.corebase.config;

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
public class CommonConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()

        // JavaTimeModule 설정
        val module = JavaTimeModule()

        // LocalDate 처리
        module.addDeserializer(LocalDate::class.java, LocalDateDeserializer(DateTimeFormatter.ISO_DATE))
        module.addSerializer(LocalDate::class.java, LocalDateSerializer(DateTimeFormatter.ISO_DATE))

        // LocalDateTime 처리
        module.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
        module.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))

        // 날짜 형식 설정
        mapper.setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"))

        // 추가적인 모듈 등록
        mapper.registerModule(ParameterNamesModule())
        mapper.registerModule(Jdk8Module())

        // JavaTimeModule 등록
        mapper.registerModule(module)

        return mapper
    }
}
