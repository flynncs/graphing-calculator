package nz.ac.auckland.se281.datastructures;

/**
 * Linked list data structure that utilises nodes to store each data element, along with a reference
 * to the next node. This structure only has references to the head and tail nodes.
 *
 * @param <T> The type of each data.
 */
public class LinkedList<T> {
  private Node<T> head;
  private Node<T> tail;
  private int size;

  /** Initialise empty linked list with size 0. */
  public LinkedList() {
    this.size = 0;
  }

  /**
   * Adds given data to the linked list in the form of a node.
   *
   * @param data data to be added.
   */
  public void add(T data) {
    // If list is empty, create head/tail and return
    if (this.size == 0) {
      this.head = new Node<T>(data);
      this.tail = this.head;
      this.size++;
      return;
    }

    // Ensure tail is correctly updated
    Node<T> currentTail = this.tail;
    Node<T> newTail = new Node<T>(data);
    currentTail.setNext(newTail);
    this.tail = newTail;
    this.size++;
  }

  /**
   * Inserts given data at the head of the linked list - i.e. given data will become the new head,
   * all other points right shifted by one.
   *
   * @param data data to be inserted.
   */
  public void insertAtHead(T data) {
    // Check if list is empty/no head exists first
    if (this.head == null) {
      this.add(data);
      return;
    }

    Node<T> newHead = new Node<T>(data);
    newHead.setNext(this.head);
    this.head = newHead;
    this.size++;
  }

  /**
   * Removes the first element (i.e. the head) from the list and returns it.
   *
   * @return head of linked list.
   */
  public T removeFirst() {
    // Ensure that head exists first
    if (this.size == 0) {
      return null;
    }

    T data = this.head.getData();
    this.head = this.head.getNext();
    this.size--;

    return data;
  }

  /**
   * Gets the first element (i.e. the head) of the linked list without removing it.
   *
   * @return data of first element of linked list.
   */
  public T getFirst() {
    return this.head.getData();
  }

  /**
   * Gets the size of the linked list (number of elements/nodes).
   *
   * @return size of linked list.
   */
  public int size() {
    return this.size;
  }

  /**
   * Determines if the linked list is empty or not.
   *
   * @return true/false depending on emptiness of list.
   */
  public boolean isEmpty() {
    return (this.size == 0);
  }

  @Override
  public String toString() {
    // Format: [x, y, z]
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    Node<T> currentNode = this.head;
    // Loop through all nodes and append data to string builder
    for (int i = 0; i < this.size; i++) {
      stringBuilder.append(currentNode.getData());
      // If current node isnt last, append the ", " to string builder
      if (!currentNode.equals(tail)) {
        stringBuilder.append(", ");
        currentNode = currentNode.getNext();
      }
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}
