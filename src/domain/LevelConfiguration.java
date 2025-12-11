package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuración de un nivel.
 * Almacena la configuración inicial de un nivel.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class LevelConfiguration {
    private List<IceCream> iceCreams;
    private List<Fruit> fruits;
    private List<Enemy> enemies;
    private List<IceBlock> obstacles;
    
    /**
     * Constructor de la configuración del nivel.
     */
    public LevelConfiguration() {
        this.iceCreams = new ArrayList<>();
        this.fruits = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.obstacles = new ArrayList<>();
    }
    
    /**
     * agrega un helado a la configuración del nivel
     * @param iceCream
     */
    public void addIceCream(IceCream iceCream) {
        iceCreams.add(iceCream);
    }
    
    /**
     * agrega una fruta a la configuración del nivel
     * @param fruit
     */
    public void addFruit(Fruit fruit) {
        fruits.add(fruit);
    }
    
    /**
     * agrega un enemigo a la configuración del nivel
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    /**
     * agrega un obstáculo a la configuración del nivel
     * @param obstacle
     */
    public void addObstacle(IceBlock obstacle) {
        obstacles.add(obstacle);
    }
    
    /**
     * Obtiene la lista de helados en la configuración
     * @return lista de helados
     */
    public List<IceCream> getIceCreams() {
        return new ArrayList<>(iceCreams);
    }
    
    /**
     * Obtiene la lista de frutas en la configuración
     * @return lista de frutas
     */
    public List<Fruit> getFruits() {
        return new ArrayList<>(fruits);
    }
    
    /**
     * Obtiene la lista de enemigos en la configuración
     * @return lista de enemigos
     */
    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }
    
    /**
     * Obtiene la lista de obstáculos en la configuración
     * @return lista de obstáculos
     */
    public List<IceBlock> getObstacles() {
        return new ArrayList<>(obstacles);
    }
}
