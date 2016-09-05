package Trees;
//When Adding children of children I got a problem is the search method- 
//which have the 2 arguments, I don't know why ?!
//See Class TestGTree
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class GTree {

    //Instance Variables
    protected GTNode root;

    //Constructors
    public GTree(int rootData) {
        root = new GTNode(null, rootData, null);
    }

    //Action Methods
    public void addChild(Object parent, Object data) throws NotFound {
        GTNode target = search(parent, this.root);
        if (target == null) {
            throw new NotFound();
        } else {
            target.addChild(data);
        }
    }

    public String search(Object key) {
        if (search(key, this.root) != null) {
            return "Found!";
        } else {
            return "Not Found!";
        }
    }

    public GTNode Position(Object key) {
        return search(key, this.root);
    }

    private GTNode search(Object data, GTNode root) {
        if (root.getData() == data) {
            return root;
        } else {
            GTNode[] currentChilren = root.getChildren();
            while (currentChilren != null || root != root.getParent().getChildren()[root.getParent().getChildren().length]) {
                int i;
                for (i = 0; i < currentChilren.length && currentChilren[i] != null; i++) {
                    if (currentChilren[i].getData().equals(data)) {
                        return currentChilren[i];
                    }
                }
                for (i = 0; i < currentChilren.length && currentChilren[i] != null; i++) {
                    return search(data, currentChilren[i]);
                }
            }
            return null;
        }
    }

    public void remove(Object data) throws NotFound {
        GTNode target = search(data, this.root);
        if (target == null) {
            throw new NotFound();
        } else {
            GTNode[] targetChildren = target.getChildren();
            GTNode[] brothers = target.getParent().getChildren();
            GTNode targetParent = target.getParent();
            int i = 0;
            for (i = 0; i < brothers.length; i++) {
                if (brothers[i] == target) {
                    break;
                }
            }
            for (; i < brothers.length - 1; i++) {
                brothers[i] = brothers[i + 1];
            }
            brothers[i + 1] = null;
            if (targetChildren != null) {
                for (i = 0; i < targetChildren.length; i++) {
                    targetParent.addChild(targetChildren[i].getData());
                }
            }
        }
    }

    public int depth(GTree T, GTNode N) {
        if (N == T.root) {
            return 0;
        } else {
            return 1 + depth(T, N.getParent());
        }
    }

    @Override
    public String toString() {
        return "Root is : " + this.root.getData()
                + "\nRoot Children are : " + root.printChildren();
    }
}
