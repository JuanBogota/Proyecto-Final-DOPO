package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nivel del juego.
 * Gestiona la configuración, estado y oleadas de frutas de un nivel.
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
    private List<List<Fruit>> fruitWaves; // Todas las oleadas
    private int currentWaveIndex; // Índice de la oleada actual
    private List<Fruit> activeFruits; // Frutas activas en el tablero
    private int totalFruitsToCollect;
    
    /**
     * Constructor del nivel con parámetros especí­ficos.
     * @param levelNumber Número del nivel.
     * @param width Ancho del tablero.
     * @param height Altura del tablero.
     * @param timeLimit Lí­mite de tiempo en segundos.
     */
    public Level(int levelNumber, int width, int height, int timeLimit) {
        this.levelNumber = levelNumber;
        this.board = new Board(width, height);
        this.timeLimit = timeLimit;
        this.timeRemaining = timeLimit;
        this.completed = false;
        this.fruitWaves = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.activeFruits = new ArrayList<>();
        this.totalFruitsToCollect = 0;
    }
    
    /**
     * Inicializa el nivel con helados, frutas (por oleadas), enemigos y obstáculos
     */
    public void initialize(LevelConfiguration config) {
        // Agregar helados
        for (IceCream iceCream : config.getIceCreams()) {
            board.addObject(iceCream);
        }
        
        // Guardar todas las oleadas de frutas, cambios de la ultima sustentacion
        this.fruitWaves = config.getFruitWaves();
        
        // Calcular total de frutas a recolectar
        for (List<Fruit> wave : fruitWaves) {
            totalFruitsToCollect += wave.size();
        }
        
        // Activar la primera oleada
        activateNextWave();
        
        // Agregar enemigos
        for (Enemy enemy : config.getEnemies()) {
            board.addObject(enemy);
        }
        
        // Agregar obstáculos del nivel (estos NO se pueden romper)
        for (Obstacle obstacle : config.getObstacles()) {
            board.addObject(obstacle);
        }
    }
    
    /**
     * Activa la siguiente oleada de frutas
     */
    private void activateNextWave() {
        if (currentWaveIndex < fruitWaves.size()) {
            List<Fruit> nextWave = fruitWaves.get(currentWaveIndex);
            for (Fruit fruit : nextWave) {
                board.addObject(fruit);
                activeFruits.add(fruit);
            }
            currentWaveIndex++;
        }
    }
    
    /**
     * Verifica si la oleada actual está completa
     */
    private boolean isCurrentWaveComplete() {
        for (Fruit fruit : activeFruits) {
            if (!fruit.isCollected()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Actualiza el estado del nivel
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
        
        if (isCurrentWaveComplete() && currentWaveIndex < fruitWaves.size()) {
            activateNextWave();
        }
        
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
        int collectedFruits = getCollectedFruits();
        
        if (collectedFruits >= totalFruitsToCollect) {
            completed = true;
        }
    }
    
    /**
     * Decrementa el tiempo restante
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
        for (List<Fruit> wave : fruitWaves) {
            for (Fruit fruit : wave) {
                if (fruit.isCollected()) {
                    collected++;
                }
            }
        }
        return collected;
    }
    
    /**
     * Obtiene la cantidad total de frutas en el nivel
     */
    public int getTotalFruits() {
        return totalFruitsToCollect;
    }
    
    /**
     * Reinicia el nivel a su estado inicial
     */
    public void reset() {
        timeRemaining = timeLimit;
        completed = false;
        // Nota: Se necesitarí­a recrear el tablero con la configuración original
        // Esto se puede manejar desde la clase Game
    }
}
