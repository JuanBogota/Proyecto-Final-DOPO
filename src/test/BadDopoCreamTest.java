package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.*;

/**
 * Pruebas unitarias para la clase BadDopoCream.
 * Actualizado para arquitectura SOLID v3.0
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 3.0
 */
public class BadDopoCreamTest {
    
    private BadDopoCream game;
    private LevelConfiguration config;
    
    /**
     * Configuración inicial antes de cada prueba
     */
    @Before
    public void setUp() {
        game = new BadDopoCream();
        config = createBasicConfiguration();
    }
    
    /**
     * Crea una configuración básica de nivel para las pruebas
     */
    private LevelConfiguration createBasicConfiguration() {
        LevelBuilder builder = new LevelBuilder();
        return builder
            .addVanillaIceCream(5, 5)
            .addGrape(10, 10)
            .addBanana(12, 12)
            .addTroll(8, 8)
            .build();
    }
    
    /**
     * Prueba que el juego se inicializa correctamente
     */
    @Test
    public void testConstructor() {
        assertEquals(GameState.NOT_STARTED, game.getState());
        assertEquals(1, game.getCurrentLevelNumber());
        assertNull(game.getCurrentLevel());
    }
    
    /**
     * Prueba que los niveles disponibles se inicializan correctamente
     */
    @Test
    public void testAvailableLevels() {
        assertNotNull(game.getAvailableLevels());
        assertEquals(3, game.getAvailableLevels().size());
        
        // Verificar que los niveles son los correctos
        assertEquals("Nivel 1 - Fácil", game.getLevelTemplate(0).getName());
        assertEquals("Nivel 2 - Medio", game.getLevelTemplate(1).getName());
        assertEquals("Nivel 3 - Difícil", game.getLevelTemplate(2).getName());
    }
    
    /**
     * Prueba que se puede obtener un template de nivel
     */
    @Test
    public void testGetLevelTemplate() {
        LevelTemplate level1 = game.getLevelTemplate(0);
        assertNotNull(level1);
        assertEquals(1, level1.getLevelNumber());
        assertEquals(Difficulty.EASY, level1.getDifficulty());
    }
    
    /**
     * Prueba que retorna null para índice inválido
     */
    @Test
    public void testGetLevelTemplateInvalidIndex() {
        assertNull(game.getLevelTemplate(-1));
        assertNull(game.getLevelTemplate(999));
    }
    
    /**
     * Prueba crear configuración de nivel usando template
     */
    @Test
    public void testCreateLevelConfiguration() {
        LevelConfiguration config = game.createLevelConfiguration(0);
        assertNotNull(config);
        assertFalse(config.getIceCreams().isEmpty());
        assertFalse(config.getFruitWaves().isEmpty());
    }
    
    /**
     * Prueba que el juego inicia correctamente con índice de nivel
     */
    @Test
    public void testStartGameWithIndex() {
        game.startGame(0); // Nivel 1
        
        assertEquals(GameState.PLAYING, game.getState());
        assertNotNull(game.getCurrentLevel());
        assertEquals(1, game.getCurrentLevelNumber());
    }
    
    /**
     * Prueba que el juego inicia correctamente con configuración (método legacy)
     * Este método mantiene compatibilidad con tests antiguos
     */
    @Test
    public void testStartGame() {
        game.startGameWithConfiguration(config);
        
        assertEquals(GameState.PLAYING, game.getState());
        assertNotNull(game.getCurrentLevel());
        assertEquals(1, game.getCurrentLevelNumber());
    }
    
    /**
     * Prueba que el juego inicia correctamente con configuración
     */
    @Test
    public void testStartGameWithConfiguration() {
        game.startGameWithConfiguration(config);
        
        assertEquals(GameState.PLAYING, game.getState());
        assertNotNull(game.getCurrentLevel());
        assertEquals(1, game.getCurrentLevelNumber());
    }
    
    /**
     * Prueba iniciar diferentes niveles
     */
    @Test
    public void testStartDifferentLevels() {
        // Nivel 1
        game.startGame(0);
        assertEquals(1, game.getCurrentLevelNumber());
        
        // Nivel 2
        game.startGame(1);
        assertEquals(2, game.getCurrentLevelNumber());
        
        // Nivel 3
        game.startGame(2);
        assertEquals(3, game.getCurrentLevelNumber());
    }
    
    /**
     * Prueba que no inicia con índice inválido
     */
    @Test
    public void testStartGameWithInvalidIndex() {
        game.startGame(999);
        assertEquals(GameState.NOT_STARTED, game.getState());
        assertNull(game.getCurrentLevel());
    }
    
