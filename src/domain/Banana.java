package domain;

/**
 * Clase que representa al plátano, fruta estática que otorga 100 puntos.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Banana extends Fruit {
    
    /**
     * Constructor de la clase Banana.
     * @param position Posición inicial del plátano en el tablero.
     */
    public Banana(Position position) {
        super(position, 100);
    }
    
    @Override
    public void update(Board board) {
        // Los plátanos no tienen comportamiento automático
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
        return "BANANA";
    }
}
