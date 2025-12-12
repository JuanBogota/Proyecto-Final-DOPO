package domain;

/**
 * Template para definir la estructura de un nivel.
 * Principio Open/Closed: Para agregar un nuevo nivel, solo crea una clase
 * que implemente esta interfaz, sin modificar código existente.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public interface LevelTemplate {
    
    /**
     * Configura el nivel usando el builder
     * @param builder El constructor de nivel a configurar
     */
    void configure(LevelBuilder builder);
    
    /**
     * Obtiene el nombre del nivel
     * @return Nombre descriptivo del nivel
     */
    String getName();
    
    /**
     * Obtiene la descripción del nivel
     * @return Descripción con detalles de frutas, enemigos y obstáculos
     */
    String getDescription();
    
    /**
     * Obtiene el número del nivel
     * @return Número del nivel (1, 2, 3, etc.)
     */
    int getLevelNumber();
    
    /**
     * Obtiene la dificultad del nivel
     * @return Dificultad (EASY, MEDIUM, HARD)
     */
    Difficulty getDifficulty();
}
