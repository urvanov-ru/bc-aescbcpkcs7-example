/**
 * 
 */
package ru.urvanov.javaexamples.bcexample;

/**
 * @author Fedor_Urvanov
 *
 */
public class IncorrectFormatException extends BcExampleException {

    /**
     * 
     */
    private static final long serialVersionUID = -2598923256325254221L;

    /**
     * 
     */
    public IncorrectFormatException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public IncorrectFormatException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public IncorrectFormatException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public IncorrectFormatException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public IncorrectFormatException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
