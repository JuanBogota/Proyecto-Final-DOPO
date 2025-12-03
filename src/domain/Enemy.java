package domain;

/**
 * Abstract class representing an enemy in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public abstract class Enemy extends GameObject {
    // Constantes para tipos de enemigos
    public static final int TROLL = 1;
    public static final int MACETA = 2;
    public static final int CALAMAR_NARANJA = 3;
    public static final int NARVAL = 4;
    
    protected int type;
    protected int speed;
    protected boolean canBreakBlocks;
    protected int direction;
    protected Board board;
    
    /**
     * Constructor
     * @param position Posición inicial del enemigo
     * @param type Tipo de enemigo (TROLL, MACETA, CALAMAR_NARANJA, NARVAL)
     * @param board Referencia al tablero del juego
     */
    public Enemy(Position position, int type, Board board) {
        super(position);
        this.type = type;
        this.direction = Direction.DOWN;
        this.board = board;
    }
    
    /**
     * Obtiene el tipo de enemigo
     * @return Tipo
     */
    public int getType() {
        return type;
    }
    
    /**
     * Obtiene la velocidad del enemigo
     * @return Velocidad
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Verifica si el enemigo puede romper bloques
     * @return true si puede romper bloques, false en caso contrario
     */
    public boolean canBreakBlocks() {
        return canBreakBlocks;
    }
    
    /**
     * Obtiene la dirección actual del enemigo
     * @return Dirección
     */
    public int getDirection() {
        return direction;
    }
    
    /**
     * Establece el tablero de juego
     * @param board Tablero del juego
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    
    /**
     * Mueve el enemigo (implementado por subclases)
     */
    public abstract void move();
    
    /**
     * Calcula el siguiente movimiento hacia el objetivo
     * @param target Helado objetivo
     * @return Dirección del siguiente movimiento
     */
    public abstract int calculateNextMove(IceCream target);
    
    /**
     * Verifica si puede moverse a una posición
     * @param pos Posición a verificar
     * @return true si puede moverse, false en caso contrario
     */
    protected boolean canMoveTo(Position pos) {
        if (!board.isValidPosition(pos)) {
            return false;
        }
        GameObject obj = board.getObjectAt(pos);

        if (obj == null) {
            return true;
        }
        if (obj instanceof Fruit) {
            return true;
        }
        if (obj instanceof IceBlock) {
            return false;
        }
        if (obj instanceof Enemy) {
            return false;
        }
        
        return false;
    }
    
    /**
     * Calcula la posición en una dirección específica
     * @param pos Posición de origen
     * @param dir Dirección
     * @return Nueva posición
     */
    protected Position calculatePositionInDirection(Position pos, int dir) {
        int newX = pos.getX();
        int newY = pos.getY();
        
        switch (dir) {
            case Direction.UP -> newY -= 1;
            case Direction.DOWN -> newY += 1;
            case Direction.LEFT -> newX -= 1;
            case Direction.RIGHT -> newX += 1;
        }
        
        return new Position(newX, newY);
    }
    
    /**
     * Verifica si hay un helado en la posición especificada
     * @param pos Posición a verificar
     * @param iceCream Helado a buscar
     * @return true si el helado está en esa posición
     */
    protected boolean isIceCreamAt(Position pos, IceCream iceCream) {
        return iceCream.getPosition().equals(pos);
    }
    
    @Override
    public void update() {
        move();
    }
}