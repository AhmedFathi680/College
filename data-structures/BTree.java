package Trees;

import java.util.EmptyStackException;

public class BTree {

    //Instance Variables
    private BTNode root;

    //Default Conestructor
    public BTree() {
        root = null;
    }

    //Action Methods
    public boolean isEmpty() {
        return (root == null);
    }

    public boolean isFound(int key) {
        return search(key).equals("Found!");
    }

    public void printInOrderTrversal(BTNode foucsNode) {
        if (foucsNode != null) {
            printInOrderTrversal(foucsNode.getLeft());
            System.out.print(foucsNode.getData() + " ");
            printInOrderTrversal(foucsNode.getRight());
        }
    }

    public void printPreOrderTrversal(BTNode foucsNode) {
        if (foucsNode != null) {
            System.out.print(foucsNode.getData() + " ");
            printPreOrderTrversal(foucsNode.getLeft());
            printPreOrderTrversal(foucsNode.getRight());
        }
    }

    public void printPostOrderTrversal(BTNode foucsNode) {
        if (foucsNode != null) {
            printPostOrderTrversal(foucsNode.getLeft());
            printPostOrderTrversal(foucsNode.getRight());
            System.out.print(foucsNode.getData() + " ");
        }
    }

    public BTNode position(int key) throws EmptyStackException {
        BTNode current = root;
        if (current == null) {
            throw new EmptyStackException();
        } else {
            while (current != null) {
                if (current.getData() > key) {
                    current = current.getLeft();
                } else if (current.getData() < key) {
                    current = current.getRight();
                } else {
                    return current;
                }
            }
            return null;
        }
    }

    public String search(int key) {
        BTNode current = root;
        if (current == null) {
            return "The Stack is empty.";
        }
        while (current != null) {
            if (current.getData() > key) {
                current = current.getLeft();
            } else if (current.getData() < key) {
                current = current.getRight();
            } else {
                return "Found!";
            }
        }
        return "Not Found!";
    }

    public void add(int data) {
        BTNode parent = null;
        BTNode current = root;
        boolean isALeftChild = true;
        if (current == null) {
            root = new BTNode(data);
        } else {
            while (current != null) {
                parent = current;
                if (current.getData() > data) {
                    isALeftChild = true;
                    current = current.getLeft();
                } else if (current.getData() < data) {
                    isALeftChild = false;
                    current = current.getRight();
                }
            }
            BTNode newBTNode = new BTNode(parent, data);
            if (isALeftChild) {
                parent.setLeft(newBTNode);
            } else {
                parent.setRight(newBTNode);
            }
        }
    }

    public boolean remove(int data) {
        BTNode current = root;
        BTNode parent = root;
        boolean isALeftChild = true;
        while (current.getData() != data) {
            parent = current;
            if (data < current.getData()) {
                isALeftChild = true;
                current = current.getLeft();
            } else {
                isALeftChild = false;
                current = current.getRight();
            }
            if (current == null) {
                return false;
            }
        }

        if (current.isLeaf()) {
            if (current == root) {
                root = null;
            } else if (isALeftChild) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } else if (current.hasLeft() && !current.hasRight()) {
            if (current == root) {
                root = root.getLeft();
                root.setParent(null);
            } else if (isALeftChild) {
                parent.setLeft(current.getLeft());
                parent.getLeft().setParent(parent);
            } else {
                parent.setRight(current.getLeft());
                parent.getRight().setParent(parent);
            }
        } else if (current.hasRight() && !current.hasLeft()) {
            if (current == root) {
                root = root.getRight();
                root.setParent(null);
            } else if (isALeftChild) {
                parent.setLeft(current.getRight());
                parent.getLeft().setParent(parent);
            } else {
                parent.setRight(current.getRight());
                parent.getRight().setParent(parent);
            }
        } else {
            BTNode replacement = getReplacementNode(current);
            if (current == root) {
                root = replacement;
            } else if (isALeftChild) {
                current.setLeft(replacement);
            } else {
                current.setRight(replacement);
            }
            replacement.setLeft(current.getLeft());
        }
        return true;
    }

    private BTNode getReplacementNode(BTNode replacedNode) {
        BTNode current = replacedNode;
        BTNode parent = replacedNode;

        BTNode child = replacedNode.getRight();

        while (child.hasLeft()) {
            parent = current;
            current = child;
            child = child.getLeft();
        }
        parent.setLeft(current.getRight());
        parent.getLeft().setParent(parent);
        current.setRight(replacedNode.getRight());
        return current;
    }
}
