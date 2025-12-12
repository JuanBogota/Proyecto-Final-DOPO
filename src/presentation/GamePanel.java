package presentation;

import domain.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Panel donde se dibuja el tablero del juego.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class GamePanel extends JPanel {
    private Level level;
    private static final int CELL_SIZE = 32; // Tamaño de cada celda en pÃ­xeles
    private Map<String, Color> colorMap;
    private BufferedImage startScreenImage;
    private BadDopoCreamGUI mainWindow;
    
    /**
     * Constructor del panel de juego
     */
    public GamePanel() {
        setBackground(new Color(240, 248, 255)); // Color de fondo azul claro
        setPreferredSize(new Dimension(800, 480));
        initializeColorMap();
        loadStartScreen();
        setupMouseListener();
    }
    
    /**
     * Establece la ventana principal
     */
    public void setMainWindow(BadDopoCreamGUI mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    /**
     * Carga la imagen de la pantalla de inicio
     */
    private void loadStartScreen() {
        try {
            // Usar ruta absoluta del proyecto
            File imageFile = new File("resources/images/start_screen.png");
            
            if (!imageFile.exists()) {
                // Intentar ruta alternativa
                imageFile = new File("src/resources/images/start_screen.png");
            }
            
            if (imageFile.exists()) {
                startScreenImage = ImageIO.read(imageFile);
                System.out.println("Imagen cargada exitosamente desde: " + imageFile.getAbsolutePath());
            } else {
                System.err.println("No se encontró la imagen en: " + imageFile.getAbsolutePath());
                startScreenImage = null;
            }
            
        } catch (Exception e) {
            System.err.println("Error cargando imagen de inicio: " + e.getMessage());
            e.printStackTrace();
            startScreenImage = null;
        }
    }
    
    /**
     * Configura el listener del mouse para detectar clicks en el botón START
     */
    private void setupMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (level == null && startScreenImage != null) {
                    // Calcular área del botón START
                    // La imagen tiene el botón START en la parte inferior central
                    int imgWidth = Math.min(startScreenImage.getWidth(), getWidth());
                    int imgHeight = Math.min(startScreenImage.getHeight(), getHeight());
                    int x = (getWidth() - imgWidth) / 2;
                    int y = (getHeight() - imgHeight) / 2;
                    
                    // Área aproximada del botón START
                    int buttonX = x + imgWidth / 2 - 100;
                    int buttonY = y + imgHeight - 100;
                    int buttonWidth = 200;
                    int buttonHeight = 60;
                    
                    if (e.getX() >= buttonX && e.getX() <= buttonX + buttonWidth &&
                        e.getY() >= buttonY && e.getY() <= buttonY + buttonHeight) {
                        if (mainWindow != null) {
                            mainWindow.startNewGameFromPanel();
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Inicializa el mapa de colores para los diferentes objetos
     */
    private void initializeColorMap() {
        colorMap = new HashMap<>();
        
        // Helados
        colorMap.put("VANILLA_ICECREAM", new Color(255, 250, 235));
        colorMap.put("STRAWBERRY_ICECREAM", new Color(255, 192, 203));
        colorMap.put("CHOCOLATE_ICECREAM", new Color(139, 90, 43));
        
        // Frutas
        colorMap.put("GRAPE", new Color(128, 0, 128));
        colorMap.put("BANANA", new Color(255, 255, 0));
        
        // Enemigos
        colorMap.put("TROLL", new Color(34, 139, 34));
        colorMap.put("POT", new Color(210, 105, 30));
        
        // Obstáculos
        colorMap.put("ICE_BLOCK", new Color(173, 216, 230));
    }
    
    /**
     * Establece el nivel a dibujar
     */
    public void setLevel(Level level) {
        this.level = level;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (level == null) {
            drawWelcomeScreen(g);
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Board board = level.getBoard();
        
        // Calcular offset para centrar el tablero
        int offsetX = (getWidth() - board.getWidth() * CELL_SIZE) / 2;
        int offsetY = (getHeight() - board.getHeight() * CELL_SIZE) / 2;
        
        // Dibujar grid de fondo
        drawGrid(g2d, board, offsetX, offsetY);
        
        // Dibujar todos los objetos del tablero
        for (GameObject obj : board.getAllObjects()) {
            drawGameObject(g2d, obj, offsetX, offsetY);
        }
    }
    
    /**
     * Dibuja la pantalla de bienvenida
     */
    private void drawWelcomeScreen(Graphics g) {
        if (startScreenImage != null) {
            // Calcular dimensiones para centrar la imagen
            int imgWidth = startScreenImage.getWidth();
            int imgHeight = startScreenImage.getHeight();
            
            // Escalar la imagen si es necesario para que quepa en el panel
            double scale = Math.min(
                (double) getWidth() / imgWidth,
                (double) getHeight() / imgHeight
            );
            
            int scaledWidth = (int) (imgWidth * scale);
            int scaledHeight = (int) (imgHeight * scale);
            
            int x = (getWidth() - scaledWidth) / 2;
            int y = (getHeight() - scaledHeight) / 2;
            
            g.drawImage(startScreenImage, x, y, scaledWidth, scaledHeight, this);

            System.out.println("Dibujando imagen en pantalla");
        } else {
            // Fallback si no se pudo cargar la imagen
            g.setColor(new Color(100, 150, 255));
            g.setFont(new Font("Arial", Font.BOLD, 36));
            
            String title = "BAD DOPO CREAM";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(title)) / 2;
            int y = getHeight() / 2;
            
            g.drawString(title, x, y);
            
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            String instruction = "Presiona 'Nuevo Juego' para comenzar";
            fm = g.getFontMetrics();
            x = (getWidth() - fm.stringWidth(instruction)) / 2;
            
            g.drawString(instruction, x, y + 50);
        }
    }
    
    /**
     * Dibuja el grid del tablero
     */
    private void drawGrid(Graphics2D g2d, Board board, int offsetX, int offsetY) {
        g2d.setColor(new Color(200, 200, 200, 100));
        
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                int px = offsetX + x * CELL_SIZE;
                int py = offsetY + y * CELL_SIZE;
                g2d.drawRect(px, py, CELL_SIZE, CELL_SIZE);
            }
        }
    }
    
    /**
     * Dibuja un objeto del juego
     */
    private void drawGameObject(Graphics2D g2d, GameObject obj, int offsetX, int offsetY) {
        Position pos = obj.getPosition();
        int x = offsetX + pos.getX() * CELL_SIZE;
        int y = offsetY + pos.getY() * CELL_SIZE;
        
        String type = obj.getType();
        Color color = colorMap.getOrDefault(type, Color.GRAY);
        
        if (obj instanceof IceCream iceCream) {
            drawIceCream(g2d, iceCream, x, y, color);
        } else if (obj instanceof Fruit fruit) {
            drawFruit(g2d, fruit, x, y, color);
        } else if (obj instanceof Enemy) {
            drawEnemy(g2d, obj, x, y, color);
        } else if (obj instanceof IceBlock) {
            drawIceBlock(g2d, x, y, color);
        }
    }
    
    /**
     * Dibuja un helado
     */
    private void drawIceCream(Graphics2D g2d, IceCream iceCream, int x, int y, Color color) {
        if (!iceCream.isAlive()) {
            return; // No dibujar helados muertos
        }
        
        // Cuerpo del helado (cono)
        int[] xPoints = {x + CELL_SIZE/2, x + CELL_SIZE/4, x + 3*CELL_SIZE/4};
        int[] yPoints = {y + CELL_SIZE - 2, y + CELL_SIZE/2, y + CELL_SIZE/2};
        g2d.setColor(new Color(210, 180, 140));
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(xPoints, yPoints, 3);
        
        // Bola de helado
        g2d.setColor(color);
        g2d.fillOval(x + CELL_SIZE/4 - 2, y + 4, CELL_SIZE/2 + 4, CELL_SIZE/2 + 4);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x + CELL_SIZE/4 - 2, y + 4, CELL_SIZE/2 + 4, CELL_SIZE/2 + 4);
        
        // Ojos
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x + CELL_SIZE/3, y + CELL_SIZE/4, 4, 4);
        g2d.fillOval(x + 2*CELL_SIZE/3 - 4, y + CELL_SIZE/4, 4, 4);
        
        // Indicador de dirección
        drawDirectionIndicator(g2d, iceCream.getFacingDirection(), x, y);
    }
    
    /**
     * Dibuja un indicador de dirección
     */
    private void drawDirectionIndicator(Graphics2D g2d, Direction direction, int x, int y) {
        g2d.setColor(Color.RED);
        int centerX = x + CELL_SIZE/2;
        int centerY = y + CELL_SIZE/2;
        
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];
        
        switch (direction) {
            case NORTH -> {
                xPoints = new int[]{centerX, centerX - 3, centerX + 3};
                yPoints = new int[]{y + 2, y + 8, y + 8};
            }
            case SOUTH -> {
                xPoints = new int[]{centerX, centerX - 3, centerX + 3};
                yPoints = new int[]{y + CELL_SIZE - 2, y + CELL_SIZE - 8, y + CELL_SIZE - 8};
            }
            case WEST -> {
                xPoints = new int[]{x + 2, x + 8, x + 8};
                yPoints = new int[]{centerY, centerY - 3, centerY + 3};
            }
            case EAST -> {
                xPoints = new int[]{x + CELL_SIZE - 2, x + CELL_SIZE - 8, x + CELL_SIZE - 8};
                yPoints = new int[]{centerY, centerY - 3, centerY + 3};
            }
        }
        
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
    
    /**
     * Dibuja una fruta
     */
    private void drawFruit(Graphics2D g2d, Fruit fruit, int x, int y, Color color) {
        if (fruit.isCollected()) {
            return;
        }
        
        g2d.setColor(color);
        g2d.fillOval(x + CELL_SIZE/4, y + CELL_SIZE/4, CELL_SIZE/2, CELL_SIZE/2);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x + CELL_SIZE/4, y + CELL_SIZE/4, CELL_SIZE/2, CELL_SIZE/2);
        
        // Dibuja detalles específicos según el tipo
        if (fruit instanceof Grape) {
            // uva 
            g2d.setColor(color.darker());
            g2d.fillOval(x + CELL_SIZE/3, y + CELL_SIZE/3 - 3, 6, 6);
            g2d.fillOval(x + CELL_SIZE/2, y + CELL_SIZE/3 - 3, 6, 6);
        } else if (fruit instanceof Banana) {
            // Platano
            g2d.setColor(color.darker());
            g2d.drawArc(x + CELL_SIZE/4, y + CELL_SIZE/4, CELL_SIZE/2, CELL_SIZE/2, 45, 270);
        }
    }
    
    /**
     * Dibuja un enemigo
     */
    private void drawEnemy(Graphics2D g2d, GameObject enemy, int x, int y, Color color) {
        // Cuerpo del enemigo
        g2d.setColor(color);
        g2d.fillRoundRect(x + 4, y + 4, CELL_SIZE - 8, CELL_SIZE - 8, 8, 8);
        g2d.setColor(color.darker());
        g2d.drawRoundRect(x + 4, y + 4, CELL_SIZE - 8, CELL_SIZE - 8, 8, 8);
        
        // Ojos amenazantes
        g2d.setColor(Color.RED);
        g2d.fillOval(x + CELL_SIZE/3 - 2, y + CELL_SIZE/3, 6, 6);
        g2d.fillOval(x + 2*CELL_SIZE/3 - 4, y + CELL_SIZE/3, 6, 6);
        
        // Boca
        g2d.setColor(Color.BLACK);
        g2d.drawArc(x + CELL_SIZE/4, y + CELL_SIZE/2, CELL_SIZE/2, CELL_SIZE/4, 180, 180);
    }
    
    /**
     * Dibuja un bloque de hielo
     */
    private void drawIceBlock(Graphics2D g2d, int x, int y, Color color) {
        
        g2d.setColor(color);
        g2d.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
        
        //Bordeeee
        g2d.setColor(color.darker());
        g2d.drawRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
    }
}