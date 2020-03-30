package local.happysixplus.backendcodeanalysis.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	protected String code;

	protected String msg;

	protected String message;// 打印出的日志信息

	public MyRuntimeException(ResponseEnums enums, String message) {
		super();
		this.code = enums.getCode();
		this.msg = enums.getMsg();
		this.message = message;
	}

	public MyRuntimeException(ResponseEnums enums) {
		super();
		this.code = enums.getCode();
		this.msg = enums.getMsg();
	}

	public MyRuntimeException() {
		super();
	}

	public MyRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyRuntimeException(String message) {
		super(message);
	}
}