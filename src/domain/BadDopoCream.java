package domain;

/**
 * Clase principal que gestiona el juego.
 * Coordina el flujo del juego y sus estados.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class BadDopoCream {
    private Level currentLevel;
    private GameState state;
    private int currentLevelNumber;
    private long lastUpdateTime;
    private long lastSecondTime;
    
    /**
     * Constructor de la clase BadDopoCream.
     */
    public BadDopoCream() {
        this.state = GameState.NOT_STARTED;
        this.currentLevelNumber = 1;
        this.lastUpdateTime = 0;
        this.lastSecondTime = 0;
    }
    
    /**
     * Inicia el juego con una configuración de nivel
     */
    public void startGame(LevelConfiguration config) {
        currentLevel = new Level(currentLevelNumber, 25, 15, 180); // 25x15, 3 minutos
        currentLevel.initialize(config);
        state = GameState.PLAYING;
        lastUpdateTime = System.currentTimeMillis();
        lastSecondTime = System.currentTimeMillis();
    }
    
    /**
     * Actualiza el estado del juego
     */
    public void update() {
        if (state != GameState.PLAYING) {
            return;
        }
        
        long currentTime = System.currentTimeMillis();
        
        // Actualiza el nivel cada cierto intervalo (100ms)
        if (currentTime - lastUpdateTime >= 100) {
            currentLevel.update();
            lastUpdateTime = currentTime;
        }
        
        // Decrementa el tiempo cada segundo
        if (currentTime - lastSecondTime >= 1000) {
            currentLevel.decrementTime();
            lastSecondTime = currentTime;
        }
        
        // Verifica condiciones de victoria/derrota
        checkGameState();
    }
    
    /**
     * Verifica si el juego ha terminado
     */
    private void checkGameState() {
        if (currentLevel.isCompleted()) {
            state = GameState.LEVEL_COMPLETED;
        } else if (currentLevel.hasLost()) {
            state = GameState.GAME_OVER;
        }
    }
    
    /**
     * Mueve un helado en una dirección específica
     */
    public boolean moveIceCream(IceCream iceCream, Direction direction) {
        if (state != GameState.PLAYING || !iceCream.isAlive()) {
            return false;
        }
        return iceCream.move(direction, currentLevel.getBoard());
    }
    
    /**
     * Crea bloques de hielo con un helado específico
     */
    public void createIceBlocks(IceCream iceCream) {
        if (state == GameState.PLAYING && iceCream.isAlive()) {
            iceCream.createIceBlocks(currentLevel.getBoard());
        }
    }
    
    /**
     * Rompe bloques de hielo con un helado específico
     */
    public void breakIceBlocks(IceCream iceCream) {
        if (state == GameState.PLAYING && iceCream.isAlive()) {
            iceCream.breakIceBlocks(currentLevel.getBoard());
        }
    }
    
    /**
     * Pausa el juego
     */
    public void pause() {
        if (state == GameState.PLAYING) {
            state = GameState.PAUSED;
        }
    }
    
    /**
     * Reanuda el juego
     */
    public void resume() {
        if (state == GameState.PAUSED) {
            state = GameState.PLAYING;
            lastUpdateTime = System.currentTimeMillis();
            lastSecondTime = System.currentTimeMillis();
        }
    }
    
    /**
     * Reinicia el nivel actual
     */
    public void restartLevel(LevelConfiguration config) {
        startGame(config);
    }
    
    /**
     * Termina el juego
     */
    public void endGame() {
        state = GameState.GAME_OVER;
    }
    
    /**
     * Obtiene el nivel actual
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Obtiene el estado actual del juego
     */
    public GameState getState() {
        return state;
    }
    
    /**
     * Obtiene el número del nivel actual
     */
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }
    
    /**
     * Calcula el puntaje total de todos los helados
     */
    public int getTotalScore() {
        int total = 0;
        if (currentLevel != null) {
            for (IceCream iceCream : currentLevel.getBoard().getIceCreams()) {
                total += iceCream.getScore();
            }
        }
        return total;
    }
}
