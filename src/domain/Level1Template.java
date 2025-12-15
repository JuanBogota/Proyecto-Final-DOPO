package domain;

/**
 * Configuración del Nivel 1 (Fácil)
 * Principio Open/Closed: Esta clase está cerrada para modificación,
 * pero el sistema está abierto para extensión (podemos crear Level4, Level5, etc.)
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Level1Template implements LevelTemplate {
    
    @Override
    public void configure(LevelBuilder builder) {
        // Agregar helado del jugador
        builder.addVanillaIceCream(12, 7);
        
        // PRIMERA OLEADA: Bananos (400 pts total)
        builder.addBanana(12, 3)
               .addBanana(6, 7)
               .addBanana(18, 7)
               .addBanana(12, 11);
        
        // SEGUNDA OLEADA: Uvas (200 pts total)
        builder.startNewWave()
               .addGrape(3, 3)
               .addGrape(21, 3)
               .addGrape(3, 11)
               .addGrape(21, 11);
        
        // Enemigos básicos
        builder.addTroll(5, 5)
               .addTroll(19, 9);
        
        // Obstáculos simples
        builder.addHorizontalIceBlocks(10, 14, 5)
               .addHorizontalIceBlocks(10, 14, 9);
    }
    
    @Override
    public String getName() {
        return "Nivel 1 - Fácil";
    }
    
    @Override
    public int getLevelNumber() {
        return 1;
    }
    
    @Override
    public Difficulty getDifficulty() {
        return Difficulty.EASY;
    }
}
