package Trees;

import java.util.EmptyStackException;

public class AVLTree {

    //Instance Varbiables
    private byte balance;
    private AVLTNode root;

    //Constructors
    public AVLTree() {
        root = null;
        balance = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean isFound(int key) {
        return search(key).equals("Found!");
    }

    public int balanceNode(AVLTNode target) {
        return Math.abs(getLeftBalance(target) - getRightBalance(target));
    }

    public boolean isBalancedNode(AVLTNode target) {
        return balanceNode(target) <= 2;
    }

    private int getRightBalance(AVLTNode target) {
        if (!target.hasRight()) {
            return 0;
        } else {
            return 1 + getRightBalance(target.getRight());
        }
    }

    private int getLeftBalance(AVLTNode target) {
        if (!target.hasLeft()) {
            return 0;
        } else {
            return 1 + getLeftBalance(target.getLeft());
        }
    }

    public void printInOrderTrversal(AVLTNode foucsNode) {
        if (foucsNode != null) {
            printInOrderTrversal(foucsNode.getLeft());
            System.out.print(foucsNode.getData() + " ");
            printInOrderTrversal(foucsNode.getRight());
        }
    }

    public void printPreOrderTrversal(AVLTNode foucsNode) {
        if (foucsNode != null) {
            System.out.print(foucsNode.getData() + " ");
            printPreOrderTrversal(foucsNode.getLeft());
            printPreOrderTrversal(foucsNode.getRight());
        }
    }

    public void printPostOrderTrversal(AVLTNode foucsNode) {
        if (foucsNode != null) {
            printPostOrderTrversal(foucsNode.getLeft());
            printPostOrderTrversal(foucsNode.getRight());
            System.out.print(foucsNode.getData() + " ");
        }
    }

    public void add(int data) {
        AVLTNode newAVLTNode = new AVLTNode(data);
        AVLTNode current = root;
        AVLTNode parent = null;
        boolean isALeftChild = true;
        if (current == null) {
            root = newAVLTNode;
        } else {
            while (current != null) {
                parent = current;
                if (data < current.getData()) {
                    isALeftChild = true;
                    current = current.getLeft();
                } else {
                    isALeftChild = false;
                    current = current.getRight();
                }
            }
            if (isALeftChild) {
                parent.setLeft(newAVLTNode);
            } else {
                parent.setRight(newAVLTNode);
            }
        }
    }

    public AVLTNode position(int key) throws EmptyStackException {
        AVLTNode current = root;
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
        AVLTNode current = root;
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

    public boolean remove(int data) {
        AVLTNode current = root;
        AVLTNode parent = root;
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
            AVLTNode replacement = getReplacementNode(current);
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

    private AVLTNode getReplacementNode(AVLTNode replacedNode) {
        AVLTNode current = replacedNode;
        AVLTNode parent = replacedNode;

        AVLTNode child = replacedNode.getRight();

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
