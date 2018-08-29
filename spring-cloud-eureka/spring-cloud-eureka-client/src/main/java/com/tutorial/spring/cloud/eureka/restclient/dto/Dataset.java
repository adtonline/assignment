package com.tutorial.spring.cloud.eureka.restclient.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dataset {

	@JsonProperty("data")
	private List<String[]> data;

	private Date startDate;

	private Date endDate;

	@JsonProperty("startDate")
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	public Date getStartDate() {
		return startDate;
	}

	@JsonProperty("start_date")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonProperty("endDate")
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	public Date getEndDate() {
		return endDate;
	}

	@JsonProperty("end_date")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String[]> getData() {
		return data;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}
}
