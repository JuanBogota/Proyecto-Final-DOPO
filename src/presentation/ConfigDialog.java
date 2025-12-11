package presentation;

import domain.*;
import javax.swing.*;
import java.awt.*;

/**
 * Diálogo para configurar un nuevo nivel del juego.
 * 
 * @author Juan Daniel Bogotá Fuentes
 * @author Nicolás Felipe Bernal Gallo
 * @version 1.0
 */
public class ConfigDialog extends JDialog {
    private JComboBox<String> levelPresetCombo;
    private LevelConfiguration selectedConfig;
    private boolean accepted;
    
    /**
     * Constructor del diálogo de configuración
     */
    public ConfigDialog(Frame parent) {
        super(parent, "Configurar Nivel", true);
        setSize(400, 300);
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
        levelPresetCombo = new JComboBox<>(new String[]{
            "Nivel 1 - Fácil",
            "Nivel 2 - Medio",
            "Nivel 3 - Difícil"
        });
        mainPanel.add(levelPresetCombo, gbc);
        
        // Descripción del nivel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JTextArea descriptionArea = new JTextArea(8, 30);
        descriptionArea.setEditable(false);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setBackground(new Color(240, 240, 240));
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
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
            selectedConfig = createConfiguration(levelPresetCombo.getSelectedIndex());
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
     * Crea la configuración del nivel según el preset seleccionado
     */
    private LevelConfiguration createConfiguration(int preset) {
        LevelBuilder builder = new LevelBuilder();
        
        // Agregar helado del jugador
        builder.addVanillaIceCream(12, 7);
        
        // Configuración común
        switch (preset) {
            case 0 -> configureLevel1(builder);
            case 1 -> configureLevel2(builder);
            case 2 -> configureLevel3(builder);
        }
        
        return builder.build();
    }
    
    /**
     * Configura el nivel 1 (fácil)
     */
    private void configureLevel1(LevelBuilder builder) {
        // Frutas distribuidas
        builder.addGrape(3, 3)
               .addGrape(21, 3)
               .addGrape(3, 11)
               .addGrape(21, 11)
               .addBanana(12, 3)
               .addBanana(6, 7)
               .addBanana(18, 7)
               .addBanana(12, 11);
        
        // Enemigos básicos
        builder.addTroll(5, 5)
               .addTroll(19, 9);
        
        // Obstáculos simples
        builder.addHorizontalIceBlocks(10, 14, 5)
               .addHorizontalIceBlocks(10, 14, 9);
    }
    
    /**
     * Configura el nivel 2 (medio)
     */
    private void configureLevel2(LevelBuilder builder) {
        // Más frutas
        builder.addGrape(2, 2)
               .addGrape(22, 2)
               .addGrape(2, 12)
               .addGrape(22, 12)
               .addGrape(12, 2)
               .addGrape(12, 12)
               .addBanana(6, 7)
               .addBanana(18, 7)
               .addBanana(12, 4)
               .addBanana(12, 10);
        
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
    
    /**
     * Configura el nivel 3 (difícil)
     */
    private void configureLevel3(LevelBuilder builder) {
        // Muchas frutas dispersas
        builder.addGrape(1, 1)
               .addGrape(23, 1)
               .addGrape(1, 13)
               .addGrape(23, 13)
               .addGrape(6, 4)
               .addGrape(18, 4)
               .addGrape(6, 10)
               .addGrape(18, 10)
               .addBanana(12, 1)
               .addBanana(12, 13)
               .addBanana(1, 7)
               .addBanana(23, 7);
        
        // Muchos enemigos
        builder.addTroll(3, 3)
               .addTroll(21, 11)
               .addPot(8, 7)
               .addPot(16, 7);
        
        // Laberinto de obstáculos
        builder.addHorizontalIceBlocks(5, 8, 4)
               .addHorizontalIceBlocks(16, 19, 4)
               .addHorizontalIceBlocks(5, 8, 10)
               .addHorizontalIceBlocks(16, 19, 10)
               .addVerticalIceBlocks(10, 2, 5)
               .addVerticalIceBlocks(14, 2, 5)
               .addVerticalIceBlocks(10, 9, 12)
               .addVerticalIceBlocks(14, 9, 12);
    }
    
    /**
     * Muestra el diálogo y retorna la configuración seleccionada
     */
    public LevelConfiguration showDialog() {
        accepted = false;
        selectedConfig = null;
        setVisible(true);
        return accepted ? selectedConfig : null;
    }
}
