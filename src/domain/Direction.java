package domain;

/**
 * Representa las cuatro direcciones básicas de movimiento
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public enum Direction {
    NORTH(0, -1),
    SOUTH(0, 1),
    EAST(1, 0),
    WEST(-1, 0);
    
    private final int dx;
    private final int dy;
    
    /**
     * Constructor de la dirección con sus desplazamientos en x e y
     * @param dx Desplazamiento en x
     * @param dy Desplazamiento en y
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Obtiene el desplazamiento en x
     */
    public int getDx() {
        return dx;
    }
    
    /**
     * Obtiene el desplazamiento en y
     */
    public int getDy() {
        return dy;
    }
    
    /**
     * Retorna la dirección opuesta
     */
    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            default -> this;
        };
    }
}
