package g56020.atlg4.stibride.model.repository;

public class RepositoryException extends Exception {
    /**
     * Constructor for RepositoryException without details.
     */
    public RepositoryException() {
        super();
    }

    /**
     * Constructor for RepositoryException with details.
     *
     * @param msg message of the exception.
     */
    public RepositoryException(String msg) {
        super(msg);
    }

    /**
     * Constructor for RepositoryException which wraps the source exception.
     *
     * @param exception wrapped exception.
     */
    public RepositoryException(Exception exception) {
        super(exception);
    }

}
