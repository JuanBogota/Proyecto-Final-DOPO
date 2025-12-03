package domain;

/**
 * Class representing the Cactus fruit in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Cactus extends Fruit {
    
    private static final int CACTUS_POINTS = 250;
    private static final int SPIKE_CYCLE = 30 * 60;
    
    private boolean hasSpikes;
    private int updateCounter;
    
    /**
     * Constructor
     * @param position Posición del cactus
     * @param board Referencia al tablero
     */
    public Cactus(Position position, Board board) {
        super(position, Fruit.CACTUS, CACTUS_POINTS, board);
        this.hasSpikes = false;
        this.updateCounter = 0;
    }
    
    /**
     * Constructor con estado inicial de púas
     * @param position Posición del cactus
     * @param board Referencia al tablero
     * @param startsWithSpikes Si comienza con púas o sin ellas
     */
    public Cactus(Position position, Board board, boolean startsWithSpikes) {
        super(position, Fruit.CACTUS, CACTUS_POINTS, board);
        this.hasSpikes = startsWithSpikes;
        this.updateCounter = 0;
    }
    
    @Override
    public void update() {
        if (isCollected) return;
        
        updateCounter++;
        
        // Cada 30 segundos saca puas
        if (updateCounter >= SPIKE_CYCLE) {
            toggleSpikes();
            updateCounter = 0;
        }
    }
    
    /**
     * Alterna el estado de las púas
     */
    private void toggleSpikes() {
        hasSpikes = !hasSpikes;
    }
    
    /**
     * Verifica si el cactus tiene púas actualmente
     * @return true si tiene púas
     */
    public boolean hasSpikes() {
        return hasSpikes;
    }
    
    @Override
    public boolean isDangerous() {
        return hasSpikes;
    }
    
    /**
     * Obtiene el tiempo restante hasta el próximo cambio de estado
     * @return Updates restantes
     */
    public int getTimeUntilToggle() {
        return SPIKE_CYCLE - updateCounter;
    }
    
    /**
     * Obtiene el contador actual de updates
     * @return Contador de updates
     */
    public int getUpdateCounter() {
        return updateCounter;
    }
    
    @Override
    public void collect() {
        if (!hasSpikes) {
            super.collect();
        }
    }
    
    /**
     * Verifica si se puede recolectar de forma segura
     * @return true si no tiene púas
     */
    public boolean canBeCollectedSafely() {
        return !hasSpikes;
    }
}