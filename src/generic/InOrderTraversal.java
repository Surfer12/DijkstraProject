package legacyalgo.generic;
import java.util.function.Consumer;

public class InOrderTraversal<T> {

    public void inOrderTraversal(GenericNode<T> root, Consumer<T> action) {
        if (root == null) {
            return;
        }
        inOrderTraversal(root.getLeft(), action);
        action.accept(root.getData());
        inOrderTraversal(root.getRight(), action);
    }

    public void preOrderTraversal(GenericNode<T> root, Consumer<T> action) {
        if (root == null) {
            return;
        }
        action.accept(root.getData());
        preOrderTraversal(root.getLeft(), action);
        preOrderTraversal(root.getRight(), action);
    }

    public void postOrderTraversal(GenericNode<T> root, Consumer<T> action) {
        if (root == null) {
            return;
        }
        postOrderTraversal(root.getLeft(), action);
        postOrderTraversal(root.getRight(), action);
        action.accept(root.getData());
    }
}
