package domain;

/**
 * Clase abstracta base para todos los objetos del juego.
 * Mantiene la posición de un objeto en el tablero.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public abstract class GameObject {
    protected Position position;
    
    /**
     * Constructor de la clase GameObject.
     * @param position Posición inicial del objeto en el tablero.
     */
    public GameObject(Position position) {
        this.position = position;
    }
    
    /**
     * Obtiene la posición actual del objeto
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * Establece una nueva posición para el objeto
     * @param position Nueva posición del objeto
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    
    /**
     * Actualiza el estado del objeto en cada tick del juego
     * @param board El tablero actual
     */
    public abstract void update(Board board);
    
    /**
     * Verifica si este objeto bloquea el movimiento de otros
     */
    public abstract boolean isSolid();
    
    /**
     * Retorna el tipo de objeto para identificación visual
     */
    public abstract String getType();
}
