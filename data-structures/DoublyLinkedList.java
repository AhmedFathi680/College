import java.util.EmptyStackException;

public class DoublyLinkedList<E> {

    //Instance Variables
    private DoublyLinkedListNode<E> head;
    private DoublyLinkedListNode<E> tail;
    private long size;

    //Default Constructor
    public DoublyLinkedList() {
        head = null;
        tail = null;
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
        tail = null;
        size = 0;
    }

    public void checkIndex(long index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void insert(E data, long index) {
        checkIndex(index);
        DoublyLinkedListNode<E> newDoublyLinkedListNode = new DoublyLinkedListNode(data);
        if (size == 0) {
            head = newDoublyLinkedListNode;
            tail = newDoublyLinkedListNode;
        } else if (index == 0) {
            newDoublyLinkedListNode.setNext(head);
            head.setPrevious(newDoublyLinkedListNode);
            head = newDoublyLinkedListNode;
        } else if (index == size) {
            newDoublyLinkedListNode.setPrevious(tail);
            tail.setNext(newDoublyLinkedListNode);
            tail = newDoublyLinkedListNode;
        } else {
            DoublyLinkedListNode<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            newDoublyLinkedListNode.setNext(current.getNext());
            newDoublyLinkedListNode.setPrevious(current);
            current.setNext(newDoublyLinkedListNode);
            newDoublyLinkedListNode.getNext().setPrevious(newDoublyLinkedListNode);
        }
        size++;
    }

    public String search(E key) {
        DoublyLinkedListNode<E> current = new DoublyLinkedListNode(key);
        for (int i = 0; i < size; i++) {
            if (current.getData().equals(key)) {
                return "Found!";
            }
        }
        return "Not Found!";
    }

    public long find(E key) {
        DoublyLinkedListNode<E> current = new DoublyLinkedListNode(key);
        for (int i = 0; i < size; i++) {
            if (current.getData().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    public void remove(long index) throws EmptyStackException {
        checkIndex(index);
        if (size == 0) {
            throw new EmptyStackException();
        } else if (index == size) {
            tail = tail.getPrevious();
            tail.setNext(null);
        } else if (index == 0) {
            head = head.getNext();
            head.setPrevious(null);
        } else {
            DoublyLinkedListNode current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
            }
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }
        size--;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "Empty Linked List.";
        } else {
            String s = "[" + head.getData() + ", ";
            DoublyLinkedListNode<E> current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
                s += current.getData() + ", ";
            }
            return s + "\b\b]";
        }
    }
}
