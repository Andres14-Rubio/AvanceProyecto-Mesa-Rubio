import java.util.ArrayList;

/**
 * Representa la torre donde se apilan los elementos.
 */
public class Tower {

    private int width;
    private int height;
    private ArrayList<StackableItem> items;
    private int nextId;
    private boolean visible;

    /**
     * Constructor de la torre.
     */
    public Tower(int width, int height) {

    }

    /**
     * Genera un identificador único para las tazas.
     */
    public int generateId() {

        return 0;
    }

    /**
     * Agrega un elemento si cabe en la torre.
     */
    public boolean addItem(StackableItem item) {

        return false;
    }

    /**
     * Elimina un elemento de la torre.
     */
    public void removeItem(StackableItem item) {

    }

    /**
     * Calcula la altura total ocupada.
     */
    public int getTotalHeight() {

        return 0;
    }

    /**
     * Reorganiza las posiciones verticales.
     */
    private void relocateItems() {

    }

    /**
     * Busca una taza por su id.
     */
    public Cup findCupById(int id) {

        return null;
    }

    /**
     * Define la visibilidad de todos los elementos.
     */
    public void setVisible(boolean visible) {

    }

    /**
     * Elimina todos los elementos de la torre.
     */
    public void clear() {

    }
}
