package com.tutorial.spring.cloud.eureka.restclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
	private final static Logger log = LoggerFactory.getLogger(JsonUtil.class);

	private static final ObjectMapper mapper = new JsonUtil.CustomObjectMapper();

	public static <T> T fromJson(String s, Class<T> tClass) throws Exception {
		try {
			T t = mapper.readValue(s, tClass);
			return t;

		} catch (Exception e) {
			log.error(e.getClass().getSimpleName() + " in parsing Object from JSON: ", e);
			throw e;

		}
	}

	public static <T> T fromJson(String s, TypeReference<T> type) throws Exception {
		try {
			T t = mapper.readValue(s, type);
			return t;

		} catch (Exception e) {
			log.error(e.getClass().getSimpleName() + " in parsing Object from JSON: ", e);
			throw e;

		}
	}

	public static <T> T fromJson(String s, String nodeNameExtractData, Class<T> clazz) throws Exception {
		CustomObjectMapper mapper = new CustomObjectMapper();
		JsonNode rootNode = mapper.readTree(s);
		JsonNode foundNode = rootNode.findValue(nodeNameExtractData);
		T convertedObj = mapper.convertValue(foundNode, clazz);
		return convertedObj;
	}

	public static String stringify(Object value) {
		return stringify(value, false);
	}

	public static String stringify(Object value, boolean pretty) {
		try {
			return pretty ? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value)
					: mapper.writeValueAsString(value);
		} catch (Exception e) {
			return "";
		}
	}

	static class CustomObjectMapper extends ObjectMapper {
		private static final long serialVersionUID = 5517319811863038899L;

		public CustomObjectMapper() {
			super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
	}
}