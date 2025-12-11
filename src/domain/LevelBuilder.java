package domain;

/**
 * Builder para construir niveles de forma fluida.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class LevelBuilder {
    private LevelConfiguration config;
    
    /**
     * Constructor del LevelBuilder.
     */
    public LevelBuilder() {
        this.config = new LevelConfiguration();
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
        config.addFruit(new Grape(new Position(x, y)));
        return this;
    }
    
    /**
     * Agrega un plátano en una posición
     */
    public LevelBuilder addBanana(int x, int y) {
        config.addFruit(new Banana(new Position(x, y)));
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
     * Agrega múltiples bloques de hielo en línea horizontal
     */
    public LevelBuilder addHorizontalIceBlocks(int startX, int endX, int y) {
        for (int x = startX; x <= endX; x++) {
            addIceBlock(x, y);
        }
        return this;
    }
    
    /**
     * Agrega múltiples bloques de hielo en línea vertical
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
        return config;
    }
}
