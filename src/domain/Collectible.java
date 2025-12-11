package domain;

/**
 * Interfaz para objetos que pueden ser recolectados por el helado.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public interface Collectible {
    
    /**
     * Obtiene los puntos que otorga este objeto al ser recolectado
     */
    int getPoints();
    
    /**
     * Verifica si este objeto puede ser recolectado actualmente
     */
    boolean isCollectable();
    
    /**
     * Método llamado cuando el objeto es recolectado
     * @param collector El helado que recolecta el objeto
     */
    void onCollect(IceCream collector);
}
