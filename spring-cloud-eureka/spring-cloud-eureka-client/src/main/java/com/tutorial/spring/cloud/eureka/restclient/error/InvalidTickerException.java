package com.tutorial.spring.cloud.eureka.restclient.error;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Ticker symbol")
public class InvalidTickerException extends RuntimeException {

	private static final long serialVersionUID = 2100716802948636315L;

	public InvalidTickerException(String tickerSymbol) {
		super(MessageFormat.format("No such Ticker symbol {0}", tickerSymbol));
	}

}
