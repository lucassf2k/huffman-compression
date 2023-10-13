package services.core.huffman;

import java.util.ArrayList;
import java.util.List;

public class Heap {
  private List<Node> heap;
  
  Heap() {
    this.heap = new ArrayList<Node>();
  }

  public void insert(Node node) {
    var size = this.heap.size();
    if (size == 0) {
      this.heap.add(node);
    } else {
      this.heap.add(node);
      this.moveUp(size - 1, this.heap);
    }
  }

  public void moveUp(int k, List<Node> heap) {
    int j;
    Node tmp;
    double fatherValue = (k - 1) / 2;
    j = this.floor(fatherValue);
    if (j >= 0 && heap.get(k).getFrequency() < heap.get(j).getFrequency()) {
      tmp = heap.get(k);
      heap.set(k, heap.get(j));
      heap.set(j, tmp);
      this.moveUp(j, heap);
    }
  }

  public int floor(double n) {
    return (int) n;
  }

  public Node remove() {
    var size = this.heap.size();
    Node removed;
    if (size != 0) {
      removed = this.heap.get(0);
      this.heap.set(0, this.heap.get(size - 1));  
      this.heap.remove(size - 1);
      this.moveDown(0, this.heap, --size);
      return removed;
    }
    return null;
  }

  public void moveDown(int k, List<Node> heap, int sizeHeap) {
    int j;
    Node tmp;
    j = 2 * k + 1;
    if (j < sizeHeap) { // indice é válidos
      if (j < sizeHeap - 1) { // há indices a frente
        if (heap.get(j).getFrequency() > heap.get(j + 1).getFrequency()) j++;
      }
      if (heap.get(j).getFrequency() < heap.get(k).getFrequency()) {
        tmp = heap.get(k);
        heap.set(k, heap.get(j));
        heap.set(j, tmp);
        this.moveDown(j, heap, sizeHeap);
      }
    }
  }

  public void buildHeap() {
    int k;
    var size = this.heap.size();
    for (k = this.floor(size / 2) - 1; k >= 0; k--) {
      this.moveDown(k, this.heap, size);
    }
  }

  public int size() {
    return this.heap.size();
  }
}
