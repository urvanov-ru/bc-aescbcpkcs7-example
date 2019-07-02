/**
 * 
 */
package ru.urvanov.javaexamples.bcexample;

/**
 * @author Fedor_Urvanov
 *
 */
public class BcExampleException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 352805250711328929L;

    /**
     * 
     */
    public BcExampleException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public BcExampleException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public BcExampleException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public BcExampleException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BcExampleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
