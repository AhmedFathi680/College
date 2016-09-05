package clgproject;

class QueueNode<E> {

    //Instance Variables
    private QueueNode next;
    private E data;
    private QueueNode previous;

    //Non-Default Constructors
    public QueueNode(E data) {
        this(null, data, null);
    }

    public QueueNode(QueueNode<E> next, E data, QueueNode<E> previous) {
        this.next = next;
        this.previous = previous;
        this.data = data;
    }

    //Accessor Methods
    public QueueNode<E> getNext() {
        return next;
    }

    public E getData() {
        return data;
    }

    public QueueNode<E> getPrevious() {
        return previous;
    }

    //Mutator Methods
    public void setNext(QueueNode<E> next) {
        this.next = next;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setPrevious(QueueNode<E> previous) {
        this.previous = previous;
    }
}
