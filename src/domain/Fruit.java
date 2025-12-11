package domain;

/**
 * Clase abstracta para las frutas del juego.
 * Gestiona el comportamiento común de todas las frutas.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public abstract class Fruit extends GameObject implements Collectible {
    protected int points;
    protected boolean collected;
    
    /**
     * Constructor de la clase Fruit.
     * @param position Posición inicial de la fruta en el tablero.
     * @param points Puntos que otorga al ser recolectada.
     */
    public Fruit(Position position, int points) {
        super(position);
        this.points = points;
        this.collected = false;
    }
    
    @Override
    public int getPoints() {
        return points;
    }
    
    @Override
    public boolean isCollectable() {
        return !collected;
    }
    
    @Override
    public void onCollect(IceCream collector) {
        collected = true;
        collector.addScore(points);
    }
    
    /**
     * Indica si la fruta ha sido recolectada
     * @return true si la fruta ha sido recolectada, false en caso contrario
     */
    public boolean isCollected() {
        return collected;
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    /**
     * Verifica si la fruta puede ser recolectada en su estado actual
     * Algunas frutas como el cactus tienen estados especiales
     */
    protected abstract boolean canBeCollectedNow();
}
