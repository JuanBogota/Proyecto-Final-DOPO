package domain;

/**
 * Clase que representa una baldosa caliente, obstáculo peligroso estático.
 * La baldosa caliente elimina al helado al contacto.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class HotTile extends Obstacle implements Dangerous {
    
    /**
     * Constructor de la clase HotTile.
     * @param position Posición de la baldosa caliente en el tablero.
     */
    public HotTile(Position position) {
        super(position);
    }
    
    @Override
    public boolean isSolid() {
        return false; // No es sólido, el helado puede pasar (pero muere)
    }
    
    @Override
    public boolean isDestructible() {
        return false; // Las baldosas calientes no se pueden destruir
    }
    
    @Override
    public void update(Board board) {
        // Verificar colisiones con helados en cada actualización
        checkCollisions(board);
    }
    
    /**
     * Verifica si hay helados en la posición de la baldosa caliente
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
        iceCream.eliminate(); // La baldosa derrite al helado
    }
    
    @Override
    public String getType() {
        return "HOT_TILE";
    }
}