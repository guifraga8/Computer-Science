package avl_tree;

public class AVLTree {
    protected Node root;

    public AVLTree() {
        this.root = null;
    }

    public Node insert(int key) {
        return this.insert(this.root, key);
    }
    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.getKey()) {
            node.setLeft(insert(node.getLeft(), key));
        } else if (key > node.getKey()) {
            node.setRight(insert(node.getRight(), key));
        } else {
            return node;
        }

        node.setHeight(1 + Math.max(this.height(node.getLeft()), this.height(node.getRight())));

        int balance = this.getBalance(node);

        if (balance > 1 && key < node.getLeft().getKey()) {
            return this.rotateRight(node);
        }
        if (balance < -1 && key > node.getRight().getKey()) {
            return this.rotateLeft(node);
        }
        if (balance > 1 && key > node.getLeft().getKey()) {
            node.setLeft(this.rotateLeft(node.getLeft()));
            return this.rotateRight(node);
        }
        if (balance < -1 && key < node.getRight().getKey()) {
            node.setRight(this.rotateRight(node.getRight()));
            return this.rotateLeft(node);
        }
        return node;
    }
    public Node delete(int key) {
        return this.delete(this.root, key);
    }
    private Node delete(Node root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.getKey()) {
            root.setLeft(this.delete(root.getLeft(), key));
        } else if (key > root.getKey()) {
            root.setRight(this.delete(root.getRight(), key));
        } else {
            if (root.getLeft() == null || root.getRight() == null) {
                Node temp = null;
                if (temp == root.getLeft()) {
                    temp = root.getRight();
                } else {
                    temp = root.getLeft();
                }
                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node temp = this.getMinimum(root.getRight());
                root.setKey(temp.getKey());
                root.setRight(this.delete(root.getRight(), temp.getKey()));
            }
        }
        if (root == null) {
            return null;
        }
        root.setHeight(Math.max(this.height(root.getLeft()), this.height(root.getRight())) + 1);
        int balance = this.getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rotateRight(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return rotateLeft(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }
        return root;
    }
    public boolean search(int key) {
        return this.search(this.root, key);
    }
    private boolean search(Node node, int key) {
        if (node != null) {
            if (node.getKey() == key) {
                return true;
            } else if (key < node.getKey()) {
                return this.search(node.left, key);
            } else {
                return this.search(node.right, key);
            }
        }
        return false;
    }
    public Node rotateLeft(Node x) {
        Node y = x.getRight();
        Node T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(Math.max(this.height(x.left), this.height(x.right)) + 1);
        y.setHeight(Math.max(this.height(y.left), this.height(y.right)) + 1);
        return y;
    }
    public Node rotateRight(Node y) {
        Node x = y.getLeft();
        Node T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        return x;
    }
    private int getBalance(Node node) {
        if (node != null) {
            return this.height(node.getLeft()) - this.height(node.getRight());
        }
        return 0;
    }
    private int height(Node node) {
        if (node != null) {
            return node.getHeight();
        }
        return 0;
    }

    public Node getMinimum() {
        return this.getMinimum(this.root);
    }
    private Node getMinimum(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /*
     * Tree Traversals
     *
     * Pre Order => root, left and right
     * In Order => left, root and right
     * Post Order => left, right and root
    */
    public void printPreOrder() {
        this.printPreOrder(this.root);
        System.out.println("\n");
    }
    private void printPreOrder(Node node) {
        if (node != null) {
            System.out.print(node.getKey() + " ");
            this.printPreOrder(node.getLeft());
            this.printPreOrder(node.getRight());
        }
    }
    public void printInOrder() {
        this.printInOrder(this.root);
        System.out.println("\n");
    }
    private void printInOrder(Node node) {
        if (node != null) {
            this.printInOrder(node.getLeft());
            System.out.print(node.getKey() + " ");
            this.printInOrder(node.getRight());
        }
    }
    public void printPostOrder() {
        this.printPostOrder(this.root);
    }
    private void printPostOrder(Node node) {
        if (node != null) {
            this.printPostOrder(node.getLeft());
            this.printPostOrder(node.getRight());
            System.out.print(node.getKey() + " ");
        }
    }
}
