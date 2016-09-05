package clgproject;

public class SinglyLinkedListNode<E> {

    //Instance Variables
    private SinglyLinkedListNode<E> next;
    private E data;

    //Default Constructor
    public SinglyLinkedListNode() {
    }

    //Non-default Constracutors
    public SinglyLinkedListNode(E data) {
        this.data = data;
    }

    public SinglyLinkedListNode(SinglyLinkedListNode<E> next, E data) {
        this.next = next;
        this.data = data;
    }

    //Accessor Methods
    public E getData() {
        return data;
    }

    public SinglyLinkedListNode<E> getNext() {
        return next;
    }

    //Mutator Methods
    public void setData(E data) {
        this.data = data;
    }

    public void setNext(SinglyLinkedListNode<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node : " + super.toString()
                + "\nNext is : " + this.next
                + "\nData is : " + this.data;
    }
}
