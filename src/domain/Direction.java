package domain;

/**
 * Constantes para direcciones de movimiento en Bad Dopo Cream
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Direction {
    // Constantes de dirección
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    
    /**
     * Verifica si una dirección es válida
     * @param dir Dirección a verificar
     * @return true si es válida
     */
    public static boolean isValid(int dir) {
        return dir >= UP && dir <= RIGHT;
    }
    
    /**
     * Obtiene el nombre de la dirección
     * @param dir Dirección
     * @return Nombre como String
     */
    public static String getName(int dir) {
        return switch (dir) {
            case UP -> "UP";
            case DOWN -> "DOWN";
            case LEFT -> "LEFT";
            case RIGHT -> "RIGHT";
            default -> "UNKNOWN";
        };
    }
    
    /**
     * Gira 90 grados a la derecha
     * @param dir Dirección actual
     * @return Nueva dirección
     */
    public static int turnRight(int dir) {
        return switch (dir) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
            default -> DOWN;
        };
    }
    
    /**
     * Gira 90 grados a la izquierda
     * @param dir Dirección actual
     * @return Nueva dirección
     */
    public static int turnLeft(int dir) {
        return switch (dir) {
            case UP -> LEFT;
            case LEFT -> DOWN;
            case DOWN -> RIGHT;
            case RIGHT -> UP;
            default -> DOWN;
        };
    }
    
    /**
     * Da media vuelta (180 grados)
     * @param dir Dirección actual
     * @return Dirección opuesta
     */
    public static int opposite(int dir) {
        return switch (dir) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> DOWN;
        };
    }
}