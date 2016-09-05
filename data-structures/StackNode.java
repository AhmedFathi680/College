public class StackNode<E> {

    //Instance Variables
    private StackNode<E> next;
    private E data;

    //Default Constructor
    public StackNode() {
        this(null, null);
    }

    //Non-default Constructors
    public StackNode(E data) {
        this(null, data);
    }

    public StackNode(StackNode<E> next, E data) {
        this.next = next;
        this.data = data;
    }

    //Accessor Methods
    public E getData() {
        return data;
    }

    public StackNode<E> getNext() {
        return next;
    }

    //Mutator Methods
    public void setData(E data) {
        this.data = data;
    }

    public void setNext(StackNode<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node : " + super.toString()
                + "\nNext is : " + this.next
                + "\nData is : " + this.data;
    }
}