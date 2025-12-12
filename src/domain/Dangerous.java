package domain;

/**
 * Interfaz para objetos que son peligrosos para el helado.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public interface Dangerous {
    
    /**
     * Verifica si este objeto puede causar daño actualmente
     */
    boolean isDangerous();
    
    /**
     * Método llamado cuando el objeto entra en contacto con un helado
     * @param iceCream El helado que toca este objeto peligroso
     */
    void onContact(IceCream iceCream);
}
