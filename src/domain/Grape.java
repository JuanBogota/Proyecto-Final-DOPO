package domain;

/**
 * Clase que representa a la uva, fruta estática que otorga 50 puntos.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Grape extends Fruit {
    
    /**
     * Constructor de la clase Grape.
     * @param position Posición inicial de la uva en el tablero.
     */
    public Grape(Position position) {
        super(position, 50);
    }
    
    @Override
    public void update(Board board) {
        // Las uvas no tienen comportamiento automático
    }
    
    @Override
    protected boolean canBeCollectedNow() {
        return true;
    }
    
    @Override
    public boolean isCollectable() {
        return !collected && canBeCollectedNow();
    }
    
    @Override
    public String getType() {
        return "GRAPE";
    }
}
