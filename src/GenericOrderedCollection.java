// =============================================================================

/**
 * Interface for methods defined in MyGenericDS
 **/
// =============================================================================
public interface GenericOrderedCollection<E> {
    public void append(E toAppend);

    public E peek();

    public E pop();

    public E get(int index);

    public void remove(int index);

    public String toString();

    public int length();
}