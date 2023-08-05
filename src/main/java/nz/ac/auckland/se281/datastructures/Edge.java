package nz.ac.auckland.se281.datastructures;

/**
 * An edge in a graph that connects two verticies.
 *
 * <p>You must NOT change the signature of the constructor of this class.
 *
 * @param <T> The type of each vertex.
 */
public class Edge<T> {
  private T source;
  private T destination;

  /**
   * Initialises the edge with given source and destination.
   *
   * @param source source vertex
   * @param destination source destination
   */
  public Edge(T source, T destination) {
    this.source = source;
    this.destination = destination;
  }

  /**
   * Gets the source vertex of the edge.
   *
   * @return source vertex
   */
  public T getSource() {
    return this.source;
  }

  /**
   * Gets the destination vertex of the edge.
   *
   * @return destination vertex
   */
  public T getDestination() {
    return this.destination;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    // Ensure object's class is same as this before casting types
    if (obj.getClass() != this.getClass()) {
      return false;
    }

    Edge<T> other = (Edge<T>) obj;

    // Equals check checks if source and destination are the same.
    return (this.getSource() == other.getSource()
        && this.getDestination() == other.getDestination());
  }

  @Override
  public int hashCode() {
    // Custom hash to ensure all edges with same source and destination
    // are considered equal.
    return this.getSource().hashCode() + this.getDestination().hashCode();
  }
}
