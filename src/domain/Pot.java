package domain;

/**
 * Clase que representa a la maceta, enemigo que persigue al jugador pero 
 * no puede romper bloques.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Pot extends Enemy {
    
    /**
     * Constructor de la clase Pot.
     * @param position Posición inicial de la maceta en el tablero.
     */
    public Pot(Position position) {
        super(position, new ChaseMovement(false)); // falsooooooooo = no puede romper hielo
    }
    
    @Override
    public String getType() {
        return "POT";
    }
}
