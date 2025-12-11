package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el tablero del juego.
 * Gestiona la cuadrícula del juego y los objetos en ella.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Board {
    private final int width;
    private final int height;
    private final List<GameObject>[][] grid;
    private List<IceCream> iceCreams;
    
    /**
     * Constructor del tablero con dimensiones específicas.
     * @param width Ancho del tablero.
     * @param height Altura del tablero.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new ArrayList[width][height];
        this.iceCreams = new ArrayList<>();
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new ArrayList<>();
            }
        }
    }
    
    /**
     * Obtiene el ancho del tablero
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Obtiene la altura del tablero
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Verifica si una posición está dentro de los límites del tablero
     */
    public boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
               position.getY() >= 0 && position.getY() < height;
    }
    
    /**
     * Agrega un objeto al tablero en una posición específica
     */
    public void addObject(GameObject object) {
        Position pos = object.getPosition();
        if (isValidPosition(pos)) {
            grid[pos.getX()][pos.getY()].add(object);
            
            if (object instanceof IceCream iceCream) {
                iceCreams.add(iceCream);
            }
        }
    }
    
    /**
     * Remueve un objeto del tablero
     */
    public void removeObject(GameObject object) {
        Position pos = object.getPosition();
        if (isValidPosition(pos)) {
            grid[pos.getX()][pos.getY()].remove(object);
            
            if (object instanceof IceCream iceCream) {
                iceCreams.remove(iceCream);
            }
        }
    }
    
    /**
     * Mueve un objeto de una posición a otra
     */
    public void moveObject(GameObject object, Position newPosition) {
        removeObject(object);
        object.setPosition(newPosition);
        addObject(object);
    }
    
    /**
     * Obtiene todos los objetos en una posición específica
     */
    public List<GameObject> getObjectsAt(Position position) {
        if (!isValidPosition(position)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(grid[position.getX()][position.getY()]);
    }
    
    /**
     * Verifica si hay un obstáculo sólido en una posición
     */
    public boolean isSolidAt(Position position) {
        List<GameObject> objects = getObjectsAt(position);
        for (GameObject obj : objects) {
            if (obj.isSolid()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifica si hay un bloque de hielo en una posición
     */
    public boolean hasIceBlockAt(Position position) {
        List<GameObject> objects = getObjectsAt(position);
        for (GameObject obj : objects) {
            if (obj instanceof IceBlock) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Obtiene la posición del helado más cercano a una posición dada
     */
    public Position getNearestIceCreamPosition(Position from) {
        Position nearest = null;
        int minDistance = Integer.MAX_VALUE;
        
        for (IceCream iceCream : iceCreams) {
            if (iceCream.isAlive()) {
                int distance = from.distanceTo(iceCream.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = iceCream.getPosition();
                }
            }
        }
        
        return nearest;
    }
    
    /**
     * Obtiene todos los helados del tablero
     */
    public List<IceCream> getIceCreams() {
        return new ArrayList<>(iceCreams);
    }
    
    /**
     * Obtiene todos los objetos del tablero
     */
    public List<GameObject> getAllObjects() {
        List<GameObject> allObjects = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                allObjects.addAll(grid[x][y]);
            }
        }
        return allObjects;
    }
}
