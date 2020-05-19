package exceptions;

public class AssemblyLineBlockException extends Exception {

	private static final long serialVersionUID = 1L;

	public AssemblyLineBlockException(final String errorMessage) {
		super(errorMessage);
	}
}
