package net.rcarz.jiraclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parameter {
	public static final Parameter EXPAND_CHANGELOG = Parameter.of("expand", "changelog");

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

	public static Parameter get(Parameter[] parameters, String paramName, Parameter other) {
		return Stream.of(parameters)
				.filter(parameter -> paramName.equals(parameter.key))
				.findFirst()
				.orElse(other);
	}

	public static Parameter[] increment(Parameter[] parameters, String startAt, int i) {
		Parameter currentValue = Parameter.get(parameters, startAt, Parameter.of(startAt, "0"));
		return Parameter.replace(
				parameters,
				currentValue,
				Parameter.of(
						currentValue.getKey(),
						String.valueOf(Integer.parseInt(currentValue.getValue()) + i)
				)
		);
	}

	private static Parameter[] replace(Parameter[] parameters, Parameter currentValue, Parameter newValue) {
		 Stream<Parameter> parameterStream = Stream.concat(
		 		Stream.of(parameters)
						 .filter(p -> !Objects.equals(p, currentValue)),
				 Stream.of(newValue)
		 );
		 List<Parameter> parameterList = parameterStream.collect(Collectors.toList());
		 return parameterList.toArray(new Parameter[] {});
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
