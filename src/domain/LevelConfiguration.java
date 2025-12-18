package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuración de un nivel.
 * Almacena la configuración inicial de un nivel con soporte para oleadas de frutas.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class LevelConfiguration {
    private List<IceCream> iceCreams;
    private List<List<Fruit>> fruitWaves; // Oleadas de frutas
    private List<Enemy> enemies;
    private List<Obstacle> obstacles;
    
    /**
     * Constructor de la configuración del nivel.
     */
    public LevelConfiguration() {
        this.iceCreams = new ArrayList<>();
        this.fruitWaves = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.obstacles = new ArrayList<>();
    }
    
    /**
     * Agrega un helado a la configuración del nivel
     * @param iceCream
     */
    public void addIceCream(IceCream iceCream) {
        iceCreams.add(iceCream);
    }
    
    /**
     * Agrega una oleada de frutas
     * @param wave Lista de frutas de la oleada
     */
    public void addWave(List<Fruit> wave) {
        fruitWaves.add(new ArrayList<>(wave));
    }
    
    /**
     * Agrega una fruta a la configuración del nivel (retrocompatibilidad)
     * @param fruit
     */
    @Deprecated
    public void addFruit(Fruit fruit) {
        // Agregar a la primera oleada por defecto
        if (fruitWaves.isEmpty()) {
            fruitWaves.add(new ArrayList<>());
        }
        fruitWaves.get(0).add(fruit);
    }
    
    /**
     * Agrega un enemigo a la configuración del nivel
     * @param enemy
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    /**
     * Agrega un obstáculo a la configuración del nivel
     * @param obstacle
     */
    public void addObstacle(Obstacle obstacle) {
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
     * Obtiene todas las oleadas de frutas
     * @return lista de oleadas
     */
    public List<List<Fruit>> getFruitWaves() {
        List<List<Fruit>> copy = new ArrayList<>();
        for (List<Fruit> wave : fruitWaves) {
            copy.add(new ArrayList<>(wave));
        }
        return copy;
    }
    
    /**
     * Obtiene la lista de frutas en la configuración
     * @return lista de todas las frutas
     */
    @Deprecated
    public List<Fruit> getFruits() {
        List<Fruit> allFruits = new ArrayList<>();
        for (List<Fruit> wave : fruitWaves) {
            allFruits.addAll(wave);
        }
        return allFruits;
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
    public List<Obstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }
}
