package domain;

/**
 * Clase que representa una fogata, obstáculo peligroso estático.
 * La fogata elimina al helado al contacto.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Bonfire extends Obstacle implements Dangerous {
    
    /**
     * Constructor de la clase Bonfire.
     * @param position Posición de la fogata en el tablero.
     */
    public Bonfire(Position position) {
        super(position);
    }
    
    @Override
    public boolean isSolid() {
        return false; // No es sólido, el helado puede pasar (pero muere)
    }
    
    @Override
    public boolean isDestructible() {
        return false; // Las fogatas no se pueden destruir
    }
    
    @Override
    public void update(Board board) {
        checkCollisions(board);
    }
    
    /**
     * Verifica si hay helados en la posición de la fogata
     */
    private void checkCollisions(Board board) {
        for (GameObject obj : board.getObjectsAt(position)) {
            if (obj instanceof IceCream iceCream) {
                if (iceCream.isAlive()) {
                    onContact(iceCream);
                }
            }
        }
    }
    
    @Override
    public boolean isDangerous() {
        return true;
    }
    
    @Override
    public void onContact(IceCream iceCream) {
        iceCream.eliminate(); // La fogata mata al helado
    }
    
    @Override
    public String getType() {
        return "BONFIRE";
    }
}