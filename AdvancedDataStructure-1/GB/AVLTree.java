package trabalho_grau_b;

public class AVLTree<T extends Comparable<T>> {
    protected Node<T> root;

    public AVLTree() {
        this.root = null;
    }

    // Insert
    public Node<T> insert(T key, Pessoa pessoa) {
        return this.insert(this.root, key, pessoa);
    }
    private Node<T> insert(Node<T> node, T key, Pessoa pessoa) {
        if (node == null) {
            return new Node<T>(key, pessoa);
        }
        if (node.getKey().compareTo(key) > 0) {
            node.setLeft(insert(node.getLeft(), key, pessoa));
        } else if (node.getKey().compareTo(key) < 0) {
            node.setRight(insert(node.getRight(), key, pessoa));
        } else {
            return node;
        }

        node.setHeight(1 + Math.max(this.height(node.getLeft()), this.height(node.getRight())));

        int balance = this.getBalance(node);

        if (balance > 1 && node.getLeft().getKey().compareTo(key) > 0) {
            return this.rotateRight(node);
        }
        if (balance < -1 && node.getRight().getKey().compareTo(key) < 0) {
            return this.rotateLeft(node);
        }
        if (balance > 1 && node.getLeft().getKey().compareTo(key) < 0) {
            node.setLeft(this.rotateLeft(node.getLeft()));
            return this.rotateRight(node);
        }
        if (balance < -1 && node.getRight().getKey().compareTo(key) > 0) {
            node.setRight(this.rotateRight(node.getRight()));
            return this.rotateLeft(node);
        }
        return node;
    }

    // Delete
    public Node<T> delete(T key) {
        return this.delete(this.root, key);
    }
    private Node<T> delete(Node<T> root, T key) {
        if (root == null) {
            return null;
        }
        if (root.getKey().compareTo(key) > 0) {
            root.setLeft(this.delete(root.getLeft(), key));
        } else if (root.getKey().compareTo(key) < 0) {
            root.setRight(this.delete(root.getRight(), key));
        } else {
            if (root.getLeft() == null || root.getRight() == null) {
                Node<T> temp = null;
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
                Node<T> temp = this.getMinimum(root.getRight());
                root.setKey(temp.getKey());
                root.setRight(this.delete(root.getRight(), temp.getKey()));
            }
        }
        if (root == null) {
            return null;
        }
        root.setHeight(Math.max(this.height(root.getLeft()), this.height(root.getRight())) + 1);
        int balance = this.getBalance(root);

        // Left Left case
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

    // Search for specific CPF
    public Pessoa search(T key) {
        return this.search(this.root, key);
    }
    private Pessoa search(Node<T> node, T key) {
        if (node != null) {
            if (node.getKey().equals(key)) {
                return node.getPessoa();
            } else if (node.getKey().compareTo(key) > 0) {
                return this.search(node.left, key);
            } else {
                return this.search(node.right, key);
            }
        }
        return null;
    }

    // Search for segment of name
    public void searchByName(T segmento) {
        this.searchByName(this.root, segmento);
    }
    private void searchByName(Node<T> node, T segmento) {
        if (node == null) {
            return;
        }

        int max = Math.min(String.valueOf(segmento).length(), String.valueOf(node.getKey()).length());
        String iniciais = String.valueOf(node.getKey()).substring(0, max);

        if (iniciais.equals(segmento)) {
            System.out.println(node.getPessoa());
            this.searchByName(node.left, segmento);
            this.searchByName(node.right, segmento);
        } else if (iniciais.compareTo((String) segmento) < 0) {
            this.searchByName(node.right, segmento);
        } else {
            this.searchByName(node.left, segmento);
        }
    }

    // Search for date
    public void searchByDate(T inicio, T fim) {
        this.searchByDate(this.root, inicio, fim);
    }
    private void searchByDate(Node<T> root, T inicio, T fim) {
        if (root == null) {
            return;
        }

        if (inicio.compareTo(root.getKey()) * (root.getKey().compareTo(fim)) >= 0) {
            System.out.println(root.getPessoa());
            this.searchByDate(root.left, inicio, fim);
            this.searchByDate(root.right, inicio, fim);
            //nó atual < valor que a gente quer -> direita
        } else if (root.getKey().compareTo(inicio) < 0) {
            this.searchByDate(root.right, inicio, fim);
        } else {
            this.searchByDate(root.left, inicio, fim);
        }
    }

    // Rotate Left
    public Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.getRight();
        Node<T> T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(Math.max(this.height(x.left), this.height(x.right)) + 1);
        y.setHeight(Math.max(this.height(y.left), this.height(y.right)) + 1);
        return y;
    }

    // Rotate Right
    public Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.getLeft();
        Node<T> T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        return x;
    }

    // Check balance
    private int getBalance(Node<T> node) {
        if (node != null) {
            return this.height(node.getLeft()) - this.height(node.getRight());
        }
        return 0;
    }

    // Check height
    private int height(Node<T> node) {
        if (node != null) {
            return node.getHeight();
        }
        return 0;
    }

    // Minimum key
    public Node<T> getMinimum() {
        return this.getMinimum(this.root);
    }
    private Node<T> getMinimum(Node<T> node) {
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
    private void printPreOrder(Node<T> node) {
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
    private void printInOrder(Node<T> node) {
        if (node != null) {
            this.printInOrder(node.getLeft());
            System.out.print(node.getKey() + " ");
            this.printInOrder(node.getRight());
        }
    }
    public void printPostOrder() {
        this.printPostOrder(this.root);
    }
    private void printPostOrder(Node<T> node) {
        if (node != null) {
            this.printPostOrder(node.getLeft());
            this.printPostOrder(node.getRight());
            System.out.print(node.getKey() + " ");
        }
    }
}
