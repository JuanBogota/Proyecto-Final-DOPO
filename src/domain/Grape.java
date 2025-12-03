package domain;

/**
 * Class representing the Grape fruit in the Bad Dopo Cream game.
 *
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Grape extends Fruit {
    
    private static final int GRAPE_POINTS = 50;
    
    /**
     * Constructor
     * @param position Posición del racimo de uvas
     * @param board Referencia al tablero
     */
    public Grape(Position position, Board board) {
        super(position, Fruit.UVAS, GRAPE_POINTS, board);
    }
    
    @Override
    public void update() {
        // Las uvas son completamente estáticas, no hacen nada
    }
}