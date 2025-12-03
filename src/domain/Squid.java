package domain;

/**
 * Class representing the Squid (Calamar Naranja) enemy in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 2.0
 */
public class Squid extends Enemy {
    private IceCream target;
    private boolean isBreakingBlock;
    private int breakingTimer;
    private static final int BREAK_TIME = 2;
    
    /**
     * Constructor
     * @param position Posición inicial del Calamar Naranja
     * @param board Referencia al tablero
     */
    public Squid(Position position, Board board) {
        super(position, Enemy.CALAMAR_NARANJA, board);
        this.speed = 1;
        this.canBreakBlocks = true;
        this.target = null;
        this.isBreakingBlock = false;
        this.breakingTimer = 0;
    }
    
    /**
     * Establece el helado objetivo
     * @param iceCream Helado a perseguir
     */
    public void setTarget(IceCream iceCream) {
        this.target = iceCream;
    }
    
    @Override
    public void move() {
        if (!isActive || target == null || !target.isAlive()) {
            return;
        }
        if (isBreakingBlock) {
            continueBreakingBlock();
            return;
        }
        int nextDirection = calculateNextMove(target);
        this.direction = nextDirection;
    
        Position nextPos = calculatePositionInDirection(position, direction);
    
        if (!board.isValidPosition(nextPos)) {
            tryAlternativeMove();
            return;
        }
        
        GameObject obj = board.getObjectAt(nextPos);
        
        if (obj == null || obj instanceof Fruit) {
            this.position = nextPos;
        } else if (obj instanceof IceBlock) {
            startBreakingBlock(nextPos);
        } else {
            tryAlternativeMove();
        }
    }
    
    /**
     * Comienza el proceso de romper un bloque de hielo
     * @param blockPos Posición del bloque a romper
     */
    private void startBreakingBlock(Position blockPos) {
        this.isBreakingBlock = true;
        this.breakingTimer = BREAK_TIME;
    }
    
    /**
     * Continúa el proceso de romper un bloque
     */
    private void continueBreakingBlock() {
        breakingTimer--;
        
        if (breakingTimer <= 0) {
            Position nextPos = calculatePositionInDirection(position, direction);
            
            if (board.isValidPosition(nextPos)) {
                GameObject obj = board.getObjectAt(nextPos);
                
                if (obj instanceof IceBlock) {
                    board.removeIceBlock((IceBlock) obj);
                    this.position = nextPos;
                }
            }
        
            this.isBreakingBlock = false;
            this.breakingTimer = 0;
        }
    }
    
    /**
     * Intenta un movimiento alternativo cuando el camino directo está bloqueado
     */
    private void tryAlternativeMove() {
        int[] alternativeDirections = getAlternativeDirections(direction);
        
        for (int altDir : alternativeDirections) {
            Position altPos = calculatePositionInDirection(position, altDir);
            if (canMoveTo(altPos)) {
                this.direction = altDir;
                this.position = altPos;
                return;
            }
        }
    
    }
    
    /**
     * Obtiene direcciones alternativas (las otras 3 direcciones)
     * @param currentDir Dirección actual
     * @return Array con las 3 direcciones alternativas
     */
    private int[] getAlternativeDirections(int currentDir) {
        return switch (currentDir) {
            case Direction.UP -> new int[]{Direction.LEFT, Direction.RIGHT, Direction.DOWN};
            case Direction.DOWN -> new int[]{Direction.LEFT, Direction.RIGHT, Direction.UP};
            case Direction.LEFT -> new int[]{Direction.UP, Direction.DOWN, Direction.RIGHT};
            case Direction.RIGHT -> new int[]{Direction.UP, Direction.DOWN, Direction.LEFT};
            default -> new int[]{Direction.UP, Direction.DOWN, Direction.LEFT};
        };
    }
    
    @Override
    public int calculateNextMove(IceCream target) {
        if (target == null) {
            return this.direction;
        }
        
        Position targetPos = target.getPosition();
        int dx = targetPos.getX() - this.position.getX();
        int dy = targetPos.getY() - this.position.getY();
        
        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else if (Math.abs(dy) > 0) {
            return dy > 0 ? Direction.DOWN : Direction.UP;
        } else {
            return this.direction;
        }
    }
    
    /**
     * Verifica si el calamar está actualmente rompiendo un bloque
     * @return true si está rompiendo un bloque
     */
    public boolean isBreakingBlock() {
        return isBreakingBlock;
    }
    
    /**
     * Obtiene el tiempo restante para romper el bloque
     * @return Turnos restantes
     */
    public int getBreakingTimer() {
        return breakingTimer;
    }
}