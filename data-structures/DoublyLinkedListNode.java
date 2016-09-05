package clgproject;

public class DoublyLinkedListNode<E> {

    //Instance Variables
    private DoublyLinkedListNode<E> next;
    private E data;
    private DoublyLinkedListNode<E> previous;

    //Default Constructor
    public DoublyLinkedListNode() {
        this(null, null, null);
    }

    //Non-default constructors
    public DoublyLinkedListNode(E data) {
        this(null, data, null);
    }

    public DoublyLinkedListNode(DoublyLinkedListNode<E> next, E data, DoublyLinkedListNode<E> previous) {
        this.next = next;
        this.previous = previous;
        this.data = data;
    }

    //Accessor Methods
    public DoublyLinkedListNode<E> getNext() {
        return next;
    }

    public E getData() {
        return data;
    }

    public DoublyLinkedListNode<E> getPrevious() {
        return previous;
    }

    //Mutator Methods
    public void setNext(DoublyLinkedListNode<E> next) {
        this.next = next;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setPrevious(DoublyLinkedListNode<E> previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "Node : " + super.toString()
                + "\nNext is : " + this.next
                + "\nData is : " + this.data
                + "\nPrevious is : " + this.previous;
    }
}
