package com.tutorial.spring.cloud.eureka.restclient.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dataset {

	@JsonProperty("data")
	private String[] data;

	private Date startDate;

	private Date endDate;
}
