package domain;

/**
 * Excepción personalizada para el juego Bad DOPO Cream.
 * Maneja excepciones específicas del dominio del juego.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0 
 */
public class BadDopoCreamException extends Exception {
    
	public static final String ERROR = "Ocurrio un error en el juego";
    public static final String INVALID_MOVE = "Movimiento inválido";
    public static final String LEVEL_NOT_FOUND = "Nivel no encontrado";
    public static final String GAME_NOT_STARTED = "El juego no ha comenzado";
    public static final String PLAYER_NOT_FOUND = "Jugador no encontrado";
    public static final String ENEMY_NOT_FOUND = "Enemigo no encontrado";
	
    /**
     * Constructor de la excepción con un mensaje específico.
     * @param message Mensaje de error detallado.
     */
    public BadDopoCreamException(String message) {
        super(message);
    }
 
}
