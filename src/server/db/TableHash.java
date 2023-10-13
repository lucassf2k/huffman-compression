package server.db;

import libs.FileController;
import server.entities.Vehicle;


public class TableHash {
  private int M;
  private Node table[];
  private int numberOfElementsAdded = 0;
  
  public TableHash() {
  }

  public TableHash(int length) {
    this.M = length;
    this.table = new Node[this.getM()];
  }

  private int hash(long k) {
    return (int)(k % this.getM());
  }

  public void insert(Vehicle k) {
    var h = this.hash(k.getRenavam());
    var node = this.table[h];
    while (node != null) {
      if (node.getKey() == k.getRenavam()) break;
      node = node.getNext();
    }
    if (node == null) {
      node = new Node();
      node.setKey(k.getRenavam());
      node.setNext(this.table[h]);
      node.setVehicle(k);
      this.table[h] = node;
      numberOfElementsAdded++;
      FileController.Write("./src/server/logs/logs.txt", "ADD: " + String.format("%.4f", this.getLoadFactor()));
    }
  }

  public void insertAtEnd(Vehicle k) {
    var c = this.hash(k.getRenavam());
    var current = this.table[c];
    Node previous = null;
    if (current == null) {
      this.table[c] = new Node(k, null);
    } else {
      while(current != null) {
        if (current.getKey() == k.getRenavam()) break;
        previous = current;
        current = current.getNext();
      }
      if (current == null) {
        Node newNode = new Node(k, null);
        previous.setNext(newNode);
      }
    }
  }

  public Node search(long k) {
    var h = this.hash(k);
    var node = this.table[h];
    while(node != null) {
      if (node.getKey() == k) return node;
      node = node.getNext();
    }
    return null;
  }

  public Node searchByLicensePlate(String licensePlate) {
    for (int i = 0; i < M; i++) {
        Node current = table[i];
        while (current != null) {
            if (current.getVehicle().getLicencePlate().equals(licensePlate)) {
                return current;  // Veículo encontrado
            }
            current = current.getNext();
        }
    }
    return null;  // Veículo não encontrado
  }

  public void update(long renavam, Vehicle updatedVehicle) {
    int hashValue = this.hash(renavam);
    Node currentNode = table[hashValue];
    while (currentNode != null) {
      if (currentNode.getVehicle().getRenavam() == renavam) {
          currentNode.setVehicle(updatedVehicle);
          return; // Veículo encontrado e atualizado
      }
      currentNode = currentNode.getNext();
    }
    System.out.println("Veículo com o renavam " + renavam + " não encontrado.");
  }

  public void delete(long renavam) {
    int hashValue = this.hash(renavam);
    if (table[hashValue] == null) {
        System.out.println("Não há veículo com o renavam informado.");
        return;
    }
    Node current = table[hashValue];
    Node previous = null;
    while (current != null && current.getKey() != renavam) {
        previous = current;
        current = current.getNext();
    }
    if (current == null) {
        System.out.println("Não há veículo com o renavam informado.");
        return;
    }
    // Removendo o nó
    if (previous == null) {
        System.out.println("Veículo removido: " + table[hashValue]);
        table[hashValue] = current.getNext();
        this.numberOfElementsAdded--;
        FileController.Write("./src/server/logs/logs.txt", "DEL: " + String.format("%.4f", this.getLoadFactor()));
    } else {
        previous.setNext(current.getNext());
    }
    System.out.println("Veículo com renavam " + renavam + " removido com sucesso.");
}

  public void show() {
    Node node;
    for (int i = 0; i < this.getM(); i++) {
      node = this.table[i];
      System.out.println(i);
      while (node != null) {
        System.out.println(" --> " + node.toString());
        node = node.getNext();
      }
      System.out.println();
    }
  }

  private float getLoadFactor() {
    float lenAdded = (float)this.getNumberOfElementsAdded();
    float lenTable = (float)this.getM();
    return lenAdded / lenTable;
  }


  public int getM() {
    return M;
  }

  public void setM(int m) {
    M = m;
  }

  public Node[] getTable() {
    return table;
  }

  public void setTable(Node[] table) {
    this.table = table;
  }

  public int getNumberOfElementsAdded() {
    return numberOfElementsAdded;
  }

  public void setNumberOfElementsAdded(int numberOfElementsAdded) {
    this.numberOfElementsAdded = numberOfElementsAdded;
  }
}