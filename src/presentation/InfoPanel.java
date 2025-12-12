package presentation;

import java.awt.*;
import javax.swing.*;

/**
 * Panel que muestra información del juego (tiempo, puntaje, frutas).
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class InfoPanel extends JPanel {
    private JLabel timeLabel;
    private JLabel scoreLabel;
    private JLabel fruitsLabel;
    private JLabel controlsLabel;
    
    /**
     * Constructor del panel de información
     */
    public InfoPanel() {
        setLayout(new GridLayout(4, 1, 5, 5));
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        initializeComponents();
    }
    
    /**
     * Inicializa los componentes del panel
     */
    private void initializeComponents() {
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        
        // Label de tiempo
        timeLabel = createStyledLabel("Tiempo: 180s", labelFont, new Color(60, 120, 180));
        add(timeLabel);
        
        // Label de puntaje
        scoreLabel = createStyledLabel("Puntaje: 0", labelFont, new Color(200, 140, 0));
        add(scoreLabel);
        
        // Label de frutas
        fruitsLabel = createStyledLabel("Frutas: 0/0", labelFont, new Color(160, 80, 160));
        add(fruitsLabel);
        
        // Label de controles
        controlsLabel = new JLabel("<html><b>Controles:</b><br/>" +
                                   "↑↓←→ o WASD: Mover<br/>" +
                                   "ESPACIO: Crear hielo<br/>" +
                                   "SHIFT: Romper hielo<br/>" +
                                   "P: Pausar</html>");
        controlsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        controlsLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        add(controlsLabel);
    }
    
    /**
     * Crea un label con estilo personalizado
     */
    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }
    
    /**
     * Actualiza la información mostrada
     */
    public void updateInfo(int timeRemaining, int score, int fruitsCollected, int totalFruits) {
        
        timeLabel.setText("Tiempo: " + formatTime(timeRemaining));
        if (timeRemaining < 30) {
            timeLabel.setForeground(Color.RED);
        } else if (timeRemaining < 60) {
            timeLabel.setForeground(new Color(255, 140, 0));
        } else {
            timeLabel.setForeground(new Color(60, 120, 180));
        }
        
        scoreLabel.setText("Puntaje: " + score);
        
        fruitsLabel.setText("Frutas: " + fruitsCollected + "/" + totalFruits);
       
        if (fruitsCollected == totalFruits && totalFruits > 0) {
            fruitsLabel.setForeground(new Color(0, 180, 0));
        } else {
            fruitsLabel.setForeground(new Color(160, 80, 160));
        }
    }
    
    /**
     * Formatea el tiempo
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }
}
