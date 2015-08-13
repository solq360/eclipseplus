package test.jmx.test1.model;

import java.beans.ConstructorProperties;

public class Result implements IResult {
	private int code;
	private String[] body;

	public Result() {
	}

	@ConstructorProperties({ "code", "body" })
	public Result(int code, String[] body) {
		this.code = code;
		this.body = body;
	}

	// getter

	public int getCode() {
		return code;
	}

	public String[] getBody() {
		return body;
	}

	public static Result valueOf(int code, String[] body) {
		Result result = new Result();
		result.code = code;
		result.body = body;
		return result;
	}

}
