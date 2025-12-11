package domain;

/**
 * Clase que representa al helado de fresa
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class StrawberryIceCream extends IceCream {
    
    /**
     * Constructor de la clase StrawberryIceCream.
     * @param position Posición inicial del helado en el tablero.
     */
    public StrawberryIceCream(Position position) {
        super(position, "STRAWBERRY");
    }
    
    @Override
    public String getType() {
        return "STRAWBERRY_ICECREAM";
    }
}
