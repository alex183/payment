package com.wirecard.payment.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.entity.enumerator.PaymentType;
import com.wirecard.payment.http.converter.ReceiptConverter;
import com.wirecard.payment.http.converter.ReceiptResponseConverter;
import com.wirecard.payment.http.data.request.*;
import com.wirecard.payment.http.data.response.PaymentReceiptResponse;
import com.wirecard.payment.usecase.GetPaymentById;
import com.wirecard.payment.usecase.GetPayments;
import com.wirecard.payment.usecase.Pay;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

    @Mock
    private Pay pay;

    @Mock
    private GetPayments getPayments;

    @Mock
    private GetPaymentById getPaymentById;

    @Mock
    private ReceiptResponseConverter receiptResponseConverter;

    @Mock
    private ReceiptConverter receiptConverter;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        when(pay.execute(any())).thenReturn(PaymentReceipt.builder().build());
        when(getPayments.execute()).thenReturn(asList(PaymentReceipt.builder().build()));
        when(getPaymentById.execute(anyString())).thenReturn(PaymentReceipt.builder().build());
        when(receiptResponseConverter.convertToResponse(anyListOf(PaymentReceipt.class))).thenReturn(asList(PaymentReceiptResponse.builder().build()));
        when(receiptResponseConverter.convertToResponse(any(PaymentReceipt.class))).thenReturn(PaymentReceiptResponse.builder().build());
        when(receiptConverter.convertToUseCase(any())).thenReturn(PaymentReceipt.builder().build());

        paymentController = new PaymentController(pay,getPayments,getPaymentById,receiptResponseConverter,receiptConverter);
    }

    @Test
    public void badRequestBecauseOfNoSessionIdHeaderOnGetPayments() throws Exception{
        mockMvc.perform(get("/payments")
                .header("origin","partner-x")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestBecauseOfNoOriginHeaderOnGetPayments() throws Exception{
        mockMvc.perform(get("/payments")
                .header("session-id","4321")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void okResponseOnGetPayments() throws Exception{
        mockMvc.perform(get("/payments")
                .header("origin","partner-x")
                .header("session-id","4321")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void badRequestBecauseOfNoSessionIdHeaderOnGetPaymentById() throws Exception{
        mockMvc.perform(get("/payments/asdasd")
                .header("origin","partner-x")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestBecauseOfNoOriginHeaderOnGetPaymentById() throws Exception{
        mockMvc.perform(get("/payments/gerger")
                .header("session-id","4321")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void okResponseOnGetPaymentById() throws Exception{
        mockMvc.perform(get("/payments/asdsa")
                .header("origin","partner-x")
                .header("session-id","4321")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void badRequestBecauseOfNoSessionIdHeaderOnPostPayment() throws Exception{
        mockMvc.perform(post("/payments")
                .header("origin","partner-x")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void badRequestBecauseOfNoOriginHeaderOnPostPayment() throws Exception{
        mockMvc.perform(post("/payments")
                .header("session-id","4321")
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void okResponseOnPostPayment() throws Exception{

        ObjectMapper objectMapper= new ObjectMapper();

        String body = objectMapper.writeValueAsString(PaymentRequest.builder()
                .buyer(Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(Client.builder()
                        .id("1234")
                        .build())
                .payment(Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.BOLETO)
                        .boleto(Boleto.builder()
                                .number("12345678901234567890123456789012345678901234").build())
                        .build())
                .build());

        mockMvc.perform(post("/payments")
                .header("origin", "partner-x")
                .header("session-id", "4321")
                .content(body)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void badRequestBecauseTypeCreditCardButNoCardObjectOnPostPayment() throws Exception{

        ObjectMapper objectMapper= new ObjectMapper();

        String body = objectMapper.writeValueAsString(PaymentRequest.builder()
                .buyer(Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(Client.builder()
                        .id("1234")
                        .build())
                .payment(Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.CREDIT_CARD)
                        .build())
                .build());

        mockMvc.perform(post("/payments")
                .header("origin", "partner-x")
                .header("session-id", "4321")
                .content(body)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void badRequestBecauseTypeBoletoButNoBoletoObjectOnPostPayment() throws Exception{

        ObjectMapper objectMapper= new ObjectMapper();

        String body = objectMapper.writeValueAsString(PaymentRequest.builder()
                .buyer(Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(Client.builder()
                        .id("1234")
                        .build())
                .payment(Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.BOLETO)
                        .build())
                .build());

        mockMvc.perform(post("/payments")
                .header("origin", "partner-x")
                .header("session-id", "4321")
                .content(body)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());

    }

}