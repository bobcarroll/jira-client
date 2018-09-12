package net.rcarz.jiraclient;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
	private String key;
	private String value;

	public Parameter() {
	}

	private Parameter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static Parameter of(String key, String value) {
		return new Parameter(key, value);
	}

	public static Map<String, String> map(Parameter... parameters) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		for(Parameter p : parameters) {
			parameterMap.put(p.getKey(), p.getValue());
		}
		return parameterMap;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
