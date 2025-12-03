package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.border.*;
import domain.*;

/**
 * Main GUI class for the Bad Dopo Cream game.
 * Handles menu and game screens with graphics and user interactions.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class BadDopoCreamGUI extends JFrame {
    
    // Constantes de diseño
    private static final int CELL_SIZE = 40; // Tamaño de cada celda en píxeles
    private static final int BOARD_WIDTH = 15; // Ancho del tablero en celdas
    private static final int BOARD_HEIGHT = 10; // Alto del tablero en celdas
    private static final int FPS = 60; // Frames por segundo
    private static final int UPDATES_PER_SECOND = 60;
    
    // Colores del tema
    private static final Color BACKGROUND_COLOR = new Color(200, 230, 255);
    private static final Color ICE_BLUE = new Color(173, 216, 230);
    private static final Color DARK_BLUE = new Color(70, 130, 180);
    private static final Color MENU_BG = new Color(240, 248, 255);
    
    // Componentes principales
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel menuPanel;
    private GamePanel gamePanel;
    private InfoPanel infoPanel;
    
    // Componentes del menú
    private JComboBox<String> gameModeComboBox;
    private JComboBox<String> levelComboBox;
    private JComboBox<String> flavorComboBox;
    private JButton startButton;
    
    // Lógica del juego
    private BadDopoCream game;
    private Board board;
    private IceCream iceCream;
    private Timer gameTimer;
    private Timer updateTimer;
    
    // Estado del juego
    private boolean gameRunning;
    private int currentLevel;
    
    // Sprites y recursos (aquí se cargarian las imagenes del juego original)
    private HashMap<String, Image> sprites;
    
    /**
     * Constructor principal de la GUI
     */
    public BadDopoCreamGUI() {
        initializeSprites();
        setupFrame();
        createComponents();
        setupLayout();
        setupListeners();
        setVisible(true);
    }
    
    /**
     * Inicializa los sprites del juego
     */
    private void initializeSprites() {
        sprites = new HashMap<>();
        // sprites.put("vanilla", ImageIO.read(new File("sprites/vanilla.png")));
        // sprites.put("strawberry", ImageIO.read(new File("sprites/strawberry.png")));
        // sprites.put("chocolate", ImageIO.read(new File("sprites/chocolate.png")));
        // sprites.put("grape", ImageIO.read(new File("sprites/grape.png")));
    }
    
    /**
     * Configura el JFrame principal
     */
    private void setupFrame() {
        setTitle("Bad DOPO Cream");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
    
    /**
     * Crea todos los componentes de la GUI
     */
    private void createComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createMenuPanel();
        
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(createGameContainer(), "GAME");
    }
    
    /**
     * Crea el panel del menú principal
     */
    private void createMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(MENU_BG);
        
        JPanel titlePanel = createTitlePanel();
        JPanel optionsPanel = createOptionsPanel();
        JPanel buttonPanel = createButtonPanel();
        
        menuPanel.add(titlePanel, BorderLayout.NORTH);
        menuPanel.add(optionsPanel, BorderLayout.CENTER);
        menuPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel del título con el logo
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(MENU_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        JLabel titleLabel = new JLabel("BAD DOPO CREAM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(DARK_BLUE);
        
        panel.add(titleLabel);
        
        return panel;
    }
    
    /**
     * Crea el panel de opciones del menú
     */
    private JPanel createOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(MENU_BG);
        panel.setBorder(new CompoundBorder(
            BorderFactory.createLineBorder(DARK_BLUE, 3, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Para cuadrar el modo de juego
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel modeLabel = new JLabel("Modo de Juego:");
        modeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(modeLabel, gbc);
        
        gbc.gridx = 1;
        gameModeComboBox = new JComboBox<>(new String[]{
            "Player vs Machine",
            "Player vs Player",
            "Machine vs Machine"
        });
        gameModeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(gameModeComboBox, gbc);
        
        // Para cuadrar el nivel Nivel
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel levelLabel = new JLabel("Nivel:");
        levelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(levelLabel, gbc);
        
        gbc.gridx = 1;
        levelComboBox = new JComboBox<>(new String[]{"Nivel 1", "Nivel 2", "Nivel 3"});
        levelComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(levelComboBox, gbc);
        
        // Para cuadrar sabor del helado
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel flavorLabel = new JLabel("Sabor:");
        flavorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(flavorLabel, gbc);
        
        gbc.gridx = 1;
        flavorComboBox = new JComboBox<>(new String[]{"Vainilla", "Fresa", "Chocolate"});
        flavorComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(flavorComboBox, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel de botones del menú
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(MENU_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        
        startButton = new JButton("INICIAR JUEGO");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setPreferredSize(new Dimension(250, 60));
        startButton.setBackground(new Color(100, 200, 100));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        panel.add(startButton);
        
        return panel;
    }
    
    /**
     * Crea el contenedor del juego con el panel de juego y el panel de información
     */
    private JPanel createGameContainer() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(BACKGROUND_COLOR);
        
        gamePanel = new GamePanel();
        infoPanel = new InfoPanel();
        
        container.add(gamePanel, BorderLayout.CENTER);
        container.add(infoPanel, BorderLayout.EAST);
        
        return container;
    }
    
    
    /**
     * Configura el layout principal
     */
    private void setupLayout() {
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Configura los listeners de eventos
     */
    private void setupListeners() {
        
        startButton.addActionListener(e -> startGame());
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        
        setFocusable(true);
    }
    
    /**
     * Inicia el juego con la configuración seleccionada
     */
    private void startGame() {
        
        int gameMode = getSelectedGameMode();
        currentLevel = levelComboBox.getSelectedIndex() + 1;
        int flavor = getSelectedFlavor();
        
        game = new BadDopoCream();
        game.setGameMode(gameMode);
        
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        Level level = new Level(currentLevel);
        level.loadLevel();
        
        Position startPos = new Position(BOARD_WIDTH / 2, BOARD_HEIGHT / 2);
        iceCream = new IceCream(startPos, flavor, board);
        
        loadLevelContent(level);
        
        game.start();
        gameRunning = true;
        
        cardLayout.show(mainPanel, "GAME");
        requestFocus();
        
        startTimers();
    }
    
    /**
     * Carga el contenido del nivel (frutas y enemigos)
     */
    private void loadLevelContent(Level level) {
        HashMap<Integer, Integer> objectiveFruits = level.getObjectiveFruits();
        
        for (Integer fruitType : objectiveFruits.keySet()) {
            int count = objectiveFruits.get(fruitType);
            
            for (int i = 0; i < count; i++) {
                Position pos = findRandomFreePosition();
                Fruit fruit = createFruit(fruitType, pos);
                if (fruit != null) {
                    board.addFruit(fruit);
                }
            }
        }
        
        for (Integer enemyType : level.getEnemyTypes()) {
            Position pos = findRandomFreePosition();
            Enemy enemy = createEnemy(enemyType, pos);
            if (enemy != null) {
                board.addEnemy(enemy);
            }
        }
    }
    
    /**
     * Crea una fruta según su tipo
     */
    private Fruit createFruit(int type, Position pos) {
        return switch (type) {
            case Fruit.UVAS -> new Grape(pos, board);
            case Fruit.PLATANO -> new Banana(pos, board);
            case Fruit.PINA -> new Pineapple(pos, board);
            case Fruit.CEREZA -> new Cherry(pos, board);
            case Fruit.CACTUS -> new Cactus(pos, board);
            default -> null;
        };
    }
    
    /**
     * Crea un enemigo según su tipo
     */
    private Enemy createEnemy(int type, Position pos) {
        Enemy enemy = switch (type) {
            case Enemy.TROLL -> new Troll(pos, board);
            case Enemy.MACETA -> new Pot(pos, board);
            case Enemy.CALAMAR_NARANJA -> new Squid(pos, board);
            case Enemy.NARVAL -> new Narval(pos, board);
            default -> null;
        };
        
        if (enemy instanceof Pot) {
            ((Pot) enemy).setTarget(iceCream);
        } else if (enemy instanceof Squid) {
            ((Squid) enemy).setTarget(iceCream);
        } else if (enemy instanceof Narval) {
            ((Narval) enemy).setTarget(iceCream);
        }
        
        return enemy;
    }
    
    /**
     * Encuentra una posición aleatoria libre en el tablero
     */
    private Position findRandomFreePosition() {
        int maxAttempts = 100;
        for (int i = 0; i < maxAttempts; i++) {
            int x = (int) (Math.random() * BOARD_WIDTH);
            int y = (int) (Math.random() * BOARD_HEIGHT);
            Position pos = new Position(x, y);
            
            if (board.isEmpty(pos)) {
                return pos;
            }
        }
        return new Position(0, 0);
    }
    
    /**
     * Inicia los timers del juego
     */
    private void startTimers() {
        
        updateTimer = new Timer(1000 / UPDATES_PER_SECOND, e -> updateGame());
        updateTimer.start();
    }
    
    /**
     * Actualiza el estado del juego
     */
    private void updateGame() {
        if (!gameRunning) return;
        
        game.update();
        
        for (Enemy enemy : board.getEnemies()) {
            if (enemy.isActive()) {
                enemy.update();
            }
        }
        
        for (Fruit fruit : board.getFruits()) {
            if (!fruit.isCollected()) {
                fruit.update();
            }
        }

        infoPanel.updateInfo();
        
        if (game.checkWinCondition()) {
            handleWin();
        } else if (game.checkLoseCondition()) {
            handleLoss();
        }
    }
    
    /**
     * Maneja la entrada del teclado
     */
    private void handleKeyPress(KeyEvent e) {
        if (!gameRunning) return;
        
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> iceCream.move(Direction.UP);
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> iceCream.move(Direction.DOWN);
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> iceCream.move(Direction.LEFT);
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> iceCream.move(Direction.RIGHT);
            case KeyEvent.VK_SPACE -> iceCream.createIceBlockLine();
            case KeyEvent.VK_SHIFT -> iceCream.breakIceBlocks(iceCream.getDirection());
            case KeyEvent.VK_ESCAPE -> pauseGame();
            case KeyEvent.VK_P -> pauseGame();
        }
    }
    
    /**
     * Pausa el juego
     */
    private void pauseGame() {
        if (gameRunning) {
            gameRunning = false;
            game.pause();
            updateTimer.stop();
            
            int option = JOptionPane.showConfirmDialog(this,
                "Juego pausado\n¿Desea continuar?",
                "Pausa",
                JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                resumeGame();
            } else {
                endGame();
            }
        }
    }
    
    /**
     * Reanuda el juego
     */
    private void resumeGame() {
        gameRunning = true;
        game.resume();
        updateTimer.start();
        requestFocus();
    }
    
    /**
     * Maneja la victoria del jugador
     */
    private void handleWin() {
        gameRunning = false;
        updateTimer.stop();
        gameTimer.stop();
        
        int score = game.getScore();
        
        int option = JOptionPane.showConfirmDialog(this,
            String.format("Felicidades, Has ganado el nivel %d\nPuntuación: %d\n¿Continuar al siguiente nivel?",
                currentLevel, score),
            "¡Victoria!",
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION && currentLevel < 3) {
            currentLevel++;
            game.nextLevel();
            startGame();
        } else {
            endGame();
        }
    }
    
    /**
     * Maneja la derrota del jugador
     */
    private void handleLoss() {
        gameRunning = false;
        updateTimer.stop();
        gameTimer.stop();
        
        int option = JOptionPane.showConfirmDialog(this,
            "Has perdido\n¿Intentar de nuevo?",
            "Game Over",
            JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            game.restart();
            startGame();
        } else {
            endGame();
        }
    }
    
    /**
     * Termina el juego y vuelve al menú
     */
    private void endGame() {
        gameRunning = false;
        if (updateTimer != null) updateTimer.stop();
        if (gameTimer != null) gameTimer.stop();
        
        cardLayout.show(mainPanel, "MENU");
    }
    
    /**
     * Obtiene el modo de juego seleccionado
     */
    private int getSelectedGameMode() {
        return switch (gameModeComboBox.getSelectedIndex()) {
            case 0 -> BadDopoCream.PLAYER_VS_MACHINE;
            case 1 -> BadDopoCream.PLAYER_VS_PLAYER;
            case 2 -> BadDopoCream.MACHINE_VS_MACHINE;
            default -> BadDopoCream.PLAYER_VS_MACHINE;
        };
    }
    
    /**
     * Obtiene el sabor seleccionado
     */
    private int getSelectedFlavor() {
        return switch (flavorComboBox.getSelectedIndex()) {
            case 0 -> IceCream.VANILLA;
            case 1 -> IceCream.STRAWBERRY;
            case 2 -> IceCream.CHOCOLATE;
            default -> IceCream.VANILLA;
        };
    }
    
    /**
     * Panel personalizado para dibujar el juego
     */
    private class GamePanel extends JPanel {
        
        public GamePanel() {
            setPreferredSize(new Dimension(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE));
            setBackground(BACKGROUND_COLOR);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            if (board == null) return;
            
            drawGrid(g2d);
            drawIceBlocks(g2d);
            drawFruits(g2d);
            drawEnemies(g2d);
            drawIceCream(g2d);
        }
        
        /**
         * Dibuja la cuadrícula del tablero
         */
        private void drawGrid(Graphics2D g2d) {
            g2d.setColor(new Color(200, 200, 200, 100));
            
            for (int x = 0; x <= BOARD_WIDTH; x++) {
                g2d.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);
            }
            
            for (int y = 0; y <= BOARD_HEIGHT; y++) {
                g2d.drawLine(0, y * CELL_SIZE, BOARD_WIDTH * CELL_SIZE, y * CELL_SIZE);
            }
        }
        
        /**
         * Dibuja los bloques de hielo
         */
        private void drawIceBlocks(Graphics2D g2d) {
            for (IceBlock block : board.getIceBlocks()) {
                if (block.isActive()) {
                    Position pos = block.getPosition();
                    int x = pos.getX() * CELL_SIZE;
                    int y = pos.getY() * CELL_SIZE;
                    
                
                    // g2d.drawImage(sprites.get("ice_block"), x, y, CELL_SIZE, CELL_SIZE, null);
            
                    g2d.setColor(ICE_BLUE);
                    g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    g2d.setColor(ICE_BLUE);
                    g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        
        /**
         * Dibuja las frutas
         */
        private void drawFruits(Graphics2D g2d) {
            for (Fruit fruit : board.getFruits()) {
                if (!fruit.isCollected() && fruit.isActive()) {
                    Position pos = fruit.getPosition();
                    int x = pos.getX() * CELL_SIZE;
                    int y = pos.getY() * CELL_SIZE;
                    
                
                    // String spriteName = getFruitSpriteName(fruit.getType());
                    // g2d.drawImage(sprites.get(spriteName), x, y, CELL_SIZE, CELL_SIZE, null);
                    
                    // Por ahora, dibujar círculos de colores
                    Color fruitColor = getFruitColor(fruit.getType());
                    g2d.setColor(fruitColor);
                    g2d.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    g2d.setColor(fruitColor.darker());
                    g2d.drawOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                }
            }
        }
        
        /**
         * Dibuja los enemigos
         */
        private void drawEnemies(Graphics2D g2d) {
            for (Enemy enemy : board.getEnemies()) {
                if (enemy.isActive()) {
                    Position pos = enemy.getPosition();
                    int x = pos.getX() * CELL_SIZE;
                    int y = pos.getY() * CELL_SIZE;
                    
                    // String spriteName = getEnemySpriteName(enemy.getType());
                    // g2d.drawImage(sprites.get(spriteName), x, y, CELL_SIZE, CELL_SIZE, null);
                    
                    // Por ahora, dibujar rectángulos rojos
                    g2d.setColor(Color.RED);
                    g2d.fillRect(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.drawRect(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                }
            }
        }
        
        /**
         * Dibuja el helado del jugador
         */
        private void drawIceCream(Graphics2D g2d) {
            if (iceCream != null && iceCream.isActive()) {
                Position pos = iceCream.getPosition();
                int x = pos.getX() * CELL_SIZE;
                int y = pos.getY() * CELL_SIZE;
            
                // String spriteName = getIceCreamSpriteName(iceCream.getFlavor());
                // g2d.drawImage(sprites.get(spriteName), x, y, CELL_SIZE, CELL_SIZE, null);
                
                // Por ahora, dibujar un triángulo (cono de helado)
                Color iceCreamColor = getIceCreamColor(iceCream.getFlavor());
                
                // Cono
                int[] xPoints = {x + CELL_SIZE / 2, x + 5, x + CELL_SIZE - 5};
                int[] yPoints = {y + CELL_SIZE - 5, y + CELL_SIZE / 2, y + CELL_SIZE / 2};
                g2d.setColor(new Color(222, 184, 135));
                g2d.fillPolygon(xPoints, yPoints, 3);
                
                // Helado
                g2d.setColor(iceCreamColor);
                g2d.fillOval(x + 8, y + 8, CELL_SIZE - 16, CELL_SIZE - 16);
                g2d.setColor(iceCreamColor.darker());
                g2d.drawOval(x + 8, y + 8, CELL_SIZE - 16, CELL_SIZE - 16);
            }
        }
        
        /**
         * Obtiene el color de una fruta según su tipo
         */
        private Color getFruitColor(int type) {
            return switch (type) {
                case Fruit.UVAS -> new Color(128, 0, 128);
                case Fruit.PLATANO -> Color.YELLOW;
                case Fruit.PINA -> new Color(255, 215, 0);
                case Fruit.CEREZA -> Color.RED;
                case Fruit.CACTUS -> Color.GREEN;
                default -> Color.GRAY;
            };
        }
        
        /**
         * Obtiene el color del helado según su sabor
         */
        private Color getIceCreamColor(int flavor) {
            return switch (flavor) {
                case IceCream.VANILLA -> new Color(255, 250, 205);
                case IceCream.STRAWBERRY -> new Color(255, 192, 203);
                case IceCream.CHOCOLATE -> new Color(139, 69, 19);
                default -> Color.WHITE;
            };
        }
    }
    
    /**
     * Panel de información lateral
     */
    private class InfoPanel extends JPanel {
        private JLabel timeLabel;
        private JLabel scoreLabel;
        private JLabel fruitsLabel;
        private JLabel levelLabel;
        
        public InfoPanel() {
            setPreferredSize(new Dimension(200, BOARD_HEIGHT * CELL_SIZE));
            setBackground(new Color(240, 240, 240));
            setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            
            add(Box.createVerticalStrut(20));
            
            JLabel title = new JLabel("INFORMACIÓN");
            title.setFont(new Font("Arial", Font.BOLD, 18));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(title);
            
            add(Box.createVerticalStrut(30));
            
            levelLabel = createInfoLabel("Nivel: 1");
            add(levelLabel);
            add(Box.createVerticalStrut(15));
            
            timeLabel = createInfoLabel("Tiempo: 3:00");
            add(timeLabel);
            add(Box.createVerticalStrut(15));
            
            scoreLabel = createInfoLabel("Puntaje: 0");
            add(scoreLabel);
            add(Box.createVerticalStrut(15));
            
            fruitsLabel = createInfoLabel("Frutas: 0");
            add(fruitsLabel);
            
            add(Box.createVerticalGlue());
            
            JPanel controlsPanel = createControlsPanel();
            add(controlsPanel);
            
            add(Box.createVerticalStrut(20));
        }
        
        /**
         * Crea una etiqueta de información
         */
        private JLabel createInfoLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        }
        
        /**
         * Crea el panel de controles
         */
        private JPanel createControlsPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(240, 240, 240));
            panel.setBorder(BorderFactory.createTitledBorder("Controles"));
            
            panel.add(new JLabel("↑/W: Arriba"));
            panel.add(new JLabel("↓/S: Abajo"));
            panel.add(new JLabel("←/A: Izquierda"));
            panel.add(new JLabel("→/D: Derecha"));
            panel.add(new JLabel("ESPACIO: Crear hielo"));
            panel.add(new JLabel("SHIFT: Romper hielo"));
            panel.add(new JLabel("P/ESC: Pausa"));
            
            return panel;
        }
        
        /**
         * Actualiza la información mostrada
         */
        public void updateInfo() {
            if (game != null) {
                int timeRemaining = game.getTimeRemaining();
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                timeLabel.setText(String.format("Tiempo: %d:%02d", minutes, seconds));
                
                scoreLabel.setText("Puntaje: " + game.getScore());
                
                if (iceCream != null) {
                    fruitsLabel.setText("Frutas: " + iceCream.getCollectedFruits());
                }
                
                levelLabel.setText("Nivel: " + currentLevel);
            }
        }
    }
    
    /**
     * Método principal para iniciar la aplicación
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BadDopoCreamGUI gui = new BadDopoCreamGUI();
        });
    }
}