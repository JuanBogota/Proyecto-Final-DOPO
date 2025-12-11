package presentation;

import domain.LevelConfiguration;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del juego Bad DOPO Cream.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class MainWindow extends JFrame {
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    private GameController controller;
    private LevelConfiguration currentConfig;
    
    /**
     * Constructor de la ventana principal
     */
    public MainWindow() {
        setTitle("Bad DOPO Cream");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        controller = new GameController(this);
        
        initializeComponents();
        createMenuBar();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Inicializa los componentes de la ventana
     */
    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de juego
        gamePanel = new GamePanel();
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(controller);
        add(gamePanel, BorderLayout.CENTER);
        
        // Panel de información
        infoPanel = new InfoPanel();
        infoPanel.setPreferredSize(new Dimension(200, 480));
        add(infoPanel, BorderLayout.EAST);
        
        // Establecer paneles en el controlador
        controller.setPanels(gamePanel, infoPanel);
        
        // Panel inferior con información adicional
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(new Color(230, 230, 230));
        JLabel authorLabel = new JLabel("Bad DOPO Cream 2025 | Juan Daniel Bogotá y Nicolás Bernal");
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        bottomPanel.add(authorLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Crea la barra de menú
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Juego
        JMenu gameMenu = new JMenu("Juego");
        
        JMenuItem newGameItem = new JMenuItem("Nuevo Juego");
        newGameItem.setAccelerator(KeyStroke.getKeyStroke("control N"));
        newGameItem.addActionListener(e -> startNewGame());
        
        JMenuItem restartItem = new JMenuItem("Reiniciar Nivel");
        restartItem.setAccelerator(KeyStroke.getKeyStroke("control R"));
        restartItem.addActionListener(e -> restartLevel());
        
        JMenuItem pauseItem = new JMenuItem("Pausar/Reanudar");
        pauseItem.setAccelerator(KeyStroke.getKeyStroke("P"));
        pauseItem.addActionListener(e -> togglePause());
        
        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.setAccelerator(KeyStroke.getKeyStroke("control Q"));
        exitItem.addActionListener(e -> exitGame());
        
        gameMenu.add(newGameItem);
        gameMenu.add(restartItem);
        gameMenu.add(pauseItem);
        gameMenu.add(exitItem);
       
        menuBar.add(gameMenu);
    
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Inicia un nuevo juego
     */
    private void startNewGame() {
        ConfigDialog dialog = new ConfigDialog(this);
        LevelConfiguration config = dialog.showDialog();
        
        if (config != null) {
            currentConfig = config;
            controller.startNewGame(config);
            gamePanel.requestFocusInWindow();
        }
    }
    
    /**
     * Reinicia el nivel actual
     */
    private void restartLevel() {
        if (currentConfig != null) {
            int response = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas reiniciar el nivel actual?",
                "Reiniciar Nivel",
                JOptionPane.YES_NO_OPTION
            );
            
            if (response == JOptionPane.YES_OPTION) {
                controller.restartLevel(currentConfig);
                gamePanel.requestFocusInWindow();
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                "No hay un juego en curso. Inicia un nuevo juego primero.",
                "Aviso",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    /**
     * Alterna entre pausar y reanudar el juego
     */
    private void togglePause() {
        if (controller.getGame().getState() == domain.GameState.PLAYING) {
            controller.pauseGame();
            JOptionPane.showMessageDialog(
                this,
                "Juego pausado",
                "Pausa",
                JOptionPane.INFORMATION_MESSAGE
            );
            controller.resumeGame();
            gamePanel.requestFocusInWindow();
        } else if (controller.getGame().getState() == domain.GameState.PAUSED) {
            controller.resumeGame();
            gamePanel.requestFocusInWindow();
        }
    }
    

    /**
     * Sale del juego
     */
    private void exitGame() {
        int response = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro que deseas salir?",
            "Salir",
            JOptionPane.YES_NO_OPTION
        );
        
        if (response == JOptionPane.YES_OPTION) {
            controller.endGame();
            System.exit(0);
        }
    }
    
    /**
     * Método main para ejecutar la aplicación
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
