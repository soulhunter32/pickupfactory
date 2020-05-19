package exceptions;

public class OvenCookException extends Exception {

	private static final long serialVersionUID = 1L;

	public OvenCookException(final String errorMessage) {
		super(errorMessage);
	}
}
