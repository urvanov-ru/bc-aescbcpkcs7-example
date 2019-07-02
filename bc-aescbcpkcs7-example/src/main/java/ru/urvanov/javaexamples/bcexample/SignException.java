/**
 * 
 */
package ru.urvanov.javaexamples.bcexample;

/**
 * @author Fedor_Urvanov
 *
 */
public class SignException extends BcExampleException {

    /**
     * 
     */
    private static final long serialVersionUID = -5495314472985442590L;

    /**
     * 
     */
    public SignException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public SignException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public SignException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public SignException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
