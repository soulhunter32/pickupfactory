package exceptions;

public class StoreBlockException extends Exception {

	private static final long serialVersionUID = 1L;

	public StoreBlockException(final String errorMessage) {
		super(errorMessage);
	}
}
