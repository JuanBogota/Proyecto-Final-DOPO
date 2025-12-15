package domain;

/**
 * Configuración del Nivel 3 (Difícil)
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class Level3Template implements LevelTemplate {
    
    @Override
    public void configure(LevelBuilder builder) {
        // Agregar helado del jugador
        builder.addVanillaIceCream(12, 7);
        
        // PRIMERA OLEADA: Bananos
        builder.addBanana(12, 1)
               .addBanana(12, 13)
               .addBanana(1, 7)
               .addBanana(23, 7);
        
        // SEGUNDA OLEADA: Uvas
        builder.startNewWave()
               .addGrape(1, 1)
               .addGrape(23, 1)
               .addGrape(1, 13)
               .addGrape(23, 13)
               .addGrape(6, 3)
               .addGrape(18, 3)
               .addGrape(6, 11)
               .addGrape(18, 11);
        
        // Muchos enemigos
        builder.addTroll(3, 3)
               .addTroll(21, 11)
               .addPot(8, 7)
               .addPot(16, 7);
        
        // Laberinto complejo de obstáculos
        builder.addHorizontalIceBlocks(5, 8, 4)
               .addHorizontalBonfires(16, 19, 4)
               .addHorizontalHotTiles(5, 8, 10)
               .addHorizontalIceBlocks(16, 19, 10)
               .addVerticalIceBlocks(10, 2, 5)
               .addVerticalIceBlocks(14, 2, 5)
               .addVerticalIceBlocks(10, 9, 12)
               .addVerticalIceBlocks(14, 9, 12);
    }
    
    @Override
    public String getName() {
        return "Nivel 3 - Difícil";
    }
    
    @Override
    public String getDescription() {
        return """
               Nivel 3 - Difícil
               
               Oleadas:
               1. 4 Bananos (100 pts c/u) = 400 pts
               2. 8 Uvas (50 pts c/u) = 400 pts
               
               Enemigos:
               • 2 Trolls
               • 2 Macetas
               
               Obstáculos:
               • Laberinto complejo de hielo
               
               Puntaje máximo: 800 puntos
               Tiempo límite: 3 minutos
               """;
    }
    
    @Override
    public int getLevelNumber() {
        return 3;
    }
    
    @Override
    public Difficulty getDifficulty() {
        return Difficulty.HARD;
    }
}
