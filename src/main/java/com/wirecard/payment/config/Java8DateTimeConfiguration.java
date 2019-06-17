/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wirecard.payment.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author alexandre
 */
@Configuration
public class Java8DateTimeConfiguration {
    @Bean
    public Module jsonMapperJava8DateTimeModule() {
        val bean = new SimpleModule();

        bean.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return LocalDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        });
        bean.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return LocalDate.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
            }
        });

        bean.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(
                    LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime));
            }
        });
        bean.addSerializer(LocalDate.class, new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(
                    LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE.format(localDate));
            }
        });

        return bean;
    }
}