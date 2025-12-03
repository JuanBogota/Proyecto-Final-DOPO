package domain;

/**
 * Exception class for handling errors specific to Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class BadDopoCreamException extends Exception {

    private static final String DEFAULT_MESSAGE = "Error en Bad Dopo Cream";
    
    /**
     * Constructor of BadDopoCreamException
     * @param message Message of the exception
     */
    public BadDopoCreamException(String message) {
        super(message);
    }
}
