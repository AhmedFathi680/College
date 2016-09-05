package clgproject;

public class DequeNode<E> {

    //Instance variables
    private DequeNode<E> next;
    private E data;
    private DequeNode<E> previous;

    //Default Constructor
    public DequeNode() {
        this(null, null, null);
    }

    //Non-default Constructors
    public DequeNode(E data) {
        this(null, data, null);
    }

    public DequeNode(DequeNode<E> next, E data, DequeNode<E> previous) {
        this.next = next;
        this.data = data;
        this.previous = previous;
    }

    //Accessor Methods
    public DequeNode<E> getNext() {
        return next;
    }

    public E getData() {
        return data;
    }

    public DequeNode<E> getPrevious() {
        return previous;
    }

    //Mutator Methods
    public void setNext(DequeNode<E> next) {
        this.next = next;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setPrevious(DequeNode<E> previous) {
        this.previous = previous;
    }

    //toString
    @Override
    public String toString() {
        return "Node : " + super.toString()
                + "\nNext is : " + this.next
                + "\nData is : " + this.data
                + "\nPrevious is : " + this.previous;
    }
}
