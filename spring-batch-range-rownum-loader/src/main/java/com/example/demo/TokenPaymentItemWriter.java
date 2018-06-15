package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class TokenPaymentItemWriter implements ItemWriter<TokenPayment>{

	private static final Logger log = LoggerFactory.getLogger(TokenPaymentItemWriter.class);

	@Override
	public void write(List<? extends TokenPayment> tokenPaymentList) throws Exception {

		Map<Long, TokenPayment> tokenPaymentMap = new HashMap<>();

		log.info("Token Payment Records Fetched: {}", tokenPaymentList.size());

		for (TokenPayment tokenPayment : tokenPaymentList) {
			tokenPaymentMap.put(tokenPayment.getTokenRefId(), tokenPayment);
		}
		
		log.info("Token Payment Records added in the Map");
	}
}