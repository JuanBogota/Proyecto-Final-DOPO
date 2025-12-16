package domain;

/**
 * Clase que representa un obstáculo en el juego.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public abstract class Obstacle extends GameObject {

    /**
     * Constructor de la clase Obstacle.
     * @param position Posición del obstáculo en el tablero.
     */
    public Obstacle(Position position) {
        super(position);
    }
    
    @Override
    public boolean isSolid() {
        return true; //los obstáculos son sólidos
    }
    
    @Override
    public void update(Board board) {
        //los obstáculos no tienen comportamiento automático
    }
    
    /**
     * Indica si el obstáculo es destructible.
     * @return true si es destructible, false en caso contrario.
     */
    public abstract boolean isDestructible();
}

