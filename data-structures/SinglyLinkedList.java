import java.util.EmptyStackException;

public class SinglyLinkedList<E> {

    //Instance Variables
    private SinglyLinkedListNode<E> head;
    private long size;

    //Default Constructor
    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    //Accsessor Methods
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

    public void checkIndex(long index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void insert(E data, long index) {
        checkIndex(index);
        SinglyLinkedListNode<E> newSinglyLinkedListNode =
                new SinglyLinkedListNode(data);
        if (size == 0) {
            head = new SinglyLinkedListNode(data);
        } else if (index == size) {
            SinglyLinkedListNode<E> current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
            }
            current.setNext(newSinglyLinkedListNode);
        } else if (index == 0) {
            newSinglyLinkedListNode.setNext(head);
            head = newSinglyLinkedListNode;
        } else {
            SinglyLinkedListNode<E> current = head;
            for (long i = 0; i < index; i++) {
                current = current.getNext();
            }
            newSinglyLinkedListNode.setNext(current.getNext());
            current.setNext(newSinglyLinkedListNode);
        }
        size++;
    }

    public String search(E key) {
        SinglyLinkedListNode<E> current = head;
        for (long i = 0; i < size; i++) {
            if (current.getData().equals(key)) {
                return "Found!";
            }
            current = current.getNext();
        }
        return "Not Found!";
    }

    public long find(E key) {
        SinglyLinkedListNode<E> current = new SinglyLinkedListNode(key);
        for (int i = 0; i < size; i++) {
            if (current.getData().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public void remove(long index) throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            checkIndex(index);
            if (index == 0) {
                head = head.getNext();
            } else {
                SinglyLinkedListNode<E> current = head;
                SinglyLinkedListNode<E> preCurrent = null;
                for (long i = 0; i < size - 1; i++) {
                    preCurrent = current;
                    current = current.getNext();
                }
                preCurrent.setNext(current.getNext());
            }
        }
        size--;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "Empty Linked List.";
        } else {
            String s = "[" + head.getData() + ", ";
            SinglyLinkedListNode<E> current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
                s += current.getData() + ", ";
            }
            return s + "\b\b]";
        }
    }
}