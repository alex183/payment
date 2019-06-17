package com.wirecard.payment.config;

import com.wirecard.payment.config.properties.ErrorMessagesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ErrorMessagesProperties.class})
public class PropertyConfiguration {
}
