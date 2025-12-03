package domain;

/**
 * Abstract class representing a Fruit in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 2.0
 */
public abstract class Fruit extends GameObject {
    // Constantes para tipos de frutas
    public static final int UVAS = 1;
    public static final int PLATANO = 2;
    public static final int PINA = 3;
    public static final int CEREZA = 4;
    public static final int CACTUS = 5;
    
    protected int type;
    protected int points;
    protected boolean isCollected;
    protected Board board;
    
    /**
     * Constructor
     * @param position Posición inicial de la fruta
     * @param type Tipo de fruta (UVAS, PLATANO, PINA, CEREZA, CACTUS)
     * @param points Puntos que otorga la fruta
     * @param board Referencia al tablero del juego
     */
    public Fruit(Position position, int type, int points, Board board) {
        super(position);
        this.type = type;
        this.points = points;
        this.isCollected = false;
        this.board = board;
    }
    
    /**
     * Obtiene el tipo de fruta
     * @return Tipo
     */
    public int getType() {
        return type;
    }
    
    /**
     * Obtiene los puntos que otorga la fruta
     * @return Puntos de la fruta
     */
    public int getPoints() {
        return points;
    }
    
    /**
     * Verifica si la fruta ha sido recolectada
     * @return true si fue recolectada, false en caso contrario
     */
    public boolean isCollected() {
        return isCollected;
    }
    
    /**
     * Verifica si la fruta es peligrosa (puede dañar al helado)
     * Por defecto es false, solo el Cactus con púas es peligroso
     * @return true si es peligrosa
     */
    public boolean isDangerous() {
        return false;
    }
    
    /**
     * Marca la fruta como recolectada
     */
    public void collect() {
        this.isCollected = true;
        this.deactivate();
    }
    
    /**
     * Verifica si una posición está libre (sin objetos sólidos)
     * @param pos Posición a verificar
     * @return true si está libre
     */
    protected boolean isPositionFree(Position pos) {
        if (!board.isValidPosition(pos)) {
            return false;
        }
        
        GameObject obj = board.getObjectAt(pos);
        return obj == null;
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
     * Actualiza el estado de la fruta (implementado por subclases)
     */
    @Override
    public abstract void update();
}