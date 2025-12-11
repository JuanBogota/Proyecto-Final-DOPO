package domain;

/**
 * Estrategia de movimiento para diferentes tipos de objetos móviles.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public interface MovementStrategy {

    /**
     * Calcula la siguiente dirección de movimiento
     * @param currentPosition La posición actual del objeto
     * @param currentDirection La dirección actual del objeto
     * @param board El tablero actual
     * @return La dirección en la que el objeto debería moverse
     */
    Direction calculateNextDirection(Position currentPosition, Direction currentDirection, Board board);
    
    /**
     * Determina si el objeto puede moverse a una posición específica
     * @param position La posición a verificar
     * @param board El tablero actual
     * @return true si el movimiento es válido
     */
    boolean canMoveTo(Position position, Board board);
}
