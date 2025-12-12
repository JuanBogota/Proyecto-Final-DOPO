package domain;

/**
 * Estrategia de movimiento de persecución: persigue al helado más cercano.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class ChaseMovement implements MovementStrategy {
    private final boolean canBreakIce;
    
    /**
     * Constructor de la estrategia de movimiento de persecución.
     * @param canBreakIce Indica si el objeto puede romper bloques de hielo.
     */
    public ChaseMovement(boolean canBreakIce) {
        this.canBreakIce = canBreakIce;
    }
    
    @Override
    public Direction calculateNextDirection(Position currentPosition, Direction currentDirection, Board board) {
        Position targetPosition = board.getNearestIceCreamPosition(currentPosition);
        
        if (targetPosition == null) {
            return currentDirection; // No hay helado, mantiene dirección
        }
        
        Direction bestDirection = getBestDirectionTowards(currentPosition, targetPosition, board);
        
        return bestDirection != null ? bestDirection : currentDirection;
    }
    
    /**
     * Obtiene la mejor dirección para moverse hacia una posición objetivo.
     * @param from Posición actual.
     * @param to Posición objetivo.
     * @param board El tablero actual.
     * @return La mejor dirección para moverse hacia el objetivo.
     */
    private Direction getBestDirectionTowards(Position from, Position to, Board board) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        
        Direction horizontalDir = dx > 0 ? Direction.EAST : Direction.WEST;
        Direction verticalDir = dy > 0 ? Direction.SOUTH : Direction.NORTH;
        
        Direction primaryDir = Math.abs(dx) > Math.abs(dy) ? horizontalDir : verticalDir;
        Direction secondaryDir = Math.abs(dx) > Math.abs(dy) ? verticalDir : horizontalDir;
        
        if (canMoveTo(from.move(primaryDir), board)) {
            return primaryDir;
        }
        
        if (canMoveTo(from.move(secondaryDir), board)) {
            return secondaryDir;
        }
        
        for (Direction dir : Direction.values()) {
            if (canMoveTo(from.move(dir), board)) {
                return dir;
            }
        }
        
        return null;
    }
    
    @Override
    public boolean canMoveTo(Position position, Board board) {
        if (!board.isValidPosition(position)) {
            return false;
        }
        
        if (canBreakIce && board.hasIceBlockAt(position)) {
            return true;
        }
        
        return !board.isSolidAt(position);
    }
}
