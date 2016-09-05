package clgproject;

import java.util.EmptyStackException;

public class Deque<E> {

    //Instance Variables
    private DequeNode<E> head;
    private DequeNode<E> tail;
    private long size;

    //Default Constructor
    public Deque() {
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

    public void addFrist(E data) {
        DequeNode<E> newDequeNode = new DequeNode(data);
        if (size == 0) {
            head = newDequeNode;
            tail = newDequeNode;
        } else {
            newDequeNode.setNext(head);
            head.setPrevious(newDequeNode);
            head = newDequeNode;
        }
        size++;
    }

    public void addLast(E data) {
        DequeNode<E> newDequeNode = new DequeNode(data);
        if (size == 0) {
            tail = newDequeNode;
            head = newDequeNode;
        } else {
            newDequeNode.setPrevious(tail);
            tail.setNext(newDequeNode);
            tail = newDequeNode;
        }
        size++;
    }

    public void removeFrist() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            head = head.getNext();
            head.setPrevious(null);
            size--;
        }
    }

    public void removeLast() throws EmptyStackException {
        if (size == 0) {
            throw new EmptyStackException();
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
        }
    }

    public E getFrist() {
        return head.getData();
    }

    public E getLast() {
        return tail.getData();
    }

    //toString
    @Override
    public String toString() {
        if (size == 0) {
            return "Empty Deque.";
        } else {
            String s = "[" + head.getData() + ", ";
            DequeNode<E> current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.getNext();
                s += current.getData() + ", ";
            }
            return s + "\b\b]";
        }
    }
}
