package com.wirecard.payment;

import com.wirecard.payment.config.Java8DateTimeConfiguration;
import com.wirecard.payment.repositories.ReceiptRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Import(Java8DateTimeConfiguration.class)
@SpringBootApplication(scanBasePackages = {"com.wirecard.*"})
@EnableMongoRepositories(basePackageClasses = ReceiptRepository.class)
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}
}
