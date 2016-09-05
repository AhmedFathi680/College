package Trees;

public class AVLTNode {

    //Instance Variables
    private AVLTNode parent;
    private AVLTNode right;
    private int data;
    private AVLTNode left;

    //Constructors
    public AVLTNode(AVLTNode parent, AVLTNode right, int data, AVLTNode left) {
        this.parent = parent;
        this.right = right;
        this.data = data;
        this.left = left;
    }

    public AVLTNode(AVLTNode parent, int data) {
        this(parent, null, data, null);
    }

    public AVLTNode(int data) {
        this(null, null, data, null);
    }

    //Accesssor Methods
    public AVLTNode getParent() {
        return parent;
    }

    public AVLTNode getRight() {
        return right;
    }

    public int getData() {
        return data;
    }

    public AVLTNode getLeft() {
        return left;
    }

    //Mutator Methods
    public void setParent(AVLTNode parent) {
        this.parent = parent;
    }

    public void setRight(AVLTNode right) {
        this.right = right;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setLeft(AVLTNode left) {
        this.left = left;
    }

    //Action Methods
    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return (this.right == null) && (this.left == null);
    }

    public boolean hasRight() {
        return this.right != null;
    }

    public boolean hasLeft() {
        return this.left != null;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "data is : " + this.data;
    }
}
