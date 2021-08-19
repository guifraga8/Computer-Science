package avl_tree;

public class Main {

    public static void main(String[] args) {

        // the elements of each operation
        int[] insert = {769, 524, 781, 782, 14, 783, 527, 272, 18, 275, 532, 22, 28, 544, 33, 802, 296, 42, 556, 301, 565, 56, 58, 318, 831, 577, 834, 325, 841, 842, 76, 337, 338, 853, 598, 345, 90, 860, 93, 611, 99, 612, 357, 869, 871, 106, 618, 878, 887, 375, 889, 634, 122, 123, 129, 644, 658, 921, 154, 155, 926, 159, 928, 418, 930, 419, 423, 167, 681, 937, 939, 172, 428, 431, 434, 181, 437, 699, 702, 959, 192, 961, 963, 196, 454, 976, 979, 468, 981, 217, 218, 234, 493, 238, 494, 241, 497, 757, 761, 767};
        int[] delete = {192, 961, 454, 842, 524, 781, 337, 658, 853, 860, 93, 926, 928, 930, 802, 167, 871, 296, 106, 42, 172, 556, 301, 238, 241, 437, 56, 761, 634, 831};
        int[] find = {139, 15, 122, 106, 657, 493, 579, 232, 241, 841};

        AVLTree tree = new AVLTree();

        System.out.println("Inserting...");
        for (int element : insert) {
            System.out.println("Inserting the element " + element + ".");
            tree.root = tree.insert(element);
        }
        System.out.println("\nTree before deleting some elements...");
        tree.printInOrder();
        System.out.println("Deleting...");
        for (int element : delete) {
            System.out.println("Deleting the element " + element + ".");
            tree.root = tree.delete(element);
        }
        System.out.println("\nSearching...");
        for (int element : find) {
            if (tree.search(element)) {
                System.out.println(element + " is in tree.");
            } else {
                System.out.println(element + " isn't in tree.");
            }
        }

        System.out.println("\nTree Traversals...");
        System.out.println("Pre Order: ");
        tree.printPreOrder();
        System.out.println("In Order: ");
        tree.printInOrder();
        System.out.println("Post Order: ");
        tree.printPostOrder();
    }
}
