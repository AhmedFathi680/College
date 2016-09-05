import java.util.EmptyStackException;

public class Queue<E> {

    //Instance Variables
    private QueueNode<E> head;
    private QueueNode<E> tail;
    private long size;

    //Deafult Constructor
    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }

    //Mutator Methods
    public long getSize() {
        return size;
    }

    //Action Methods
    public boolean isEmpty() {
        return size == 0;
    }

    public void makeEmpty() {
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(E data) {
        QueueNode<E> newQueueNode = new QueueNode(data);
        if (size == 0) {
            head = newQueueNode;
            tail = newQueueNode;
        } else {
            newQueueNode.setNext(head);
            head.setPrevious(newQueueNode);
            head = newQueueNode;
        }
        size++;
    }

    public E dequeue() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            E temp = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp;
        }
    }

    public E front() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            return tail.getData();
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "Empty Queue.";
        } else {
            String s = "[" + head.getData() + ", ";
            QueueNode current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
                s += current.getData() + ", ";
            }
            return s + "\b\b]";
        }
    }
}