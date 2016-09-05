public class GTNode {

    //instance variables
    private GTNode parent;
    private Object data;
    private GTNode[] children;

    //Constructors
    public GTNode(GTNode parent, Object data, GTNode[] children) {
        this.parent = parent;
        this.data = data;
        this.children = children;
    }

    public GTNode(GTNode parent, Object data) {
        this(parent, data, null);
    }

    public GTNode getParent() {
        return parent;
    }

    public Object getData() {
        return data;
    }

    public GTNode[] getChildren() {
        return children;
    }

    public void setParent(GTNode parent) {
        this.parent = parent;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setChildren(GTNode[] children) {
        this.children = children;
    }

    public void addChild(Object data) {
        GTNode newGTNode = new GTNode(this, data);
        if (this.children == null) {
            children = new GTNode[1];
            children[0] = newGTNode;
        } else {
            if (children[children.length - 1] != null) {
                GTNode[] newChildren = new GTNode[2 * children.length];
                System.arraycopy(children, 0, newChildren, 0, children.length);
                children = newChildren;
            } else if (children[children.length / 4] == null) {
                GTNode[] newChildren = new GTNode[children.length / 2];
                System.arraycopy(children, 0, newChildren, 0, children.length);
                children = newChildren;
            }
            int i = 0;
            while (children[i] != null) {
                i++;
            }
            children[i] = newGTNode;
        }
    }

    //This Methods Prints [Child data *** with Parent ***, Child data *** with Parent ***]
    //and the length of the array which having them, and the number of the children already exist
    public String printChildren() {
        String s = "[";
        int i = 0;
        //System.out.println(children[children.length-1].getData());
        while (i < children.length && children[i] != null) {
            s += children[i].getData() + " With Parent "
                    + children[i].getParent().getData() + ", ";
            i++;
        }
        return s + "\b\b]\nThe Array Length = " + children.length
                + "\nThe number of Children = " + i;
    }
}
