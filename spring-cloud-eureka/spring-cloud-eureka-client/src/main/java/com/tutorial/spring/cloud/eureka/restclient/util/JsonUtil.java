package com.tutorial.spring.cloud.eureka.restclient.util;

import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.capitaland.ewallet.common.util.CommonConstant;
import com.capitaland.ewallet.web.rest.errors.VendorApiException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
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
	private static final ObjectMapper mapper = new JsonUtil.CustomObjectMapper();

	public static <T> T fromJSON(String s, Class<T> tClass) throws Exception {

		try {
			T t = mapper.readValue(s, tClass);
			return t;

		} catch (Exception e) {
			log.error(e.getClass().getSimpleName() + " in parsing Object from JSON: ", e);
			throw e;

		} 
	}

	public static <T> T fromJSON(String s, TypeReference<T> type) {

		ObjectMapper mapper = new ObjectMapper();

		// Ignore unknown properties.
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			T t = mapper.readValue(s, type);
			return t;

		} catch (Exception e) {
			log.error(e.getClass().getSimpleName() + " in parsing Object from JSON: ", e);
			throw e;

		} 
	}
	
	public static <T> T fromJSON(String s, String resultNodeName, TypeReference<T> type) throws Exception {

		JSONParser parser = new JSONParser();

		Object obj;
		try {
			obj = parser.parse(s);
		} catch (ParseException e) {
			log.error(e.getClass().getSimpleName() + " in parsing Object from JSON: ", e);
			throw e;
		}

		JSONObject jsonObject = (JSONObject) obj;
		String responseCode = (String) jsonObject.get(CommonConstant.RESPONSE_CODE);
		if (CommonConstant.RESPONSE_CODE_SUCCESS.equals(responseCode)) {
			Object resultNode = jsonObject.get(resultNodeName);
			JsonNode jsonNode;
			if (resultNode instanceof JSONObject) {
				jsonNode = convertJsonFormat((JSONObject) resultNode);
			} else {
				// instanceof JSONArray
				jsonNode = convertJsonFormat((JSONArray) resultNode);
			}
			ObjectMapper mapper = new ObjectMapper();
			T t = mapper.readValue(new TreeTraversingParser(jsonNode), type);
			return t;
		} else {
			// response fail
			String message = (String) jsonObject.get(CommonConstant.MESSAGE);
			throw new VendorApiException(responseCode, message);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T fromJSON(String s, String resultNodeName, Class<T> tClass) throws Exception {

		JSONParser parser = new JSONParser();

		Object obj;
		try {
			obj = parser.parse(s);
		} catch (ParseException e) {
			log.error(e.getClass().getSimpleName() + " in parsing Object from JSON: ", e);
			throw e;
		}

		JSONObject jsonObject = (JSONObject) obj;

		String responseCode = (String) jsonObject.get(CommonConstant.RESPONSE_CODE);
		if (CommonConstant.RESPONSE_CODE_SUCCESS.equals(responseCode)) {
			Object resultNode = jsonObject.get(resultNodeName);
			JsonNode jsonNode;
			if (resultNode instanceof JSONObject) {
				jsonNode = convertJsonFormat((JSONObject) resultNode);
			} else if(resultNode instanceof JSONArray){
				// instanceof JSONArray
				jsonNode = convertJsonFormat((JSONArray) resultNode);
			} else {
				// primitive type not Json
				return (T) resultNode;
			}
			ObjectMapper mapper = new ObjectMapper();
			T t = mapper.readValue(new TreeTraversingParser(jsonNode), tClass);
			return t;
		} else {
			// response fail
			String message = (String) jsonObject.get(CommonConstant.MESSAGE);
			throw new VendorApiException(responseCode, message);
		}
	}
	
	public static JsonNode convertJsonFormat(JSONObject json) {
	    ObjectNode ret = JsonNodeFactory.instance.objectNode();

	    @SuppressWarnings("unchecked")
	    Iterator<String> iterator = json.keySet().iterator();
	    for (; iterator.hasNext();) {
	        String key = iterator.next();
	        Object value;
	        value = json.get(key);
	        if (value == null)
	            ret.putNull(key);
	        else if (value instanceof String)
	            ret.put(key, (String) value);
	        else if (value instanceof Integer)
	            ret.put(key, (Integer) value);
	        else if (value instanceof Long)
	            ret.put(key, (Long) value);
	        else if (value instanceof Double)
	            ret.put(key, (Double) value);
	        else if (value instanceof Boolean)
	            ret.put(key, (Boolean) value);
	        else if (value instanceof JSONObject)
	            ret.set(key, convertJsonFormat((JSONObject) value));
	        else if (value instanceof JSONArray)
	            ret.set(key, convertJsonFormat((JSONArray) value));
	        else
	            throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
	    }
	    return ret;
	}

	static JsonNode convertJsonFormat(JSONArray json) {
	    ArrayNode ret = JsonNodeFactory.instance.arrayNode();
	    for (int i = 0; i < json.size(); i++) {
	        Object value;
	        value = json.get(i);
	        if (value == null)
	            ret.addNull();
	        else if (value instanceof String)
	            ret.add((String) value);
	        else if (value instanceof Integer)
	            ret.add((Integer) value);
	        else if (value instanceof Long)
	            ret.add((Long) value);
	        else if (value instanceof Double)
	            ret.add((Double) value);
	        else if (value instanceof Boolean)
	            ret.add((Boolean) value);
	        else if (value instanceof JSONObject)
	            ret.add(convertJsonFormat((JSONObject) value));
	        else if (value instanceof JSONArray)
	            ret.add(convertJsonFormat((JSONArray) value));
	        else
	            throw new RuntimeException("not prepared for converting instance of class " + value.getClass());
	    }
	    return ret;
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