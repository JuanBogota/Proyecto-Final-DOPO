package domain;

/**
 * Class representing the Narval enemy in the Bad Dopo Cream game.
 *
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Narval extends Enemy {
    private IceCream target;
    private boolean isCharging;
    private int chargeSpeed;
    private int patrolDirection;
    
    private static final int NORMAL_SPEED = 1;
    private static final int CHARGE_SPEED = 2;
    private static final int DETECTION_RANGE = 15;
    
    /**
     * Constructor
     * @param position Posición inicial del Narval
     * @param board Referencia al tablero
     */
    public Narval(Position position, Board board) {
        super(position, Enemy.NARVAL, board);
        this.speed = NORMAL_SPEED;
        this.canBreakBlocks = true;
        this.target = null;
        this.isCharging = false;
        this.chargeSpeed = CHARGE_SPEED;
        this.patrolDirection = getRandomDirection();
        this.direction = patrolDirection;
    }
    
    /**
     * Constructor con dirección de patrullaje inicial
     * @param position Posición inicial del Narval
     * @param board Referencia al tablero
     * @param initialDirection Dirección inicial de patrullaje
     */
    public Narval(Position position, Board board, int initialDirection) {
        super(position, Enemy.NARVAL, board);
        this.speed = NORMAL_SPEED;
        this.canBreakBlocks = true;
        this.target = null;
        this.isCharging = false;
        this.chargeSpeed = CHARGE_SPEED;
        this.patrolDirection = initialDirection;
        this.direction = patrolDirection;
    }
    
    /**
     * Establece el helado objetivo para detectar
     * @param iceCream Helado a detectar
     */
    public void setTarget(IceCream iceCream) {
        this.target = iceCream;
    }
    
    @Override
    public void move() {
        if (!isActive) return;
        
        if (!isCharging && shouldStartCharging()) {
            startCharging();
        }
        
        if (isCharging) {
            performCharge();
        } else {
            patrol();
        }
    }
    
    /**
     * Verifica si el narval debe comenzar a embestir
     * @return true si detecta un helado alineado
     */
    private boolean shouldStartCharging() {
        if (target == null || !target.isAlive()) {
            return false;
        }
        
        Position targetPos = target.getPosition();
        int dx = targetPos.getX() - position.getX();
        int dy = targetPos.getY() - position.getY();
        
        if (dy == 0 && Math.abs(dx) <= DETECTION_RANGE) {
            this.direction = dx > 0 ? Direction.RIGHT : Direction.LEFT;
            return true;
        }
        
        if (dx == 0 && Math.abs(dy) <= DETECTION_RANGE) {
            this.direction = dy > 0 ? Direction.DOWN : Direction.UP;
            return true;
        }
        
        return false;
    }
    
    /**
     * Inicia la embestida
     */
    private void startCharging() {
        this.isCharging = true;
        this.speed = CHARGE_SPEED;
    }
    
    /**
     * Realiza el movimiento de embestida
     */
    private void performCharge() {
        for (int i = 0; i < speed; i++) {
            Position nextPos = calculatePositionInDirection(position, direction);
            
            if (!board.isValidPosition(nextPos)) {
                stopCharging();
                return;
            }
            
            GameObject obj = board.getObjectAt(nextPos);
            
            if (obj == null || obj instanceof Fruit) {
                this.position = nextPos;
            } else if (obj instanceof IceBlock) {
                board.removeIceBlock((IceBlock)obj);
                this.position = nextPos;
            } else {
                stopCharging();
                return;
            }
        }
        
        if (!shouldStartCharging()) {
            stopCharging();
        }
    }
    
    /**
     * Detiene la embestida y vuelve al patrullaje
     */
    private void stopCharging() {
        this.isCharging = false;
        this.speed = NORMAL_SPEED;
        this.direction = patrolDirection;
    }
    
    /**
     * Movimiento de patrullaje normal
     */
    private void patrol() {
        Position nextPos = calculatePositionInDirection(position, patrolDirection);
        
        if (canMoveTo(nextPos)) {
            this.position = nextPos;
        } else {
            changePatrolDirection();
        }
    }
    
    /**
     * Cambia la dirección de patrullaje cuando choca con un obstáculo
     */
    private void changePatrolDirection() {
        int[] directions = {
            turnRight(patrolDirection),
            turnLeft(patrolDirection),
            turnAround(patrolDirection)
        };
        
        for (int newDir : directions) {
            Position testPos = calculatePositionInDirection(position, newDir);
            if (canMoveTo(testPos)) {
                this.patrolDirection = newDir;
                this.direction = newDir;
                return;
            }
        }
    }
    
    /**
     * Gira 90 grados a la derecha
     */
    private int turnRight(int currentDir) {
        return switch (currentDir) {
            case Direction.UP -> Direction.RIGHT;
            case Direction.RIGHT -> Direction.DOWN;
            case Direction.DOWN -> Direction.LEFT;
            case Direction.LEFT -> Direction.UP;
            default -> Direction.DOWN;
        };
    }
    
    /**
     * Gira 90 grados a la izquierda
     */
    private int turnLeft(int currentDir) {
        return switch (currentDir) {
            case Direction.UP -> Direction.LEFT;
            case Direction.LEFT -> Direction.DOWN;
            case Direction.DOWN -> Direction.RIGHT;
            case Direction.RIGHT -> Direction.UP;
            default -> Direction.DOWN;
        };
    }
    
    /**
     * Da media vuelta
     */
    private int turnAround(int currentDir) {
        return switch (currentDir) {
            case Direction.UP -> Direction.DOWN;
            case Direction.DOWN -> Direction.UP;
            case Direction.LEFT -> Direction.RIGHT;
            case Direction.RIGHT -> Direction.LEFT;
            default -> Direction.DOWN;
        };
    }
    
    /**
     * Obtiene una dirección aleatoria
     */
    private int getRandomDirection() {
        int[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        return directions[(int)(Math.random() * directions.length)];
    }
    
    @Override
    public int calculateNextMove(IceCream target) {
        return this.direction;
    }
    
    /**
     * Verifica si el narval está embistiendo
     * @return true si está en modo embestida
     */
    public boolean isCharging() {
        return isCharging;
    }
}