package com.wirecard.payment.config;

import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, Logbook logbook) {
		return builder.requestFactory(factory(logbook))
				.build();

	}

	private ClientHttpRequestFactory factory(Logbook logbook) {
		CloseableHttpClient client = HttpClientBuilder.create()
				.addInterceptorFirst(new LogbookHttpRequestInterceptor(logbook))
				.addInterceptorFirst(new LogbookHttpResponseInterceptor()).build();

		return new HttpComponentsClientHttpRequestFactory(client);
	}
}