package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nivel del juego.
 * Gestiona la configuración y estado de un nivel.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Level {
    private final int levelNumber;
    private final Board board;
    private final int timeLimit; // en segundos
    private int timeRemaining;
    private boolean completed;
    private List<Fruit> totalFruits;
    private int fruitsToCollect;
    
    /**
     * Constructor del nivel con parámetros específicos.
     * @param levelNumber Número del nivel.
     * @param width Ancho del tablero.
     * @param height Altura del tablero.
     * @param timeLimit Límite de tiempo en segundos.
     */
    public Level(int levelNumber, int width, int height, int timeLimit) {
        this.levelNumber = levelNumber;
        this.board = new Board(width, height);
        this.timeLimit = timeLimit;
        this.timeRemaining = timeLimit;
        this.completed = false;
        this.totalFruits = new ArrayList<>();
        this.fruitsToCollect = 0;
    }
    
    /**
     * Inicializa el nivel con helados, frutas, enemigos y obstáculos
     */
    public void initialize(LevelConfiguration config) {
        
        for (IceCream iceCream : config.getIceCreams()) {
            board.addObject(iceCream);
        }
        
        for (Fruit fruit : config.getFruits()) {
            board.addObject(fruit);
            totalFruits.add(fruit);
        }
        fruitsToCollect = totalFruits.size();
        
        for (Enemy enemy : config.getEnemies()) {
            board.addObject(enemy);
        }
        
        for (IceBlock obstacle : config.getObstacles()) {
            board.addObject(obstacle);
        }
    }
    
    /**
     * Actualiza el estado del nivel (llamado en cada tick del juego)
     */
    public void update() {
        if (completed) {
            return;
        }
        
        List<GameObject> objects = board.getAllObjects();
        for (GameObject obj : objects) {
            obj.update(board);
        }
        
        checkCollectibles();
        
        checkLevelCompletion();
    }
    
    /**
     * Verifica y procesa la recolección de frutas
     */
    private void checkCollectibles() {
        for (IceCream iceCream : board.getIceCreams()) {
            if (!iceCream.isAlive()) {
                continue;
            }
            
            Position pos = iceCream.getPosition();
            List<GameObject> objects = board.getObjectsAt(pos);
            
            for (GameObject obj : objects) {
                if (obj instanceof Fruit fruit) {
                    if (fruit.isCollectable()) {
                        fruit.onCollect(iceCream);
                        board.removeObject(fruit);
                    }
                }
            }
        }
    }
    
    /**
     * Verifica si el nivel ha sido completado
     */
    private void checkLevelCompletion() {
        int collectedFruits = 0;
        for (Fruit fruit : totalFruits) {
            if (fruit.isCollected()) {
                collectedFruits++;
            }
        }
        
        if (collectedFruits >= fruitsToCollect) {
            completed = true;
        }
    }
    
    /**
     * Decrementa el tiempo restante (llamar cada segundo)
     */
    public void decrementTime() {
        if (timeRemaining > 0) {
            timeRemaining--;
        }
    }
    
    /**
     * Verifica si el tiempo se ha agotado
     */
    public boolean isTimeUp() {
        return timeRemaining <= 0;
    }
    
    /**
     * Indica si el nivel ha sido completado
     */
    public boolean isCompleted() {
        return completed;
    }
    
    /**
     * Indica si el jugador ha perdido
     */
    public boolean hasLost() {
        // Pierde si se acabó el tiempo o todos los helados están muertos
        boolean allDead = true;
        for (IceCream iceCream : board.getIceCreams()) {
            if (iceCream.isAlive()) {
                allDead = false;
                break;
            }
        }
        return isTimeUp() || allDead;
    }
    
    /**
     * Obtiene el número del nivel
     */
    public int getLevelNumber() {
        return levelNumber;
    }
    
    /**
     * Obtiene el tablero del nivel
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Obtiene el tiempo restante en segundos
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }
    
    /**
     * Obtiene la cantidad de frutas recolectadas
     */
    public int getCollectedFruits() {
        int collected = 0;
        for (Fruit fruit : totalFruits) {
            if (fruit.isCollected()) {
                collected++;
            }
        }
        return collected;
    }
    
    /**
     * Obtiene la cantidad total de frutas en el nivel
     */
    public int getTotalFruits() {
        return fruitsToCollect;
    }
    
    /**
     * Reinicia el nivel a su estado inicial
     */
    public void reset() {
        timeRemaining = timeLimit;
        completed = false;
        // Nota: Se necesitaría recrear el tablero con la configuración original
        // Esto se puede manejar desde la clase Game
    }
}
