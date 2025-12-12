package domain;

/**
 * Interfaz para objetos que tienen capacidad de movimiento.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public interface Movable {
    
    /**
     * Intenta mover el objeto en una dirección específica
     * @param direction La dirección del movimiento
     * @param board El tablero actual
     * @return true si el movimiento fue exitoso, false en caso contrario
     */
    boolean move(Direction direction, Board board);
    
    /**
     * Retorna la dirección actual hacia donde está mirando el objeto
     */
    Direction getFacingDirection();
    
    /**
     * Establece la dirección hacia donde está mirando el objeto
     */
    void setFacingDirection(Direction direction);
}
