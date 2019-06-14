package com.wirecard.payment.http;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.entity.enumerator.CardIssuer;
import com.wirecard.payment.exception.MockedException;
import com.wirecard.payment.exception.PaymentNotFoundException;
import com.wirecard.payment.exception.PaymentTypeNotYetSupportedException;
import com.wirecard.payment.exception.PaymentsNotFoundException;
import com.wirecard.payment.http.converter.ReceiptConverter;
import com.wirecard.payment.http.converter.ReceiptResponseConverter;
import com.wirecard.payment.http.data.request.PaymentRequest;
import com.wirecard.payment.http.data.response.ErrorResponse;
import com.wirecard.payment.usecase.GetPaymentById;
import com.wirecard.payment.usecase.GetPayments;
import com.wirecard.payment.usecase.Pay;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wirecard.payment.entity.enumerator.PaymentType.CREDIT_CARD;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/payments",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    private final Pay pay;
    private final GetPayments getPayments;
    private final GetPaymentById getPaymentById;
    private final ReceiptResponseConverter receiptResponseConverter;
    private final ReceiptConverter receiptConverter;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> pay(
            @RequestHeader(name = "origin") String origin,
            //This simulates the session id from the user. It would be validated in order grand access only to allowed
            // users(it would be better to intercept with a lib imported all api's, so its not implemented one by one)
            @RequestHeader(name = "session-id") String sessionId,
            @Valid @RequestBody PaymentRequest paymentRequest) {

        try{
            PaymentReceipt payment = receiptConverter.convertToUseCase(paymentRequest);

            if(CREDIT_CARD.equals(payment.getPayment().getType())
                    && CardIssuer.UNKNOWN.equals(payment.getPayment().getCard().getIssuer())){
                return new ResponseEntity<>(ErrorResponse.builder()
                        .userMessage("Validation Failed")
                        .developerMessage("Invalid credit dard issuer")
                        .build(), HttpStatus.BAD_REQUEST);
            }

            PaymentReceipt paymentReceipt = pay.execute(payment);

            return ResponseEntity.ok(receiptResponseConverter.convertToResponse(paymentReceipt));

        }catch (PaymentTypeNotYetSupportedException e){
            return new ResponseEntity<>(ErrorResponse.builder()
                    .userMessage(e.getUserMessage())
                    .developerMessage(e.getDeveloperMessage())
                    .build(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch(MockedException e){
            return new ResponseEntity<>(ErrorResponse.builder()
                    .userMessage(e.getUserMessage())
                    .developerMessage(e.getDeveloperMessage())
                    .build(), HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> findPayments(
            @RequestHeader(name = "origin") String origin,
            @RequestHeader(name = "session-id") String sessionId) {
        try {
            List<PaymentReceipt> paymentReceipts = getPayments.execute();
            return ResponseEntity.ok(receiptResponseConverter.convertToResponse(paymentReceipts));
        }catch (PaymentsNotFoundException e){
            return new ResponseEntity<>(ErrorResponse.builder()
                    .userMessage(e.getUserMessage())
                    .developerMessage(e.getDeveloperMessage())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{paymentReceiptId}")
    @ResponseBody
    public ResponseEntity<Object> findPaymentById(
            @RequestHeader(name = "origin") String origin,
            @RequestHeader(name = "session-id") String sessionId,
            @PathVariable(name= "paymentReceiptId") String id) {
        try {
            PaymentReceipt paymentReceipt = getPaymentById.execute(id);
            return ResponseEntity.ok(receiptResponseConverter.convertToResponse(paymentReceipt));
        }catch (PaymentNotFoundException e){
            return new ResponseEntity<>(ErrorResponse.builder()
                    .userMessage(e.getUserMessage())
                    .developerMessage(e.getDeveloperMessage())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

}
