package nz.ac.auckland.se281.datastructures;

/**
 * Queue data structure that utilises a linked list to store data elements. Operates on FIFO
 * principle.
 *
 * @param <T> The type of each element's data.
 */
public class Queue<T> {
  private LinkedList<T> queue;

  /** Initialises an empty queue. */
  public Queue() {
    queue = new LinkedList<T>();
  }

  /**
   * Add's given data to end of queue.
   *
   * @param data data to be added
   */
  public void enqueue(T data) {
    queue.add(data);
  }

  /**
   * Removes and returns data of element at front of queue. Returns null if queue is empty.
   *
   * @return data of element at front of queue - null if empty.
   */
  public T dequeue() {
    // Check queue is not empty
    if (queue.size() > 0) {
      return queue.removeFirst();
    }
    return null;
  }

  /**
   * Returns element at front of queue.
   *
   * @return element at front of queue
   */
  public T peek() {
    return queue.getFirst();
  }

  /**
   * Determines if queue is empty.
   *
   * @return true/false depending on emptiness of queue
   */
  public boolean isEmpty() {
    return (queue.size() == 0);
  }

  @Override
  public String toString() {
    return queue.toString();
  }
}
