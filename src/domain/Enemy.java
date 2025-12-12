package domain;

/**
 * Clase abstracta para los enemigos del juego.
 * Gestiona el comportamiento común de todos los enemigos.
 * Patrón: Strategy - usa MovementStrategy para delegar el comportamiento de movimiento
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public abstract class Enemy extends GameObject implements Movable, Dangerous {
    protected Direction facingDirection;
    protected MovementStrategy movementStrategy;
    
    /**
     * Constructor de la clase Enemy.
     * @param position Posición inicial del enemigo en el tablero.
     * @param movementStrategy Estrategia de movimiento del enemigo.
     */
    public Enemy(Position position, MovementStrategy movementStrategy) {
        super(position);
        this.movementStrategy = movementStrategy;
        this.facingDirection = Direction.SOUTH;
    }
    
    @Override
    public void update(Board board) {

        Direction nextDirection = movementStrategy.calculateNextDirection(
            position, facingDirection, board
        );
        
        move(nextDirection, board);
        
        checkCollisions(board);
    }
    
    @Override
    public boolean move(Direction direction, Board board) {
        setFacingDirection(direction);
        Position newPosition = position.move(direction);
        
        if (movementStrategy.canMoveTo(newPosition, board)) {
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
    
    @Override
    public boolean isDangerous() {
        return true;
    }
    
    @Override
    public void onContact(IceCream iceCream) {
        iceCream.eliminate();
    }
    
    /**
     * Verifica si hay colisiones con helados en la posición actual
     */
    protected void checkCollisions(Board board) {
        for (GameObject obj : board.getObjectsAt(position)) {
            if (obj instanceof IceCream iceCream) {
                if (iceCream.isAlive()) {
                    onContact(iceCream);
                }
            }
        }
    }
    
    @Override
    public boolean isSolid() {
        return false;
    }
    
    /**
     * Permite cambiar la estrategia de movimiento en tiempo de ejecución
     */
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
}
