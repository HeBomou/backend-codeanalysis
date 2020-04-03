package local.happysixplus.backendcodeanalysis.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MyRuntimeException(String message) {
		super(message);
	}
}