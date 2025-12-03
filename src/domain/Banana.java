package domain;

/**
 * Class representing the Banana (Plátano) fruit in the Bad Dopo Cream game.
 *
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 2.0
 */
public class Banana extends Fruit {
    
    private static final int BANANA_POINTS = 100;
    
    /**
     * Constructor
     * @param position Posición del plátano
     * @param board Referencia al tablero
     */
    public Banana(Position position, Board board) {
        super(position, Fruit.PLATANO, BANANA_POINTS, board);
    }
    
    @Override
    public void update() {
        // Los plátanos son completamente estáticos, no hacen nada
    }
}