package domain;

/**
 * Class representing the Pineapple fruit in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Pineapple extends Fruit {
    
    private static final int PINEAPPLE_POINTS = 200;
    
    private IceCream lastIceCreamMoved;
    private int lastDirection;
    
    /**
     * Constructor
     * @param position Posición inicial de la piña
     * @param board Referencia al tablero
     */
    public Pineapple(Position position, Board board) {
        super(position, Fruit.PINA, PINEAPPLE_POINTS, board);
        this.lastDirection = Direction.DOWN;
        this.lastIceCreamMoved = null;
    }
    
    @Override
    public void update() {
        // La piña no se mueve automáticamente, 
    }
    
    /**
     * Mueve la piña cuando un helado se mueve
     * @param iceCream Helado que se movió
     * @param direction Dirección en la que se movió el helado
     */
    public void moveWithIceCream(IceCream iceCream, int direction) {
        if (isCollected) return;
        
        this.lastIceCreamMoved = iceCream;
        this.lastDirection = direction;
        
        Position newPos = calculatePositionInDirection(position, direction);
        
        if (canMoveTo(newPos)) {
            board.removeFruit(this.position);
            this.position = newPos;
            board.addFruit(this);
        }
    }
    
    /**
     * Verifica si la piña puede moverse a una posición
     * @param pos Posición a verificar
     * @return true si puede moverse
     */
    private boolean canMoveTo(Position pos) {
        if (!board.isValidPosition(pos)) {
            return false;
        }
        
        GameObject obj = board.getObjectAt(pos);
    
        if (obj == null) {
            return true;
        }

        if (obj instanceof IceBlock) {
            return false;
        }

        if (obj instanceof Enemy) {
            return false;
        }

        if (obj instanceof Fruit) {
            return false;
        }
        
        return false;
    }
    
    /**
     * Obtiene el último helado que causó el movimiento de la piña
     * @return Helado que movió la piña
     */
    public IceCream getLastIceCreamMoved() {
        return lastIceCreamMoved;
    }
    
    /**
     * Obtiene la última dirección de movimiento
     * @return Dirección
     */
    public int getLastDirection() {
        return lastDirection;
    }
}