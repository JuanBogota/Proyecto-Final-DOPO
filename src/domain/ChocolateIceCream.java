package domain;

/**
 * Clase que representa el helado de chocolate.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class ChocolateIceCream extends IceCream {
    
    /**
     * Constructor de la clase ChocolateIceCream.
     * @param position Posición inicial del helado en el tablero.
     */
    public ChocolateIceCream(Position position) {
        super(position, "CHOCOLATE");
    }
    
    @Override
    public String getType() {
        return "CHOCOLATE_ICECREAM";
    }
}
