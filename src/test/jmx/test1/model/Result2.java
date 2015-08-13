package test.jmx.test1.model;

import java.io.Serializable;

public class Result2 implements Serializable {

	private static final long serialVersionUID = -7178695597615362121L;
	private int code;
	private String[] body;

	// getter

	public int getCode() {
		return code;
	}

	public String[] getBody() {
		return body;
	}

	public static Result2 valueOf(int code, String[] body) {
		Result2 result = new Result2();
		result.code = code;
		result.body = body;
		return result;
	}

}
