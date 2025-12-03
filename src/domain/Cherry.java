package domain;

/**
 * Class representing the Cherry (Cereza) fruit in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 2.0
 */
public class Cherry extends Fruit {
    
    private static final int CHERRY_POINTS = 150;
    private static final int TELEPORT_INTERVAL = 20 * 60;
    
    private int updateCounter;
    
    /**
     * Constructor
     * @param position Posición inicial de la cereza
     * @param board Referencia al tablero
     */
    public Cherry(Position position, Board board) {
        super(position, Fruit.CEREZA, CHERRY_POINTS, board);
        this.updateCounter = 0;
    }
    
    /**
     * Constructor con intervalo de teletransporte personalizado
     * @param position Posición inicial de la cereza
     * @param board Referencia al tablero
     * @param updatesPerSecond Updates por segundo del juego (para calcular el intervalo)
     */
    public Cherry(Position position, Board board, int updatesPerSecond) {
        super(position, Fruit.CEREZA, CHERRY_POINTS, board);
        this.updateCounter = 0;
    }
    
    @Override
    public void update() {
        if (isCollected) return;

        updateCounter++;

        // Cada 20 segundos se tepea
        if (updateCounter >= TELEPORT_INTERVAL) {
            teleport();
            updateCounter = 0;
        }
    }
    
    /**
     * Teletransporta la cereza a una posición aleatoria válida y libre
     */
    public void teleport() {
        Position newPos = findRandomFreePosition();
        
        if (newPos != null) {
    
            board.removeFruit(this.position);
            this.position = newPos;
            board.addFruit(this);
        }
    }
    
    /**
     * Busca una posición libre aleatoria en el tablero
     * @return Posición libre o null si no encuentra ninguna
     */
    private Position findRandomFreePosition() {
        int maxAttempts = 100;
        int attempts = 0;
        
        while (attempts < maxAttempts) {
            int x = (int)(Math.random() * board.getWidth());
            int y = (int)(Math.random() * board.getHeight());
            Position testPos = new Position(x, y);
            
            if (isPositionFree(testPos)) {
                return testPos;
            }
            
            attempts++;
        }
        
        return null;
    }
    
    /**
     * Fuerza un teletransporte inmediato (útil para pruebas)
     */
    public void forceTeleport() {
        teleport();
        updateCounter = 0;
    }
    
    /**
     * Obtiene el contador actual de updates
     * @return Contador de updates
     */
    public int getUpdateCounter() {
        return updateCounter;
    }
    
    /**
     * Obtiene el tiempo restante hasta el próximo teletransporte
     * @return Updates restantes
     */
    public int getTimeUntilTeleport() {
        return TELEPORT_INTERVAL - updateCounter;
    }
}