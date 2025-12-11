package domain;

/**
 * Representa una posición en el tablero del juego
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Position {
    private final int x;
    private final int y;
    
    /**
     * Constructor de la posición con coordenadas x e y
     * @param x Coordenada x
     * @param y Coordenada y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Obtiene la coordenada x
     */
    public int getX() {
        return x;
    }
    
    /**
     * Obtiene la coordenada y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Calcula una nueva posición en una dirección específica
     */
    public Position move(Direction direction) {
        return new Position(x + direction.getDx(), y + direction.getDy());
    }
    
    /**
     * Calcula la distancia Manhattan a otra posición
     */
    public int distanceTo(Position other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }
    
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
