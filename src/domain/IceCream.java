package domain;

/**
 * Represents the Ice Cream player character in the Bad Dopo Cream game.
 * 
 * @author Nicolas Felipe Bernal Gallo
 * @author Juan Daniel Bogota Fuentes
 * @version 3.0 - Refactorizado con clase Direction
 */
public class IceCream extends GameObject {
    // Constantes para sabores de helado
    public static final int VANILLA = 1;
    public static final int STRAWBERRY = 2;
    public static final int CHOCOLATE = 3;
    
    private int flavor;
    private int direction;
    private int speed;
    private int collectedFruits;
    private boolean isAlive;
    private Board board;
    
    /**
     * Constructor
     * @param position Posición inicial del helado
     * @param flavor Sabor del helado (usar constantes VANILLA, STRAWBERRY, CHOCOLATE)
     * @param board Referencia al tablero del juego
     */
    public IceCream(Position position, int flavor, Board board) {
        super(position);
        this.flavor = flavor;
        this.direction = Direction.DOWN;
        this.speed = 1;
        this.collectedFruits = 0;
        this.isAlive = true;
        this.board = board;
    }
    
    /**
     * Establece el tablero de juego
     * @param board Tablero del juego
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    
    /**
     * Obtiene el sabor del helado
     * @return Sabor
     */
    public int getFlavor() {
        return flavor;
    }
    
    /**
     * Obtiene la dirección actual
     * @return Dirección
     */
    public int getDirection() {
        return direction;
    }
    
    /**
     * Obtiene la cantidad de frutas recolectadas
     * @return Cantidad de frutas
     */
    public int getCollectedFruits() {
        return collectedFruits;
    }
    
    /**
     * Verifica si el helado está vivo
     * @return true si está vivo, false en caso contrario
     */
    public boolean isAlive() {
        return isAlive;
    }
    
    /**
     * Mueve el helado en la dirección especificada
     * @param dir Dirección del movimiento (Direction.UP, DOWN, LEFT, RIGHT)
     * @return true si el movimiento fue exitoso, false si no se pudo mover
     */
    public boolean move(int dir) {
        if (!isAlive) {
            return false;
        }
        
        this.direction = dir;
   
        Position newPosition = calculateNewPosition(dir);
        
        if (!isValidMove(newPosition)) {
            return false;
        }
        
        this.position = newPosition;
        
        checkFruitCollection();
        
        return true;
    }
    
    /**
     * Calcula la nueva posición basándose en la dirección
     * @param dir Dirección del movimiento
     * @return Nueva posición calculada
     */
    private Position calculateNewPosition(int dir) {
        int newX = position.getX();
        int newY = position.getY();
        
        switch (dir) {
            case Direction.UP -> newY -= speed;
            case Direction.DOWN -> newY += speed;
            case Direction.LEFT -> newX -= speed;
            case Direction.RIGHT -> newX += speed;
        }
        
        return new Position(newX, newY);
    }
    
    /**
     * Verifica si un movimiento a la posición especificada es válido
     * @param pos Posición a verificar
     * @return true si el movimiento es válido, false en caso contrario
     */
    private boolean isValidMove(Position pos) {
        if (!board.isValidPosition(pos)) {
            return false;
        }
   
        GameObject obj = board.getObjectAt(pos);
        
        if (obj == null) {
            return true;
        }
        
        if (obj instanceof IceBlock) {
            return false;
        }
        
        if (obj instanceof Enemy) {
            die();
            return false;
        }
        
        if (obj instanceof Fruit) {
            return true;
        }
        
        return true;
    }
    
    /**
     * Verifica y recolecta frutas en la posición actual
     */
    private void checkFruitCollection() {
        GameObject obj = board.getObjectAt(position);
        
        if (obj instanceof Fruit fruit) {
            if (!fruit.isCollected()) {
                collectFruit(fruit);
            }
        }
    }
    
    /**
     * Crea una FILA COMPLETA de bloques de hielo en la dirección actual.
     * Los bloques se crean desde la posición del helado hasta encontrar un obstáculo
     * o el borde del tablero.
     * Si en alguna posición ya existe un bloque, este no se reemplaza ni se crea uno nuevo.
     * 
     * @return Número de bloques creados
     */
    public int createIceBlockLine() {
        if (!isAlive) {
            return 0;
        }
        
        int blocksCreated = 0;
        Position currentPos = new Position(position.getX(), position.getY());
    
        while (true) {
            currentPos = calculatePositionInDirection(currentPos, direction);
        
            if (!board.isValidPosition(currentPos)) {
                break;
            }
            
            GameObject obj = board.getObjectAt(currentPos);
            
            if (obj == null) {
                IceBlock block = new IceBlock(currentPos, this);
                board.addIceBlock(block);
                blocksCreated++;
            } else if (obj instanceof Enemy || obj instanceof Fruit) {
                break;
            } else {
                break;
            }
        }
        
        return blocksCreated;
    }
    
    /**
     * Crea un único bloque de hielo en la dirección actual (versión simple).
     * @return true si se creó el bloque, false en caso contrario
     */
    public boolean createSingleIceBlock() {
        if (!isAlive) {
            return false;
        }
        
        Position blockPos = calculatePositionInDirection(position, direction);
       
        if (!board.isValidPosition(blockPos)) {
            return false;
        }
 
        if (!board.isEmpty(blockPos)) {
            return false;
        }
        
        IceBlock block = new IceBlock(blockPos, this);
        board.addIceBlock(block);
        
        return true;
    }
    
    /**
     * Rompe bloques de hielo en la dirección especificada (efecto dominó).
     * Destruye todos los bloques consecutivos en esa dirección.
     * 
     * @param dir Dirección en la que romper bloques
     * @return Número de bloques rotos
     */
    public int breakIceBlocks(int dir) {
        if (!isAlive) {
            return 0;
        }
        
        int blocksDestroyed = 0;
        Position currentPos = new Position(position.getX(), position.getY());
        
        while (true) {
            currentPos = calculatePositionInDirection(currentPos, dir);
            
            if (!board.isValidPosition(currentPos)) {
                break;
            }
            
            GameObject obj = board.getObjectAt(currentPos);
            if (obj instanceof IceBlock iceBlock) {
                board.removeIceBlock(iceBlock);
                blocksDestroyed++;
            } else {
                break;
            }
        }
        
        return blocksDestroyed;
    }
    
    /**
     * Calcula la posición en una dirección específica desde una posición dada
     * @param pos Posición de origen
     * @param dir Dirección
     * @return Nueva posición
     */
    private Position calculatePositionInDirection(Position pos, int dir) {
        int newX = pos.getX();
        int newY = pos.getY();
   
        switch (dir) {
            case Direction.UP -> newY -= 1;
            case Direction.DOWN -> newY += 1;
            case Direction.LEFT -> newX -= 1;
            case Direction.RIGHT -> newX += 1;
        }
        
        return new Position(newX, newY);
    }
    
    /**
     * Recolecta una fruta
     * @param fruit Fruta a recolectar
     */
    public void collectFruit(Fruit fruit) {
        if (fruit != null && !fruit.isCollected()) {
            fruit.collect();
            this.collectedFruits++;
            board.removeFruit(fruit.getPosition());
        }
    }
    
    /**
     * Marca al helado como muerto
     */
    public void die() {
        this.isAlive = false;
        this.deactivate();
    }
    
    @Override
    public void update() {
        // Actualiza el estado del helado
        // Por ahora no hay comportamiento automático
        // Esta función se puede usar para animaciones o efectos especiales
    }
}