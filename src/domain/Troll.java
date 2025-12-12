package domain;

/**
 * Esta clase representa al Troll, enemigo que se mueve en línea recta y 
 * cambia de dirección al chocar.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Troll extends Enemy {
    
    /**
     * Constructor de la clase Troll.
     * @param position Posición inicial del troll en el tablero.
     */
    public Troll(Position position) {
        super(position, new LinearMovement());
    }
    
    @Override
    public String getType() {
        return "TROLL";
    }
}
