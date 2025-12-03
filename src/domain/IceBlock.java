package domain;

/**
 * Class representing an Ice Block in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class IceBlock extends GameObject {
    private IceCream createdBy;
    
    /**
     * Constructor
     * @param position Posición del bloque
     * @param creator Helado que creó el bloque
     */
    public IceBlock(Position position, IceCream creator) {
        super(position);
        this.createdBy = creator;
    }
    
    /**
     * Obtiene el helado que creó este bloque
     * @return Helado creador
     */
    public IceCream getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Destruye el bloque de hielo
     */
    public void destroy() {
        this.deactivate();
    }
    
    @Override
    public void update() {
        // Los bloques de hielo no necesitan actualización
    }
}
