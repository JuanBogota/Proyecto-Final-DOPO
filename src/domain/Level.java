package domain;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a game level in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Level {
    private int levelNumber;
    private HashMap<Integer, Integer> objectiveFruits;
    private ArrayList<Integer> enemyTypes;
    private ArrayList<Position> initialIceBlocksPositions;
    private int timeLimit;
    
    /**
     * Constructor
     * @param levelNumber Número del nivel
     */
    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        this.objectiveFruits = new HashMap<Integer, Integer>();
        this.enemyTypes = new ArrayList<Integer>();
        this.initialIceBlocksPositions = new ArrayList<Position>();
        this.timeLimit = 180; // 3 minutos por defecto
    }
    
    /**
     * Obtiene el número del nivel
     * @return Número del nivel
     */
    public int getLevelNumber() {
        return levelNumber;
    }
    
    /**
     * Obtiene el límite de tiempo
     * @return Tiempo límite en segundos
     */
    public int getTimeLimit() {
        return timeLimit;
    }
    
    /**
     * Obtiene los objetivos de frutas
     * @return Mapa de tipo de fruta a cantidad objetivo
     */
    public HashMap<Integer, Integer> getObjectiveFruits() {
        return objectiveFruits;
    }
    
    /**
     * Obtiene los tipos de enemigos del nivel
     * @return Lista de tipos de enemigos
     */
    public ArrayList<Integer> getEnemyTypes() {
        return enemyTypes;
    }
    
    /**
     * Carga la configuración del nivel
     */
    public void loadLevel() {
        // Configura el nivel según el número
        if (levelNumber == 1) {
            configureLevelOne();
        } else if (levelNumber == 2) {
            configureLevelTwo();
        } else if (levelNumber == 3) {
            configureLevelThree();
        } else {
            configureLevelOne();
        }
    }
    
    /**
     * Configura el nivel 1
     */
    private void configureLevelOne() {
        objectiveFruits.put(Fruit.UVAS, 8);
        objectiveFruits.put(Fruit.PLATANO, 8);
        enemyTypes.add(Enemy.TROLL);
        enemyTypes.add(Enemy.TROLL);
    }
    
    /**
     * Configura el nivel 2
     */
    private void configureLevelTwo() {
        objectiveFruits.put(Fruit.PINA, 8);
        objectiveFruits.put(Fruit.PLATANO, 8);
        enemyTypes.add(Enemy.MACETA);
    }
    
    /**
     * Configura el nivel 3
     */
    private void configureLevelThree() {
        objectiveFruits.put(Fruit.PINA, 8);
        objectiveFruits.put(Fruit.CEREZA, 8);
        enemyTypes.add(Enemy.CALAMAR_NARANJA);
    }
    
    /**
     * Obtiene la configuración del nivel
     */
    public void getConfiguration() {
        // Retorna la configuración del nivel
    }
    
    /**
     * Verifica si el nivel está completado
     * @return true si está completado, false en caso contrario
     */
    public boolean isCompleted() {
        // Lógica para verificar si se cumplieron los objetivos
        return false;
    }
}
