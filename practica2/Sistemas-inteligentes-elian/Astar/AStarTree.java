import java.util.*;

public class AStarTree {

  private static final int ROWS = 60;
  private static final int COLS = 80;
  private static final int MAX_ITERATIONS = ROWS * COLS;

  // posibles vecinos que se pueden verificar
  // que son arriba, abajo, izquierda y derecha
  private static final int[][] NEIGHBORS = { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };

  // la heuristica es la distancia euclidiana, que es 
  // la raiz cuadrada de la suma de los catetos al cuadrado 
  // C^2 = A^2 + B^2
  private static int heuristic(int row1, int col1, int row2, int col2) {
    // Heuristic function: Euclidean distance
    int dRow = row1 - row2;
    int dCol = col1 - col2;
    return (int) Math.sqrt(dRow * dRow + dCol * dCol);
  }

  // la distancia es la distancia de manhattan
  // que es la suma de los valores absolutos de la diferencia en la fila y la columna
  private static int distance(int row1, int col1, int row2, int col2) {
    // Heuristic function: Euclidean distance
    int dRow = row1 - row2;
    int dCol = col1 - col2;
    return (int) Math.abs(dRow) + Math.abs(dCol);
  }

  // se obtiene el camino desde el nodo final hasta el nodo inicial
  // tomando el atributo padre de cado nodo
  public static List<Node> getPath(Node endNode) {
    List<Node> path = new ArrayList<>();
    Node currentNode = endNode;
    while (currentNode != null) {
      path.add(currentNode);
      currentNode = currentNode.parent;
    }
    Collections.reverse(path);
    return path;
  }

  // se obtienen los vecinos de un nodo
  // recorriendo la lista NEIGHBORS definida arriba,
  // verificando que el nodo no este fuera de los limites de la matriz
  // que el nodo no sea un obstaculo
  // se crea un nuevo nodo con los valores de la fila, columna, costo, heuristica y padre
  public static List<Node> getNeighbors(Node node, boolean[][] grid, int[] end) {
    List<Node> neighbors = new ArrayList<>();
    for (int i = 0; i < NEIGHBORS.length; i++) {
      int newRow = node.row + NEIGHBORS[i][0];
      int newCol = node.col + NEIGHBORS[i][1];
      if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS && !grid[newRow][newCol]) {
        int cost = distance(node.getRow(), node.getCol(), newRow, newCol);
        Node neighbor = new Node(newRow, newCol, node.g + cost, heuristic(newRow, newCol, end[0], end[1]), node);
        neighbors.add(neighbor);
      }
    }
    return neighbors;
  }

  // se implementa el algoritmo A* de acuord al pseudocodigo
  // usando una PriorityQueue para el open set para obtener siempre el vecino con mayor F
  public static Node aStar(boolean[][] grid, int[] start, int[] end) {
    Node startNode = new Node(start[0], start[1], 0, 0, null);
    PriorityQueue<Node> openSet = new PriorityQueue<>();
    Set<Node> closedSet = new HashSet<>();
    openSet.offer(startNode);

    int iterations = 0;
    while (!openSet.isEmpty() && iterations < MAX_ITERATIONS) {
      iterations++;
      Node current = openSet.poll();
      if (current.row == end[0] && current.col == end[1]) {
        return current;
      }
      closedSet.add(current);
      for (Node neighbor : getNeighbors(current, grid, end)) {
        if (closedSet.contains(neighbor)) {
          continue;
        }
        int tentativeG = current.g + distance(current.getRow(), current.getCol(), neighbor.getRow(), neighbor.getCol());
        if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
          neighbor.g = tentativeG;
          neighbor.parent = current;
          if (!openSet.contains(neighbor)) {
            openSet.offer(neighbor);
          }
        }
      }
    }
    return null;
  }
}
