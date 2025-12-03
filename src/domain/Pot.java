package domain;

/**
 * Class representing the Pot enemy in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 2.0
 */
public class Pot extends Enemy {

    private IceCream target;
    
    /**
     * Constructor
     * @param position Posición inicial de la Maceta
     * @param board Referencia al tablero
     */
    public Pot(Position position, Board board) {
        super(position, Enemy.MACETA, board);
        this.speed = 1;
        this.canBreakBlocks = false;
        this.target = null;
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
        int nextDirection = calculateNextMove(target);
        this.direction = nextDirection;
        
        Position nextPos = calculatePositionInDirection(position, direction);
 
        if (canMoveTo(nextPos)) {
            this.position = nextPos;
        } else {
            tryAlternativeMove();
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
     * Calcula la distancia Manhattan al helado objetivo
     * @return Distancia al helado
     */
    public int getDistanceToTarget() {
        if (target == null) return Integer.MAX_VALUE;
        
        Position targetPos = target.getPosition();
        int dx = Math.abs(targetPos.getX() - this.position.getX());
        int dy = Math.abs(targetPos.getY() - this.position.getY());
        
        return dx + dy;
    }
}