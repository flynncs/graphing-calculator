package nz.ac.auckland.se281.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A graph that is composed of a set of verticies and edges.
 *
 * <p>You must NOT change the signature of the existing methods or constructor of this class.
 *
 * @param <T> The type of each vertex, that have a total ordering.
 */
public class Graph<T extends Comparable<T>> {
  /** Custom comparator for vertices in the graph. */
  class VertexComparator implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
      try {
        // Attempt to cast the objects to integers
        int firstVertex = Integer.parseInt((String) o1);
        int secondVertex = Integer.parseInt((String) o2);
        if (firstVertex < secondVertex) {
          return -1;
        } else if (firstVertex == secondVertex) {
          return 0;
        } else {
          return 1;
        }
      } catch (Exception e) {
        // Compare normally if exception.
        return compare(o1, o2);
      }
    }
  }

  private Set<T> vertices;
  private Set<Edge<T>> edges;

  /**
   * Intialises the graph and loads given vertices and edges into instance variables.
   *
   * @param verticies set of vertices of graph
   * @param edges set of edges of graph
   */
  public Graph(Set<T> verticies, Set<Edge<T>> edges) {
    this.vertices = verticies;
    this.edges = edges;
  }

  /**
   * Calculates and returns the roots of own instance's graph - will automatically determine the
   * correct roots to calculate based on the graph's equivalence.
   *
   * @return roots of own instance's graph
   */
  public TreeSet<T> getRoots() {
    // Perform correct calculation based on equivalence
    if (!this.isEquivalence()) {
      return this.getRootsNonEquivalence();
    } else {
      return this.getRootsEquivalence();
    }
  }

  /**
   * Calculates the roots for own equivalence graph - only to be called if and only if the graph is
   * reflexive.
   *
   * @return roots of instance's equivalence graph
   */
  private TreeSet<T> getRootsEquivalence() {
    // Use customer comparator for numerical ordering
    TreeSet<T> roots = new TreeSet<T>(new VertexComparator());

    for (T vertex : this.vertices) {
      Set<T> equivalenceClass = this.getEquivalenceClass(vertex);
      roots.add(Collections.min(equivalenceClass));
    }

    return roots;
  }

  /**
   * Calculates the roots of own graph assuming the graph is non equivalence. Must be called if and
   * only if the graph is non-equivalence.
   *
   * @return the roots of instance's non-equivalence graph
   */
  private TreeSet<T> getRootsNonEquivalence() {
    Set<T> nonRoots = new HashSet<T>();
    Set<T> rootCandidates = new HashSet<T>();

    // If vertex has an incoming edge, exclude from root candidates
    for (Edge<T> edge : this.edges) {
      nonRoots.add(edge.getDestination());
      rootCandidates.add(edge.getSource());
    }

    // Remove all excluded roots from candidates
    rootCandidates.removeAll(nonRoots);

    TreeSet<T> roots = new TreeSet<T>(new VertexComparator());
    roots.addAll(rootCandidates);

    return roots;
  }

  /**
   * Determines whether or not the instance's graph is reflexive.
   *
   * @return true/false, depending on the reflexivity of the graph
   */
  public boolean isReflexive() {
    for (T vertex : this.vertices) {
      Edge<T> reflexiveEdge = new Edge<T>(vertex, vertex);
      if (!this.edges.contains(reflexiveEdge)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Determines whether or not the instance's graph is symmetric.
   *
   * @return true/false, depending on the symmetry of the graph
   */
  public boolean isSymmetric() {
    for (Edge<T> edge : this.edges) {
      Edge<T> symmetricEdge = new Edge<T>(edge.getDestination(), edge.getSource());
      if (!this.edges.contains(symmetricEdge)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Determines whether or not the instance's graph is transitive.
   *
   * @return true/false, depending on the transitivty of the graph
   */
  public boolean isTransitive() {
    for (Edge<T> edge : this.edges) {
      for (Edge<T> otherEdge : this.edges) {
        // If a pair of edges that must have a third transitive edge is found:
        if (otherEdge.getSource() == edge.getDestination()) {
          // Determine the transitive edge, and search for it in edges
          Edge<T> transitiveEdge = new Edge<T>(edge.getSource(), otherEdge.getDestination());
          if (!this.edges.contains(transitiveEdge)) {
            return false;
          }
        }
      }
    }

    return true;
  }

  /**
   * Determines the transitivity of instance's graph.
   *
   * @return true/false depending on transitivity
   */
  public boolean isAntiSymmetric() {
    for (Edge<T> edge : this.edges) {
      Edge<T> symmetricEdge = new Edge<T>(edge.getDestination(), edge.getSource());
      if (this.edges.contains(symmetricEdge) && edge.getSource() != edge.getDestination()) {
        return false;
      }
    }

    return true;
  }

  /**
   * Determines whether or not the instance's graph displays equivalence.
   *
   * @return true/false depending on equivalence
   */
  public boolean isEquivalence() {
    return (this.isReflexive() && this.isSymmetric() && this.isTransitive());
  }

  /**
   * Finds and returns the equivalence class of a given vertex. If this equivalence class is empty,
   * an empty set witll be returned. If not, an unordered set of all equivalence vertices found will
   * be returned.
   *
   * @param vertex vertex to determine equivalence class of
   * @return a set of vertices belonging to equivalence class of given vertex
   */
  public Set<T> getEquivalenceClass(T vertex) {
    Set<T> equivalenceClass = new HashSet<T>();
    if (!this.isEquivalence()) {
      return equivalenceClass;
    }

    equivalenceClass.add(vertex);
    equivalenceClass.addAll(this.getAdjacent(vertex));

    return equivalenceClass;
  }

  /**
   * Performs an iterative breadth-first search on the instance's graph, returning a list of all
   * visited vertices in order.
   *
   * @return list of all vertices visited - ordered
   */
  public List<T> iterativeBreadthFirstSearch() {
    Queue<T> queue = new Queue<T>();
    LinkedHashSet<T> visited = new LinkedHashSet<T>();

    for (T root : this.getRoots()) {
      queue.enqueue(root);
    }

    // Loop until queue is empty, using FIFO principles
    while (!queue.isEmpty()) {
      T vertex = queue.dequeue();
      visited.add(vertex);
      // Find all adjacent vertices and add to queue.
      for (T adjacentVertex : this.getAdjacent(vertex)) {
        if (!visited.contains(adjacentVertex)) {
          queue.enqueue(adjacentVertex);
        }
      }
    }

    return new ArrayList<T>(visited);
  }

  /**
   * Performs an iterative depth-first search on the instance's graph. This will generate a list of
   * all visited vertices, in order.
   *
   * @return list of visited vertices - in order.
   */
  public List<T> iterativeDepthFirstSearch() {
    Stack<T> stack = new Stack<T>();
    LinkedHashSet<T> visited = new LinkedHashSet<T>();

    // Roots must be in descending order for depth-first
    for (T root : this.getRoots().descendingSet()) {
      stack.push(root);
    }

    while (!stack.isEmpty()) {
      T vertex = stack.pop();
      visited.add(vertex);
      // Loop through all adjacent vertices in descending order
      for (T adjacentVertex : this.getAdjacent(vertex).descendingSet()) {
        if (!visited.contains(adjacentVertex)) {
          stack.push(adjacentVertex);
        }
      }
    }
    return new ArrayList<T>(visited);
  }

  /**
   * Finds the adjacent vertices of a given vertex in the instance's graph.
   *
   * @param vertex vertex for which to find adjacent vertices
   * @return sorted set of adjacent vertices
   */
  private TreeSet<T> getAdjacent(T vertex) {
    TreeSet<T> adjacent = new TreeSet<T>(new VertexComparator());

    for (Edge<T> edge : this.edges) {
      // If the vertex is source and edge is not reflexive
      if (edge.getSource().equals(vertex) && !edge.getDestination().equals(vertex)) {
        adjacent.add(edge.getDestination());
      }
    }

    return adjacent;
  }

  /**
   * Performs a recursive breadth-first search of the instance's graph and returns a list of all
   * vertices visited, in order. Makes use of a recursive helper function, breadthRecursiveStep.
   *
   * @return list of all vertices visited in order.
   */
  public List<T> recursiveBreadthFirstSearch() {
    Queue<T> queue = new Queue<T>();
    LinkedHashSet<T> visited = new LinkedHashSet<T>();

    for (T root : this.getRoots()) {
      queue.enqueue(root);
    }

    // Perform recursion
    visited = this.breadthRecursiveStep(queue, visited);

    return new ArrayList<T>(visited);
  }

  /**
   * Helper function for the recursive breadth-first search. Base case of the given queue being
   * empty - in this case, the visited vertices will be returned. Recursive step of locating
   * adjacent vertices to queue'd vertices and adding them to the queue.
   *
   * @param queue the current queue of vertices to be searched
   * @param visited an ordered list of visited vertices
   * @return list of vertices visited, in order
   */
  private LinkedHashSet<T> breadthRecursiveStep(Queue<T> queue, LinkedHashSet<T> visited) {
    if (queue.isEmpty()) { // Base step - ends recursion when queue emptied
      return visited;
    }

    T vertex = queue.dequeue();
    visited.add(vertex);

    for (T adjacentVertex : this.getAdjacent(vertex)) {
      if (!visited.contains(adjacentVertex)) {
        queue.enqueue(adjacentVertex);
      }
    }

    // Continue recursion until base step
    return breadthRecursiveStep(queue, visited);
  }

  /**
   * Performs a recursive depth-first search on the instance's graph. Uses a helper methods,
   * depthRecursiveStep, to perform the recursion.
   *
   * @return list of vertices visited, in order
   */
  public List<T> recursiveDepthFirstSearch() {
    Stack<T> stack = new Stack<T>();
    LinkedHashSet<T> visited = new LinkedHashSet<T>();

    // Depth-first must use descending order to add to stack
    for (T root : this.getRoots().descendingSet()) {
      stack.push(root);
    }

    visited = this.depthRecursiveStep(stack, visited);

    return new ArrayList<T>(visited);
  }

  /**
   * Helper method for performing a depth-first recursive search. Base case of the stack being
   * empty, in which case the visited set is returned. Recursive step of finding all adjacent
   * vertices to those in the stack, and adding these to the stack.
   *
   * @param stack current stack of vertices to be searched
   * @param visited list of vertices visited, in order
   * @return list of vertices visited, in order.
   */
  private LinkedHashSet<T> depthRecursiveStep(Stack<T> stack, LinkedHashSet<T> visited) {
    if (stack.isEmpty()) { // Base step - stack is empty
      return visited;
    }

    T vertex = stack.pop();
    visited.add(vertex);

    // Must search smallest vertices first
    for (T adjacentVertex : this.getAdjacent(vertex).descendingSet()) {
      if (!visited.contains(adjacentVertex)) {
        stack.push(adjacentVertex);
      }
    }

    return depthRecursiveStep(stack, visited);
  }
}
