package notes.main;

/**
 * Common exception class.
 */
public class NotesException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotesException(Exception e) {
		super(e);
	}
}
