package nz.ac.auckland.se281.datastructures;

/**
 * Node structure to assist in creation of a linked list. Contains given data and a pointer to the
 * next node.
 *
 * @param <T> The type of each node's data.
 */
public class Node<T> {
  private Node<T> next;
  private T data;

  /**
   * Intialises a node with given data and no next node.
   *
   * @param data data to be stored
   */
  public Node(T data) {
    this.data = data;
    this.next = null;
  }

  /**
   * Gets the data associated with this node.
   *
   * @return data
   */
  public T getData() {
    return this.data;
  }

  /**
   * Sets the data to new provided data.
   *
   * @param data data to be stored in this node
   */
  public void setData(T data) {
    this.data = data;
  }

  /**
   * Gets the node this one points to.
   *
   * @return next node
   */
  public Node<T> getNext() {
    return this.next;
  }

  /**
   * Sets the next node to provided node.
   *
   * @param next next node
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }

  @Override
  public String toString() {
    return this.data.toString();
  }
}
