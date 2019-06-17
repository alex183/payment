package com.wirecard.payment.repositories;

import com.wirecard.payment.repositories.data.ReceiptEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ReceiptRepository extends MongoRepository<ReceiptEntity, String> {

}
