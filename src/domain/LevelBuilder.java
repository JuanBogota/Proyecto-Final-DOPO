package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder para construir niveles de forma fluida.
 * Soporta oleadas de frutas.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class LevelBuilder {
    private LevelConfiguration config;
    private List<Fruit> currentWave;
    
    /**
     * Constructor del LevelBuilder.
     */
    public LevelBuilder() {
        this.config = new LevelConfiguration();
        this.currentWave = new ArrayList<>();
    }
    
    /**
     * Inicia una nueva oleada de frutas.
     * Las frutas de la oleada anterior se guardan.
     */
    public LevelBuilder startNewWave() {
        if (!currentWave.isEmpty()) {
            config.addWave(new ArrayList<>(currentWave));
            currentWave.clear();
        }
        return this;
    }
    
    /**
     * Agrega un helado de vainilla en una posición
     */
    public LevelBuilder addVanillaIceCream(int x, int y) {
        config.addIceCream(new VanillaIceCream(new Position(x, y)));
        return this;
    }
    
    /**
     * Agrega un helado de fresa en una posición
     */
    public LevelBuilder addStrawberryIceCream(int x, int y) {
        config.addIceCream(new StrawberryIceCream(new Position(x, y)));
        return this;
    }
    
    /**
     * Agrega un helado de chocolate en una posición
     */
    public LevelBuilder addChocolateIceCream(int x, int y) {
        config.addIceCream(new ChocolateIceCream(new Position(x, y)));
        return this;
    }
    
    /**
     * Agrega uvas en una posición
     */
    public LevelBuilder addGrape(int x, int y) {
        Fruit grape = new Grape(new Position(x, y));
        currentWave.add(grape);
        return this;
    }
    
    /**
     * Agrega un plátano en una posición
     */
    public LevelBuilder addBanana(int x, int y) {
        Fruit banana = new Banana(new Position(x, y));
        currentWave.add(banana);
        return this;
    }
    
    /**
     * Agrega un enemigo Troll en una posición
     */
    public LevelBuilder addTroll(int x, int y) {
        config.addEnemy(new Troll(new Position(x, y)));
        return this;
    }
    
    /**
     * Agrega un enemigo Maceta en una posición
     */
    public LevelBuilder addPot(int x, int y) {
        config.addEnemy(new Pot(new Position(x, y)));
        return this;
    }
    
    /**
     * Agrega un bloque de hielo en una posición
     */
    public LevelBuilder addIceBlock(int x, int y) {
        config.addObstacle(new IceBlock(new Position(x, y), false));
        return this;
    }
    
    /**
     * Agrega múltiples bloques de hielo en lí­nea horizontal
     */
    public LevelBuilder addHorizontalIceBlocks(int startX, int endX, int y) {
        for (int x = startX; x <= endX; x++) {
            addIceBlock(x, y);
        }
        return this;
    }
    
    /**
     * Agrega múltiples bloques de hielo en lí­nea vertical
     */
    public LevelBuilder addVerticalIceBlocks(int x, int startY, int endY) {
        for (int y = startY; y <= endY; y++) {
            addIceBlock(x, y);
        }
        return this;
    }
    
    /**
     * Construye y retorna la configuración del nivel
     */
    public LevelConfiguration build() {
        // Guardar la última oleada si hay frutas pendientes
        if (!currentWave.isEmpty()) {
            config.addWave(new ArrayList<>(currentWave));
        }
        return config;
    }
}
