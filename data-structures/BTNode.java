public class BTNode {

    //Instance Variables
    private BTNode parent;
    private BTNode right;
    private int data;
    private BTNode left;

    //Constructors
    public BTNode(BTNode parent, BTNode right, int data, BTNode left) {
        this.parent = parent;
        this.right = right;
        this.data = data;
        this.left = left;
    }

    public BTNode(int data) {
        this(null, null, data, null);
    }

    public BTNode(BTNode parent, int data) {
        this(parent, null, data, null);
    }

    //Accessors Methods
    public BTNode getParent() {
        return parent;
    }

    public BTNode getLeft() {
        return this.left;
    }

    public int getData() {
        return this.data;
    }

    public BTNode getRight() {
        return this.right;
    }

    //Mutators Methods
    public void setParent(BTNode parent) {
        this.parent = parent;
    }

    public void setLeft(BTNode left) {
        this.left = left;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setRight(BTNode right) {
        this.right = right;
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
}