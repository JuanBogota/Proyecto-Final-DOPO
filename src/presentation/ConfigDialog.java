package presentation;

import domain.BadDopoCream;
import domain.LevelTemplate;
import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Diálogo para configurar un nuevo nivel del juego.
 * Solo depende de BadDopoCream
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class ConfigDialog extends JDialog {
    private JComboBox<String> levelPresetCombo;
    private int selectedLevelIndex;
    private boolean accepted;
    private BadDopoCream game;
    
    /**
     * Constructor del diálogo de configuración
     */
    public ConfigDialog(Frame parent, BadDopoCream game) {
        super(parent, "Configurar Nivel", true);
        this.game = game;
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        initializeComponents();
    }
    
    /**
     * Inicializa los componentes del diálogo
     */
    private void initializeComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Título
        JLabel titleLabel = new JLabel("Selecciona un Nivel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Selector de nivel preset
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel levelLabel = new JLabel("Nivel:");
        mainPanel.add(levelLabel, gbc);
        
        gbc.gridx = 1;
        // Obtener niveles disponibles desde el juego
        List<LevelTemplate> levels = game.getAvailableLevels();
        String[] levelNames = levels.stream()
            .map(LevelTemplate::getName)
            .toArray(String[]::new);
        
        levelPresetCombo = new JComboBox<>(levelNames);
        mainPanel.add(levelPresetCombo, gbc);
        
        // Descripción del nivel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JTextArea descriptionArea = new JTextArea(10, 30);
        descriptionArea.setEditable(false);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setBackground(new Color(240, 240, 240));
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Mostrar descripción inicial
        if (!levels.isEmpty()) {
            descriptionArea.setText(levels.get(0).getDescription());
        }
        
        // Listener para actualizar descripción al cambiar nivel
        levelPresetCombo.addActionListener(e -> {
            int selected = levelPresetCombo.getSelectedIndex();
            if (selected >= 0 && selected < levels.size()) {
                descriptionArea.setText(levels.get(selected).getDescription());
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        mainPanel.add(scrollPane, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton startButton = new JButton("Iniciar Juego");
        startButton.setBackground(new Color(60, 179, 113));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            selectedLevelIndex = levelPresetCombo.getSelectedIndex();
            accepted = true;
            dispose();
        });
        
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            accepted = false;
            dispose();
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Muestra el diálogo y retorna el índice del nivel seleccionado
     * @return Índice del nivel seleccionado, o -1 si se canceló
     */
    public int showDialog() {
        accepted = false;
        selectedLevelIndex = -1;
        setVisible(true);
        return accepted ? selectedLevelIndex : -1;
    }
}
