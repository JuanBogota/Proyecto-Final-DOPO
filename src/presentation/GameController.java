package presentation;

import domain.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controlador del juego que conecta la presentación con el dominio.
 * Maneja las entradas del usuario y actualiza la vista.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class GameController implements KeyListener {
    private BadDopoCream game;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private MainWindow mainWindow;
    private Timer gameTimer;
    private IceCream playerIceCream;
    
    /**
     * Constructor del controlador
     */
    public GameController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.game = new BadDopoCream();
    }
    
    /**
     * Establece los paneles de la vista
     */
    public void setPanels(GamePanel gamePanel, InfoPanel infoPanel) {
        this.gamePanel = gamePanel;
        this.infoPanel = infoPanel;
    }
    
    /**
     * Inicia un nuevo juego con la configuración especificada
     */
    public void startNewGame(LevelConfiguration config) {
        game.startGame(config);
        
        if (!game.getCurrentLevel().getBoard().getIceCreams().isEmpty()) {
            playerIceCream = game.getCurrentLevel().getBoard().getIceCreams().get(0);
        }
        
        startGameLoop();
        
        updateView();
    }
    
    /**
     * Inicia el loop principal del juego
     */
    private void startGameLoop() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        
        gameTimer = new Timer(100, e -> {
            if (game.getState() == GameState.PLAYING) {
                game.update();
                updateView();
                checkGameStatus();
            }
        });
        
        gameTimer.start();
    }
    
    /**
     * Actualiza la vista con el estado actual del juego
     */
    private void updateView() {
        if (gamePanel != null) {
            gamePanel.setLevel(game.getCurrentLevel());
            gamePanel.repaint();
        }
        
        if (infoPanel != null) {
            infoPanel.updateInfo(
                game.getCurrentLevel().getTimeRemaining(),
                game.getTotalScore(),
                game.getCurrentLevel().getCollectedFruits(),
                game.getCurrentLevel().getTotalFruits()
            );
        }
    }
    
    /**
     * Verifica el estado del juego (victoria/derrota)
     */
    private void checkGameStatus() {
        GameState state = game.getState();
        
        if (state == GameState.LEVEL_COMPLETED) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(
                mainWindow,
                "¡Nivel completado!\nPuntaje: " + game.getTotalScore(),
                "¡Victoria!",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else if (state == GameState.GAME_OVER) {
            gameTimer.stop();
            JOptionPane.showMessageDialog(
                mainWindow,
                "Game Over\nPuntaje final: " + game.getTotalScore(),
                "Juego Terminado",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Pausa el juego
     */
    public void pauseGame() {
        if (game.getState() == GameState.PLAYING) {
            game.pause();
            if (gameTimer != null) {
                gameTimer.stop();
            }
        }
    }
    
    /**
     * Reanuda el juego
     */
    public void resumeGame() {
        if (game.getState() == GameState.PAUSED) {
            game.resume();
            if (gameTimer != null) {
                gameTimer.start();
            }
        }
    }
    
    /**
     * Reinicia el nivel actual
     */
    public void restartLevel(LevelConfiguration config) {
        game.restartLevel(config);
        updateView();
    }
    
    /**
     * Termina el juego
     */
    public void endGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        game.endGame();
    }
    
    /**
     * Obtiene el juego actual
     */
    public BadDopoCream getGame() {
        return game;
    }
    
   /** 
    * Implementación de KeyListener
    */
    @Override
    public void keyPressed(KeyEvent e) {
        if (game.getState() != GameState.PLAYING || playerIceCream == null) {
            return;
        }
        
        Direction direction = null;
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> direction = Direction.NORTH;
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> direction = Direction.SOUTH;
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> direction = Direction.WEST;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> direction = Direction.EAST;
            case KeyEvent.VK_SPACE -> {
                game.createIceBlocks(playerIceCream);
                updateView();
            }
            case KeyEvent.VK_SHIFT -> {
                game.breakIceBlocks(playerIceCream);
                updateView();
            }
            case KeyEvent.VK_P -> {
                if (game.getState() == GameState.PLAYING) {
                    pauseGame();
                } else if (game.getState() == GameState.PAUSED) {
                    resumeGame();
                }
            }
        }
        
        if (direction != null) {
            game.moveIceCream(playerIceCream, direction);
            updateView();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // No lo usamos
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // No lo usamos
    }
    
}
