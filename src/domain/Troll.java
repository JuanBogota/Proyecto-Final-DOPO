package domain;

/**
 * Class representing the Troll enemy in the Bad Dopo Cream game.
 *
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 2.0
 */
public class Troll extends Enemy {
    
    /**
     * Constructor
     * @param position Posición inicial del Troll
     * @param board Referencia al tablero
     */
    public Troll(Position position, Board board) {
        super(position, Enemy.TROLL, board);
        this.speed = 1;
        this.canBreakBlocks = false;
        this.direction = getRandomDirection();
    }
    
    /**
     * Constructor con dirección inicial específica
     * @param position Posición inicial del Troll
     * @param board Referencia al tablero
     * @param initialDirection Dirección inicial
     */
    public Troll(Position position, Board board, int initialDirection) {
        super(position, Enemy.TROLL, board);
        this.speed = 1;
        this.canBreakBlocks = false;
        this.direction = initialDirection;
    }
    
    @Override
    public void move() {
        if (!isActive) return;
        Position nextPos = calculatePositionInDirection(position, direction);
        if (canMoveTo(nextPos)) {
            this.position = nextPos;
        } else {
            changeDirection();
        }
    }
    
    /**
     * Cambia la dirección del Troll cuando choca con un obstáculo.
     * Intenta girar en orden: derecha, izquierda, atrás
     */
    private void changeDirection() {
        int[] directions = {
            turnRight(direction),
            turnLeft(direction),
            turnAround(direction)
        };
        for (int newDir : directions) {
            Position testPos = calculatePositionInDirection(position, newDir);
            if (canMoveTo(testPos)) {
                this.direction = newDir;
                return;
            }
        }
    }
    
    /**
     * Gira 90 grados a la derecha
     * @param currentDir Dirección actual
     * @return Nueva dirección
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
     * @param currentDir Dirección actual
     * @return Nueva dirección
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
     * Da media vuelta (180 grados)
     * @param currentDir Dirección actual
     * @return Dirección opuesta
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
     * @return Dirección aleatoria
     */
    private int getRandomDirection() {
        int[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        int randomIndex = (int)(Math.random() * directions.length);
        return directions[randomIndex];
    }
    
    /**
     * Retorna el próximo movimiento del Troll (no persigue al helado)
     * @param target Helado objetivo (ignorado por el Troll)
     * @return Dirección del siguiente movimiento
     */
    @Override
    public int calculateNextMove(IceCream target) {
        // Los Trolls NO persiguen al helado, solo siguen su patrón
        return this.direction;
    }
}