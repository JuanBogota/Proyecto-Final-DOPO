package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.*;

/**
 * Pruebas unitarias para la clase BadDopoCream.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
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
     * Prueba que el juego inicia correctamente
     */
    @Test
    public void testStartGame() {
        game.startGame(config);
        
        assertEquals(GameState.PLAYING, game.getState());
        assertNotNull(game.getCurrentLevel());
        assertEquals(1, game.getCurrentLevelNumber());
    }
    
    /**
     * Prueba que el nivel actual se obtiene correctamente
     */
    @Test
    public void testGetCurrentLevel() {
        assertNull(game.getCurrentLevel());
        
        game.startGame(config);
        assertNotNull(game.getCurrentLevel());
        assertEquals(1, game.getCurrentLevel().getLevelNumber());
    }
    
    /**
     * Prueba que el estado del juego cambia correctamente
     */
    @Test
    public void testGetState() {
        assertEquals(GameState.NOT_STARTED, game.getState());
        
        game.startGame(config);
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
        game.startGame(config);
        assertEquals(GameState.PLAYING, game.getState());
        
        game.pause();
        assertEquals(GameState.PAUSED, game.getState());
    }
    
    /**
     * Prueba la funcionalidad de reanudar
     */
    @Test
    public void testResume() {
        game.startGame(config);
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
        game.startGame(config);
        assertEquals(GameState.PLAYING, game.getState());
        
        game.resume();
        assertEquals(GameState.PLAYING, game.getState());
    }
    
    /**
     * Prueba el movimiento de helado
     */
    @Test
    public void testMoveIceCream() {
        game.startGame(config);
        
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
        game.startGame(config);
        
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
        game.startGame(config);
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
        game.startGame(config);
        
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
        game.startGame(config);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        Board board = game.getCurrentLevel().getBoard();
        Position nextPos = iceCream.getPosition().move(iceCream.getFacingDirection());
        
        iceCream.eliminate();
        game.createIceBlocks(iceCream);
        
        assertFalse(board.hasIceBlockAt(nextPos));
    }
    
    /**
     * Prueba romper bloques de hielo
     */
    @Test
    public void testBreakIceBlocks() {
        game.startGame(config);
        
        IceCream iceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        Board board = game.getCurrentLevel().getBoard();
        
        game.createIceBlocks(iceCream);
        Position nextPos = iceCream.getPosition().move(iceCream.getFacingDirection());
        assertTrue(board.hasIceBlockAt(nextPos));
        
        game.breakIceBlocks(iceCream);
        assertFalse(board.hasIceBlockAt(nextPos));
    }
    
    /**
     * Prueba reiniciar el nivel
     */
    @Test
    public void testRestartLevel() {
        game.startGame(config);
        game.pause();
        
        game.restartLevel(config);
        
        assertEquals(GameState.PLAYING, game.getState());
        assertNotNull(game.getCurrentLevel());
    }
    
    /**
     * Prueba terminar el juego
     */
    @Test
    public void testEndGame() {
        game.startGame(config);
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
        
        game.startGame(config);
        assertEquals(1, game.getCurrentLevelNumber());
    }
    
    /**
     * Prueba obtener el puntaje total inicial
     */
    @Test
    public void testGetTotalScoreInitial() {
        game.startGame(config);
        assertEquals(0, game.getTotalScore());
    }
    
    /**
     * Prueba que el puntaje total suma correctamente
     */
    @Test
    public void testGetTotalScoreAfterCollecting() {
        game.startGame(config);
        
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
        game.startGame(config);
        
        game.update();
        
        assertEquals(GameState.PLAYING, game.getState());
    }
    
    /**
     * Prueba que la actualización no hace nada cuando no está jugando
     */
    @Test
    public void testUpdateWhenNotPlaying() {
        game.startGame(config);
        game.pause();
        
        GameState stateBefore = game.getState();
        game.update();
        GameState stateAfter = game.getState();
        
        assertEquals(stateBefore, stateAfter);
    }
}
