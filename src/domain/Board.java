package domain;

import java.util.ArrayList;

/**
 * Represent the game board, managing ice blocks, fruits, and enemies.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Board {
    private int width;
    private int height;
    private ArrayList<IceBlock> iceBlocks;
    private ArrayList<Fruit> fruits;
    private ArrayList<Enemy> enemies;
    
    /**
     * Constructor
     * @param width Ancho del tablero
     * @param height Alto del tablero
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.iceBlocks = new ArrayList<IceBlock>();
        this.fruits = new ArrayList<Fruit>();
        this.enemies = new ArrayList<Enemy>();
    }
    
    /**
     * Obtiene el ancho del tablero
     * @return Ancho
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Obtiene el alto del tablero
     * @return Alto
     */
    public int getHeight() {
        return height;
    }

    /**
     * Obtiene la lista de enemigos en el tablero
     * @return Lista de enemigos
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Obtiene la lista de bloques de hielo en el tablero
     * @return Lista de bloques de hielo
     */
    public ArrayList<IceBlock> getIceBlocks() {
        return iceBlocks;
    }

    /**
     * Obtiene la lista de frutas en el tablero
     * @return Lista de frutas
     */
    public ArrayList<Fruit> getFruits() {
        return fruits;
    }
    
    /**
     * Agrega un bloque de hielo al tablero
     * @param block Bloque de hielo a agregar
     */
    public void addIceBlock(IceBlock block) {
        iceBlocks.add(block);
    }
    
    /**
     * Remueve un bloque de hielo en la posición especificada
     * @param pos Posición del bloque a remover
     */
    public void removeIceBlock(IceBlock block) {
        iceBlocks.remove(block);
    }
    
    /**
     * Agrega una fruta al tablero
     * @param fruit Fruta a agregar
     */
    public void addFruit(Fruit fruit) {
        fruits.add(fruit);
    }
    
    /**
     * Remueve una fruta en la posición especificada
     * @param pos Posición de la fruta a remover
     */
    public void removeFruit(Position pos) {
        for (int i = 0; i < fruits.size(); i++) {
            if (fruits.get(i).getPosition().equals(pos)) {
                fruits.remove(i);
                break;
            }
        }
    }
    
    /**
     * Agrega un enemigo al tablero
     * @param enemy Enemigo a agregar
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    
    /**
     * Verifica si una posición está vacía
     * @param pos Posición a verificar
     * @return true si está vacía, false en caso contrario
     */
    public boolean isEmpty(Position pos) {
        return getObjectAt(pos) == null;
    }
    
    /**
     * Obtiene el objeto en una posición específica
     * @param pos Posición a consultar
     * @return Objeto en la posición o null si está vacía
     */
    public GameObject getObjectAt(Position pos) {
        for (IceBlock block : iceBlocks) {
            if (block.getPosition().equals(pos)) return block;
        }
        
        for (Fruit fruit : fruits) {
            if (fruit.getPosition().equals(pos)) return fruit;
        }

        for (Enemy enemy : enemies) {
            if (enemy.getPosition().equals(pos)) return enemy;
        }
        
        return null;
    }
    
    /**
     * Verifica colisión entre dos objetos del juego
     * @param obj1 Primer objeto
     * @param obj2 Segundo objeto
     * @return true si colisionan, false en caso contrario
     */
    public boolean checkCollision(GameObject obj1, GameObject obj2) {
        return obj1.getPosition().equals(obj2.getPosition());
    }
    
    /**
     * Verifica si una posición es válida dentro del tablero
     * @param pos Posición a validar
     * @return true si es válida, false en caso contrario
     */
    public boolean isValidPosition(Position pos) {
        return pos.getX() >= 0 && pos.getX() < width &&
               pos.getY() >= 0 && pos.getY() < height;
    }
}