    /**
     * Prueba que el nivel actual se obtiene correctamente
     */
    @Test
    public void testGetCurrentLevel() {
        assertNull(game.getCurrentLevel());
        
        game.startGame(0);
        assertNotNull(game.getCurrentLevel());
        assertEquals(1, game.getCurrentLevel().getLevelNumber());
    }
    
    /**
     * Prueba que el estado del juego cambia correctamente
     */
    @Test
    public void testGetState() {
        assertEquals(GameState.NOT_STARTED, game.getState());
        
        game.startGame(0);
        assertEquals(GameState.PLAYING, game.getState());
        
        game.pause();
        assertEquals(GameState.PAUSED, game.getState());
        
        game.resume();
        assertEquals(GameState.PLAYING, game.getState());
        
        game.endGame();
        assertEquals(GameState.GAME_OVER, game.getState());
    }
    
    /**
     * Prueba la funcionalidad de pausa
     */
    @Test
    public void testPause() {
        game.startGame(0);
        assertEquals(GameState.PLAYING, game.getState());
        
        game.pause();
        assertEquals(GameState.PAUSED, game.getState());
    }
    
    /**
     * Prueba la funcionalidad de reanudar
     */
    @Test
    public void testResume() {
        game.startGame(0);
        game.pause();
        assertEquals(GameState.PAUSED, game.getState());
        
        game.resume();
        assertEquals(GameState.PLAYING, game.getState());
    }
    
    /**
     * Prueba que no se puede reanudar si no está pausado
     */
    @Test
    public void testResumeWhenNotPaused() {
        game.startGame(0);
        assertEquals(GameState.PLAYING, game.getState());
        
        game.resume();
        assertEquals(GameState.PLAYING, game.getState());
    }
    
    /**
     * Prueba el movimiento de helado
     */
    @Test
    public void testMoveIceCream() {
        game.startGame(0);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        Position initialPosition = iceCream.getPosition();
        
        boolean moved = game.moveIceCream(iceCream, Direction.NORTH);
        
        assertTrue(moved);
        assertNotEquals(initialPosition, iceCream.getPosition());
    }
    
    /**
     * Prueba que no se puede mover un helado muerto
     */
    @Test
    public void testMoveDeadIceCream() {
        game.startGame(0);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        iceCream.eliminate();
        
        boolean moved = game.moveIceCream(iceCream, Direction.NORTH);
        
        assertFalse(moved);
    }
    
    /**
     * Prueba que no se puede mover cuando el juego no está en estado PLAYING
     */
    @Test
    public void testMoveWhenNotPlaying() {
        game.startGame(0);
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        
        game.pause();
        boolean moved = game.moveIceCream(iceCream, Direction.NORTH);
        
        assertFalse(moved);
    }
    
    /**
     * Prueba la creación de bloques de hielo
     */
    @Test
    public void testCreateIceBlocks() {
        game.startGameWithConfiguration(config);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        Board board = game.getCurrentLevel().getBoard();
        
        game.createIceBlocks(iceCream);
        
        Position nextPos = iceCream.getPosition().move(iceCream.getFacingDirection());
        assertTrue(board.hasIceBlockAt(nextPos));
    }
    
    /**
     * Prueba que no se crean bloques con helado muerto
     */
    @Test
    public void testCreateIceBlocksWithDeadIceCream() {
        game.startGameWithConfiguration(config);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        Board board = game.getCurrentLevel().getBoard();
        Position nextPos = iceCream.getPosition().move(iceCream.getFacingDirection());
        
        iceCream.eliminate();
        game.createIceBlocks(iceCream);
        
        assertFalse(board.hasIceBlockAt(nextPos));
    }
    
    /**
     * Prueba romper bloques de hielo creados por el jugador
     */
    @Test
    public void testBreakIceBlocks() {
        game.startGameWithConfiguration(config);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        Board board = game.getCurrentLevel().getBoard();
        
        // Crear bloques de hielo (estos tienen playerCreated = true)
        game.createIceBlocks(iceCream);
        Position nextPos = iceCream.getPosition().move(iceCream.getFacingDirection());
        assertTrue(board.hasIceBlockAt(nextPos));
        
        // Romper los bloques
        game.breakIceBlocks(iceCream);
        assertFalse(board.hasIceBlockAt(nextPos));
    }
    
