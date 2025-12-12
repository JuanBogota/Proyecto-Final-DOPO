package domain;

/**
 * Niveles de dificultad del juego
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public enum Difficulty {
    EASY("Fácil"),
    MEDIUM("Medio"),
    HARD("Difícil");
    
    private final String displayName;
    
    Difficulty(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
