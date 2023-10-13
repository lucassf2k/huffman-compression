package services.core.huffman;

public class Node {
  private int frequency;
  private char character;
  private Node left, right;

  public int getFrequency() {
    return frequency;
  }
  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }
  public char getCharacter() {
    return character;
  }
  public void setCharacter(char character) {
    this.character = character;
  }
  public Node getLeft() {
    return left;
  }
  public void setLeft(Node left) {
    this.left = left;
  }
  public Node getRight() {
    return right;
  }
  public void setRight(Node right) {
    this.right = right;
  }
}
