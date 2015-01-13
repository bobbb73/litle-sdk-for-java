package com.litle.sdk;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.litle.sdk.generate.ApplepayHeaderType;
import com.litle.sdk.generate.ApplepayType;
import com.litle.sdk.generate.CardTokenType;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;
import com.litle.sdk.generate.OrderSourceType;
import com.litle.sdk.generate.PayPal;
import com.litle.sdk.generate.Sale;
import com.litle.sdk.generate.SaleResponse;

public class TestSale {

	private static LitleOnline litle;

	@BeforeClass
	public static void beforeClass() throws Exception {
		litle = new LitleOnline();
	}

	@Test
	public void simpleSaleWithCard() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setLitleTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000000");
		card.setExpDate("1210");
		sale.setCard(card);
		SaleResponse response = litle.sale(sale);
		assertEquals("Approved", response.getMessage());
	}

	@Test
	public void simpleSaleWithPayPal() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setLitleTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		PayPal paypal = new PayPal();
		paypal.setPayerId("1234");
		paypal.setToken("1234");
		paypal.setTransactionId("123456");
		sale.setPaypal(paypal);
		SaleResponse response = litle.sale(sale);
		assertEquals("Approved", response.getMessage());
	}

	@Test
	public void simpleSaleWithToken() throws Exception {
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardTokenType token = new CardTokenType();
		token.setCardValidationNum("349");
		token.setExpDate("1214");
		token.setLitleToken("1111222233334000");
		token.setType(MethodOfPaymentTypeEnum.VI);
		sale.setToken(token);
		SaleResponse response = litle.sale(sale);
		assertEquals("Approved", response.getMessage());
	}

	@Test
    public void simpleSaleWithApplepay() throws Exception{
        Sale sale = new Sale();
        sale.setAmount(106L);
        sale.setLitleTxnId(123456L);
        sale.setOrderId("12347");
        sale.setOrderSource(OrderSourceType.ECOMMERCE);

        ApplepayType applepayType = new ApplepayType();
        ApplepayHeaderType applepayHeaderType = new ApplepayHeaderType();
        applepayHeaderType.setApplicationData("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setEphemeralPublicKey("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setPublicKeyHash("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setTransactionId("1234");
        applepayType.setHeader(applepayHeaderType);
        applepayType.setData("user");
        applepayType.setSignature("sign");
        applepayType.setVersion("1");

//        PayPal paypal = new PayPal();
//        paypal.setPayerId("1234");
//        paypal.setToken("1234");
//        paypal.setTransactionId("123456");
//        sale.setPaypal(paypal);
        sale.setApplepay(applepayType);
        SaleResponse response = litle.sale(sale);
        assertEquals("Approved", response.getMessage());
    }

	@Test
    public void simpleSaleWithSecondaryAmount() throws Exception{
        Sale sale = new Sale();
        sale.setAmount(106L);
        sale.setSecondaryAmount(50L);
        sale.setLitleTxnId(123456L);
        sale.setOrderId("12344");
        sale.setOrderSource(OrderSourceType.ECOMMERCE);
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000000");
        card.setExpDate("1210");
        sale.setCard(card);
        SaleResponse response = litle.sale(sale);
        assertEquals("Approved", response.getMessage());
    }
}
