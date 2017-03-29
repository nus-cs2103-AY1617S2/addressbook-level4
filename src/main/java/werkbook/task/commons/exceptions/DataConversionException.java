package werkbook.task.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataConversionException(Exception cause) {
        super(cause);
    }

}
