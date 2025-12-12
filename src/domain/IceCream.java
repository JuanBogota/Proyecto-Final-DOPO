package domain;

/**
 * Clase abstracta para los helados del juego.
 * Gestiona el comportamiento común de todos los helados.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public abstract class IceCream extends GameObject implements Movable {
    protected Direction facingDirection;
    protected boolean alive;
    protected int score;
    protected String color;
    
    /**
     * Constructor de la clase IceCream.
     * @param position Posición inicial del helado en el tablero.
     * @param color Color del helado.
     */
    public IceCream(Position position, String color) {
        super(position);
        this.color = color;
        this.facingDirection = Direction.SOUTH;
        this.alive = true;
        this.score = 0;
    }
    
    @Override
    public boolean move(Direction direction, Board board) {
        setFacingDirection(direction);
        Position newPosition = position.move(direction);
        
        if (board.isValidPosition(newPosition) && !board.isSolidAt(newPosition)) {
            board.moveObject(this, newPosition);
            return true;
        }
        
        return false;
    }
    
    @Override
    public Direction getFacingDirection() {
        return facingDirection;
    }
    
    @Override
    public void setFacingDirection(Direction direction) {
        this.facingDirection = direction;
    }
    
    /**
     * Agrega puntos al puntaje del helado
     */
    public void addScore(int points) {
        this.score += points;
    }
    
    /**
     * Obtiene el puntaje actual del helado
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Verifica si el helado está vivo
     */
    public boolean isAlive() {
        return alive;
    }
    
    /**
     * Elimina el helado del juego
     */
    public void eliminate() {
        this.alive = false;
    }
    
    /**
     * Obtiene el color del helado
     */
    public String getColor() {
        return color;
    }
    
    @Override
    public void update(Board board) {
        // El helado no tiene actualización automática en cada tick
        // Sus acciones son controladas por el jugador o máquina
    }
    
    @Override
    public boolean isSolid() {
        return false; // Los helados no bloquean el movimiento
    }
}
