package domain;

/**
 * Represents a position on the game board in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Position {
    private int x;
    private int y;
    
    /**
     * Constructor
     * @param x Coordenada X
     * @param y Coordenada Y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Obtiene la coordenada X
     * @return Coordenada X
     */
    public int getX() {
        return x;
    }
    
    /**
     * Obtiene la coordenada Y
     * @return Coordenada Y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Establece la coordenada X
     * @param x Nueva coordenada X
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Establece la coordenada Y
     * @param y Nueva coordenada Y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Verifica si dos posiciones son iguales
     * @param other Otra posición a comparar
     * @return true si las posiciones son iguales, false en caso contrario
     */
    public boolean equals(Position other) {
        if (other == null) return false;
        return this.x == other.x && this.y == other.y;
    }
    
    /**
     * Calcula la distancia euclidiana a otra posición
     * @param other Otra posición
     * @return Distancia a la otra posición
     */
    public double distanceTo(Position other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