    /**
     * Prueba que NO se rompen bloques del nivel original
     */
    @Test
    public void testCannotBreakLevelBlocks() {
        // Crear configuración con bloques del nivel
        LevelBuilder builder = new LevelBuilder();
        builder.addVanillaIceCream(5, 5)
               .addIceBlock(5, 4); // Bloque del nivel (playerCreated = false)
        LevelConfiguration configWithBlocks = builder.build();
        
        game.startGameWithConfiguration(configWithBlocks);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        Board board = game.getCurrentLevel().getBoard();
        
        // Verificar que el bloque del nivel existe
        Position blockPos = new Position(5, 4);
        assertTrue(board.hasIceBlockAt(blockPos));
        
        // Mirar hacia el bloque
        iceCream.setFacingDirection(Direction.NORTH);
        
        // Intentar romper el bloque del nivel
        game.breakIceBlocks(iceCream);
        
        // El bloque del nivel NO debe romperse
        assertTrue(board.hasIceBlockAt(blockPos));
    }
    
    /**
     * Prueba reiniciar el nivel con configuración
     */
    @Test
    public void testRestartLevelWithConfiguration() {
        game.startGameWithConfiguration(config);
        game.pause();
        
        game.startGameWithConfiguration(config); // Reiniciar manualmente
        
        assertEquals(GameState.PLAYING, game.getState());
        assertNotNull(game.getCurrentLevel());
    }
    
    /**
     * Prueba reiniciar el nivel con índice
     */
    @Test
    public void testRestartLevelWithIndex() {
        game.startGame(0);
        game.pause();
        
        game.restartLevel(0);
        
        assertEquals(GameState.PLAYING, game.getState());
        assertNotNull(game.getCurrentLevel());
    }
    
    /**
     * Prueba terminar el juego
     */
    @Test
    public void testEndGame() {
        game.startGame(0);
        assertEquals(GameState.PLAYING, game.getState());
        
        game.endGame();
        assertEquals(GameState.GAME_OVER, game.getState());
    }
    
    /**
     * Prueba obtener el número del nivel actual
     */
    @Test
    public void testGetCurrentLevelNumber() {
        assertEquals(1, game.getCurrentLevelNumber());
        
        game.startGame(0);
        assertEquals(1, game.getCurrentLevelNumber());
        
        game.startGame(1);
        assertEquals(2, game.getCurrentLevelNumber());
    }
    
    /**
     * Prueba obtener el puntaje total inicial
     */
    @Test
    public void testGetTotalScoreInitial() {
        game.startGame(0);
        assertEquals(0, game.getTotalScore());
    }
    
    /**
     * Prueba que el puntaje total suma correctamente
     */
    @Test
    public void testGetTotalScoreAfterCollecting() {
        game.startGame(0);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        iceCream.addScore(100);
        
        assertEquals(100, game.getTotalScore());
    }
    
    /**
     * Prueba que el puntaje es 0 sin nivel iniciado
     */
    @Test
    public void testGetTotalScoreWithoutLevel() {
        assertEquals(0, game.getTotalScore());
    }
    
    /**
     * Prueba actualización del juego
     */
    @Test
    public void testUpdate() {
        game.startGame(0);
        
        game.update();
        
        assertEquals(GameState.PLAYING, game.getState());
    }
    
    /**
     * Prueba que la actualización no hace nada cuando no está jugando
     */
    @Test
    public void testUpdateWhenNotPlaying() {
        game.startGame(0);
        game.pause();
        
        GameState stateBefore = game.getState();
        game.update();
        GameState stateAfter = game.getState();
        
        assertEquals(stateBefore, stateAfter);
    }
    
    /**
     * Prueba que las oleadas funcionan correctamente
     */
    @Test
    public void testFruitWaves() {
        game.startGame(0); // Nivel 1 tiene 2 oleadas
        
        Level level = game.getCurrentLevel();
        
        // Verificar que hay oleadas configuradas
        assertTrue(level.getTotalFruits() > 0);
        
        // Inicialmente, solo la primera oleada está activa
        int initialFruits = level.getCollectedFruits();
        assertEquals(0, initialFruits);
    }
    
    /**
     * Prueba la integración con LevelTemplate
     */
    @Test
    public void testLevelTemplateIntegration() {
        // Obtener template del nivel 1
        LevelTemplate template = game.getLevelTemplate(0);
        assertNotNull(template);
        
        // Verificar propiedades del template
        assertEquals("Nivel 1 - Fácil", template.getName());
        assertEquals(1, template.getLevelNumber());
        assertEquals(Difficulty.EASY, template.getDifficulty());
        assertNotNull(template.getDescription());
        assertTrue(template.getDescription().contains("Bananos"));
        assertTrue(template.getDescription().contains("Uvas"));
    }

}