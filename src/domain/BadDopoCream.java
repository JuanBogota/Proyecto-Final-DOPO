package domain;

/**
 * Class representing the main game logic for BadDopoCream.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class BadDopoCream{
    // Constantes para modos de juego
    public static final int PLAYER_VS_PLAYER = 1;
    public static final int PLAYER_VS_MACHINE = 2;
    public static final int MACHINE_VS_MACHINE = 3;
    
    // Constantes para estados del juego
    public static final int MENU = 1;
    public static final int PLAYING = 2;
    public static final int PAUSED = 3;
    public static final int WON = 4;
    public static final int LOST = 5;
    
    private Level currentLevel;
    private Board board;
    private int gameMode;
    private int gameState;
    private int score;
    private int timeRemaining;
    private IceCream iceCream1;
    private IceCream iceCream2; // Por si cambiamos el modo de JvsJ
    private boolean isPaused;
    
    /**
     * Constructor of the Game class
     */
    public BadDopoCream() {
        this.gameState = MENU;
        this.score = 0;
        this.timeRemaining = 180;
        this.isPaused = false;
    }
    
    /**
     * Returns the current level
     * @return the current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Obtiene el tablero
     * @return board 
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Obtiene el modo de juego
     * @return Modo de juego
     */
    public int getGameMode() {
        return gameMode;
    }
    
    /**
     * Establece el modo de juego
     * @param mode Modo de juego (PLAYER_VS_PLAYER, PLAYER_VS_MACHINE, MACHINE_VS_MACHINE)
     */
    public void setGameMode(int mode) {
        this.gameMode = mode;
    }
    
    /**
     * Obtiene el estado del juego
     * @return Estado (MENU, PLAYING, PAUSED, WON, LOST)
     */
    public int getGameState() {
        return gameState;
    }
    
    /**
     * Obtiene el puntaje actual
     * @return Puntaje
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Obtiene el tiempo restante
     * @return Tiempo restante en segundos
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }
    
    /**
     * Obtiene el primer helado
     * @return Helado del jugador 1
     */
    public IceCream getIceCream1() {
        return iceCream1;
    }
    
    /**
     * Obtiene el segundo helado (solo para PvsP)
     * @return Helado del jugador 2
     */
    public IceCream getIceCream2() {
        return iceCream2;
    }
    
    /**
     * Verifica si el juego está pausado
     * @return true si está pausado, false en caso contrario
     */
    public boolean isPaused() {
        return isPaused;
    }
    
    /**
     * Inicia el juego
     */
    public void start() {
        this.gameState = PLAYING;
        // Inicializa el nivel, tablero y entidades
    }
    
    /**
     * Pausa el juego
     */
    public void pause() {
        if (gameState == PLAYING) {
            this.isPaused = true;
            this.gameState = PAUSED;
        }
    }
    
    /**
     * Resume el juego pausado
     */
    public void resume() {
        if (gameState == PAUSED) {
            this.isPaused = false;
            this.gameState = PLAYING;
        }
    }
    
    /**
     * Reinicia el nivel actual
     */
    public void restart() {
        // Reinicia posiciones y estado del nivel actual
        this.timeRemaining = 180;
        currentLevel.loadLevel();
    }
    
    /**
     * Actualiza el estado del juego (llamado cada frame)
     */
    public void update() {
        if (gameState != PLAYING) return;
        
        // Actualiza tiempo
        // Actualiza entidades
        // Verifica colisiones
        // Verifica condiciones de victoria/derrota
        
        checkCollisions();
        
        if (checkWinCondition()) {
            gameState = WON;
        }
        
        if (checkLoseCondition()) {
            gameState = LOST;
        }
    }
    
    /**
     * Verifica colisiones entre entidades
     */
    public void checkCollisions() {
        // Verifica colisión helado-enemigo
        // Verifica colisión helado-fruta
    }
    
    /**
     * Verifica si se cumplió la condición de victoria
     * @return true si se ganó, false en caso contrario
     */
    public boolean checkWinCondition() {
        // El nivel se gana al recolectar todas las frutas
        return currentLevel.isCompleted();
    }
    
    /**
     * Verifica si se cumplió la condición de derrota
     * @return true si se perdió, false en caso contrario
     */
    public boolean checkLoseCondition() {
        // El nivel se pierde si: el helado muere o se acaba el tiempo
        return !iceCream1.isActive() || timeRemaining <= 0;
    }
    
    /**
     * Avanza al siguiente nivel
     */
    public void nextLevel() {
        int nextLevelNumber = currentLevel.getLevelNumber() + 1;
        if (nextLevelNumber <= 3) {
            currentLevel = new Level(nextLevelNumber);
            currentLevel.loadLevel();
            restart();
        }
    }
    
    /**
     * Agrega puntos al score
     * @param points Puntos a agregar
     */
    public void addScore(int points) {
        this.score += points;
    }
}
