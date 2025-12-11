package domain;

import java.util.Random;

/**
 * Estrategia de movimiento lineal, se mueve en línea recta y cambia de dirección al chocar.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class LinearMovement implements MovementStrategy {
    private Random random;
    
    /**
     * Constructor de la estrategia de movimiento lineal
     */
    public LinearMovement() {
        this.random = new Random();
    }
    
    @Override
    public Direction calculateNextDirection(Position currentPosition, Direction currentDirection, Board board) {
        Position nextPosition = currentPosition.move(currentDirection);
        
        if (canMoveTo(nextPosition, board)) {
            return currentDirection;
        }
        
        Direction[] directions = Direction.values();
        Direction newDirection = currentDirection;
        
        for (int i = 0; i < 10; i++) {
            Direction randomDir = directions[random.nextInt(directions.length)];
            if (canMoveTo(currentPosition.move(randomDir), board)) {
                newDirection = randomDir;
                break;
            }
        }
        
        return newDirection;
    }
    
    @Override
    public boolean canMoveTo(Position position, Board board) {
        return board.isValidPosition(position) && !board.isSolidAt(position);
    }
}
