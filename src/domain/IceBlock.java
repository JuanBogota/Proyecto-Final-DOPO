package domain;

/**
 * Clase que representa el bloque de hielo, obstáculo sólido que puede 
 * ser creado o roto por los helados.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class IceBlock extends Obstacle {
    private boolean playerCreated; // true si fue creado por el jugador, false si es del escenario
    
    /**
     * Constructor de la clase IceBlock.
     * @param position Posición del bloque de hielo en el tablero.
     * @param playerCreated Indica si el bloque fue creado por el jugador.
     */
    public IceBlock(Position position, boolean playerCreated) {
        super(position);
        this.playerCreated = playerCreated;
    }
    
    /**
     * Verifica si el bloque fue creado por el jugador
     */
    public boolean isPlayerCreated() {
        return playerCreated;
    }
    
    @Override
    public String getType() {
        return "ICE_BLOCK";
    }

    @Override
    public boolean isDestructible() {
        return playerCreated; // Solo los bloques creados por el jugador son destructibles
    }
}
