package domain;

/**
 * Configuración del Nivel 2 (Medio)
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Level2Template implements LevelTemplate {
    
    @Override
    public void configure(LevelBuilder builder) {
        // Agregar helado del jugador
        builder.addVanillaIceCream(12, 7);
        
        // PRIMERA OLEADA: Bananos (400 pts total)
        builder.addBanana(6, 7)
               .addBanana(18, 7)
               .addBanana(12, 4)
               .addBanana(12, 10);
        
        // SEGUNDA OLEADA: Uvas (300 pts total)
        builder.startNewWave()
               .addGrape(2, 2)
               .addGrape(22, 2)
               .addGrape(2, 12)
               .addGrape(22, 12)
               .addGrape(12, 2)
               .addGrape(12, 12);
        
        // Enemigos + perseguidor
        builder.addTroll(4, 4)
               .addTroll(20, 10)
               .addPot(12, 2); // Maceta perseguidora
        
        // Más obstáculos
        builder.addHorizontalIceBlocks(8, 16, 5)
               .addHorizontalIceBlocks(8, 16, 9)
               .addVerticalIceBlocks(10, 3, 11)
               .addVerticalIceBlocks(14, 3, 11);
    }
    
    @Override
    public String getName() {
        return "Nivel 2 - Medio";
    }
    
    @Override
    public String getDescription() {
        return """
               Nivel 2 - Medio
               
               Oleadas:
               1. 4 Bananos (100 pts c/u) = 400 pts
               2. 6 Uvas (50 pts c/u) = 300 pts
               
               Enemigos:
               • 2 Trolls (movimiento lineal)
               • 1 Maceta (persigue al jugador)
               
               Obstáculos:
               • Laberinto de bloques de hielo
               
               Puntaje máximo: 700 puntos
               Tiempo límite: 3 minutos
               """;
    }
    
    @Override
    public int getLevelNumber() {
        return 2;
    }
    
    @Override
    public Difficulty getDifficulty() {
        return Difficulty.MEDIUM;
    }
}
