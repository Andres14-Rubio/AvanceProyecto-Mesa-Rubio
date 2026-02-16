/**
 * Clase abstracta que representa un elemento apilable.
 */
public abstract class StackableItem {

    protected int height;
    protected int positionY;

    /**
     * Retorna la altura del elemento.
     */
    public int getHeight() {

        return 0;
    }

    /**
     * Mueve el elemento a una posición vertical.
     */
    public void moveTo(int y) {

    }

    /**
     * Hace visible el elemento.
     */
    public abstract void makeVisible();

    /**
     * Hace invisible el elemento.
     */
    public abstract void makeInvisible();
}
