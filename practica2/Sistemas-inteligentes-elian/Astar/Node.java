public class Node implements Comparable<Node> {
  int row;
  int col;
  int g;
  int h;
  Node parent;

  public Node(int row, int col, int g, int h, Node parent) {
    this.row = row;
    this.col = col;
    this.g = g;
    this.h = h;
    this.parent = parent;
  }

  public int getF() {
    return g + h;
  }

  public int getG() {
    return g;
  }

  public int getH() {
    return h;
  }

  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }

  public Node getParent() {
    return parent;
  }

  // se usa en el algoritmo A* cuando se crea una PriorityQueue para representar el open set
  // al traer un nuevo nodo con openSet.poll(), viene y compara de esta manera
  // compara solo el atributo F y devuelve el mayor
  @Override
  public int compareTo(Node other) {
    return Integer.compare(getF(), other.getF());
  }

  // cuando se verifica si el vecino ya esta en el conjunto de abierto o cerrado
  // closedSet.contains(neighbor), !openSet.contains(neighbor)
  // se usa contains() el cual usa este metodo que compara si la fila y la columna son iguales
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Node) {
      Node otroNodo = (Node) obj;
      return this.row == otroNodo.row && this.col == otroNodo.col;
    }
    return false;
  }
}
