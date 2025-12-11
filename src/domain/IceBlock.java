package domain;

/**
 * Clase que representa el bloque de hielo, obstáculo sólido que puede 
 * ser creado o roto por los helados.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class IceBlock extends GameObject {
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
    public void update(Board board) {
        // Los bloques de hielo normales no tienen comportamiento automático
    }
    
    @Override
    public boolean isSolid() {
        return true;
    }
    
    @Override
    public String getType() {
        return "ICE_BLOCK";
    }
}
