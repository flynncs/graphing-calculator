package nz.ac.auckland.se281.datastructures;

/**
 * Stack data structure that stores data of type T - LIFO policy.
 *
 * @param <T> The type of each element's data, that have a total ordering.
 */
public class Stack<T> {
  private LinkedList<T> stack;

  /** Initialises the stack to empty. */
  public Stack() {
    this.stack = new LinkedList<T>();
  }

  /**
   * Pushes given data to stop of the stack.
   *
   * @param data data to add to stack.
   */
  public void push(T data) {
    this.stack.insertAtHead(data);
  }

  /**
   * Pops and returns the top element on the stack. If stack is empty, null is returned.
   *
   * @return top element on stack - null if empty.
   */
  public T pop() {
    return this.stack.removeFirst();
  }

  /**
   * Gets top element of stack without removing it.
   *
   * @return top element from stack.
   */
  public T peek() {
    return this.stack.getFirst();
  }

  /**
   * Gets the size of the stack.
   *
   * @return size of stack
   */
  public int size() {
    return this.stack.size();
  }

  /**
   * Determines whether or not stack is empty.
   *
   * @return true/false depending on empty or not
   */
  public boolean isEmpty() {
    return this.stack.isEmpty();
  }

  @Override
  public String toString() {
    return this.stack.toString();
  }
}
