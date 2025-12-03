package domain;

/**
 * Abstract class representing a generic game object in the Bad Dopo Cream game.
 *
 * 
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public abstract class GameObject {
    protected Position position;
    protected boolean isActive;
    
    /**
     * Constructor
     * @param position Posición inicial del objeto
     */
    public GameObject(Position position) {
        this.position = position;
        this.isActive = true;
    }
    
    /**
     * Obtiene la posición actual del objeto
     * @return Posición del objeto
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * Establece una nueva posición para el objeto
     * @param pos Nueva posición
     */
    public void setPosition(Position pos) {
        this.position = pos;
    }
    
    /**
     * Verifica si el objeto está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Activa el objeto
     */
    public void activate() {
        this.isActive = true;
    }
    
    /**
     * Desactiva el objeto
     */
    public void deactivate() {
        this.isActive = false;
    }
    
    /**
     * Actualiza el estado del objeto (debe ser implementado por subclases)
     */
    public abstract void update();
}
