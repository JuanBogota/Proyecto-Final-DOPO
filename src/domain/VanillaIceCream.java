package domain;

/**
 * Clase que representa al helado de vainilla.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class VanillaIceCream extends IceCream {
    
    /**
     * Constructor de la clase VanillaIceCream.
     * @param position Posición inicial del helado en el tablero.
     */
    public VanillaIceCream(Position position) {
        super(position, "VANILLA");
    }
    
    @Override
    public String getType() {
        return "VANILLA_ICECREAM";
    }
}
