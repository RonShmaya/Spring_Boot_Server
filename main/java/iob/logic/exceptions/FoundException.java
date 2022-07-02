package iob.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND)
public class FoundException extends RuntimeException {

	private static final long serialVersionUID = -6937210878842411741L;

	public FoundException() {
	}

	public FoundException(String message) {
		super(message);
	}

	public FoundException(Throwable cause) {
		super(cause);
	}

	public FoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
