package trabalho_grau_b;

public class Node<T> {
    protected T key;
    protected int height;
    protected Node<T> left;
    protected Node<T> right;
    protected Pessoa pessoa;

    public Node(T key, Pessoa pessoa) {
        this.key = key;
        this.height = 1;
        this.left = null;
        this.right = null;
        this.pessoa = pessoa;
    }

    public T getKey() {
        return this.key;
    }
    public void setKey(T key) {
        this.key = key;
    }
    public int getHeight() {
        return this.height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Node<T> getLeft() {
        return this.left;
    }
    public void setLeft(Node<T> left) {
        this.left = left;
    }
    public Node<T> getRight() {
        return this.right;
    }
    public void setRight(Node<T> right) {
        this.right = right;
    }
    public Pessoa getPessoa() {
        return this.pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
