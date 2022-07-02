package iob.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ActionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4135341446057363926L;

	public ActionNotFoundException() {
	}

	public ActionNotFoundException(String message) {
		super(message);
	}

	public ActionNotFoundException(Throwable cause) {
		super(cause);
	}

	public ActionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
