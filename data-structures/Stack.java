import java.util.EmptyStackException;

public class Stack<E> {

    //Instance Variables
    private StackNode<E> head;
    private long size;

    //Default Constructor
    public Stack() {
        head = null;
        size = 0;
    }

    //Accessor Methods
    public long getSize() {
        return size;
    }

    //Action Methods
    public boolean isEmpty() {
        return size == 0;
    }

    public void makeEmpty() {
        head = null;
        size = 0;
    }

    public void push(E data) {
        StackNode<E> newStackNode = new StackNode(data);
        if (size == 0) {
            head = newStackNode;
        } else {
            newStackNode.setNext(head);
            head = newStackNode;
        }
        size++;
    }

    public E pop() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            E temp = head.getData();
            head = head.getNext();
            size--;
            return temp;
        }
    }

    public E top() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            return head.getData();
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "Empty Stack.";
        } else {
            String s = "[" + head.getData() + ", ";
            StackNode<E> current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
                s += current.getData() + ", ";
            }
            return s + "\b\b]";
        }
    }
}
