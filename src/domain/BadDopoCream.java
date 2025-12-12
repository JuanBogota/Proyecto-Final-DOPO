package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que gestiona el juego.
 * Coordina el flujo del juego y sus estados.
 * Principio de Responsabilidad Única: Gestiona el juego y la creación de niveles.
 * Principio Open/Closed: Abierto para extensión (nuevos niveles), cerrado para modificación.
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
    private List<LevelTemplate> availableLevels;
    
    /**
     * Constructor de la clase BadDopoCream.
     */
    public BadDopoCream() {
        this.state = GameState.NOT_STARTED;
        this.currentLevelNumber = 1;
        this.lastUpdateTime = 0;
        this.lastSecondTime = 0;
        initializeAvailableLevels();
    }
    
    /**
     * Inicializa los niveles disponibles.
     * Principio Open/Closed: Para agregar un nuevo nivel, solo agrega una nueva instancia aquí.
     */
    private void initializeAvailableLevels() {
        availableLevels = new ArrayList<>();
        availableLevels.add(new Level1Template());
        availableLevels.add(new Level2Template());
        availableLevels.add(new Level3Template());
        // Para agregar un nuevo nivel: availableLevels.add(new Level4Template());
    }
    
    /**
     * Obtiene la lista de templates de niveles disponibles
     * @return Lista de templates de niveles
     */
    public List<LevelTemplate> getAvailableLevels() {
        return new ArrayList<>(availableLevels);
    }
    
    /**
     * Obtiene un template de nivel específico
     * @param levelIndex Índice del nivel (0 para nivel 1, 1 para nivel 2, etc.)
     * @return El template del nivel o null si no existe
     */
    public LevelTemplate getLevelTemplate(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < availableLevels.size()) {
            return availableLevels.get(levelIndex);
        }
        return null;
    }
    
    /**
     * Crea la configuración de un nivel usando su template
     * @param levelIndex Índice del nivel
     * @return La configuración del nivel
     */
    public LevelConfiguration createLevelConfiguration(int levelIndex) {
        LevelTemplate template = getLevelTemplate(levelIndex);
        if (template == null) {
            return null;
        }
        
        LevelBuilder builder = new LevelBuilder();
        template.configure(builder);
        return builder.build();
    }
    
    /**
     * Inicia el juego con un nivel específico
     * @param levelIndex Índice del nivel (0, 1, 2, etc.)
     */
    public void startGame(int levelIndex) {
        LevelConfiguration config = createLevelConfiguration(levelIndex);
        if (config != null) {
            startGameWithConfiguration(config);
            currentLevelNumber = levelIndex + 1;
        }
    }
    
    /**
     * Inicia el juego con una configuración de nivel específica
     */
    public void startGameWithConfiguration(LevelConfiguration config) {
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
        if (currentTime - lastUpdateTime >= 400) {
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
     * Mueve un helado en una dirección especí­fica
     */
    public boolean moveIceCream(IceCream iceCream, Direction direction) {
        if (state != GameState.PLAYING || !iceCream.isAlive()) {
            return false;
        }
        return iceCream.move(direction, currentLevel.getBoard());
    }
    
    /**
     * Crea bloques de hielo con un helado especí­fico
     */
    public void createIceBlocks(IceCream iceCream) {
        if (state == GameState.PLAYING && iceCream.isAlive()) {
            currentLevel.getBoard().createIceBlocks(
                iceCream.getPosition(), 
                iceCream.getFacingDirection()
            );
        }
    }
    
    /**
     * Rompe bloques de hielo con un helado especí­fico
     */
    public void breakIceBlocks(IceCream iceCream) {
        if (state == GameState.PLAYING && iceCream.isAlive()) {
            currentLevel.getBoard().breakIceBlocks(
                iceCream.getPosition(), 
                iceCream.getFacingDirection()
            );
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
    public void restartLevel(int levelIndex) {
        startGame(levelIndex);
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
