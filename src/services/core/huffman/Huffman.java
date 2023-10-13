package services.core.huffman;

import java.util.Arrays;
import java.util.LinkedList;

public class Huffman {
  private Node root;
  private String[] codes;
  private static final int ASCII_COUNT = 128;

  public void buildHuffmanTree(int n, char[] chars, int[] frequencies) {
    Heap minimumHeap = new Heap();
    Node node;
    for (int i = 0; i < n; i++) {
      node = new Node();
      node.setCharacter(chars[i]);
      node.setFrequency(frequencies[i]);
      node.setLeft(null);
      node.setRight(null);
      minimumHeap.insert(node);
    }
    this.setRoot(null);
    while(minimumHeap.size() > 1) {
      var x = minimumHeap.remove();
      var y = minimumHeap.remove();
      Node z = new Node();
      z.setCharacter('-');
      z.setLeft(x);
      z.setRight(y);
      z.setFrequency(x.getFrequency() + y.getFrequency());
      this.setRoot(z);
      minimumHeap.insert(z);
    }
  }

  public void print(Node node, String str) {
    if (
      node.getLeft() == null && 
      node.getRight() == null && 
      this.getRoot().getCharacter() == '-'
    ) {
      System.out.println(node.getCharacter() + ";" + str);
      return;
    } else {
      this.print(node.getLeft(), str.concat("0"));
      this.print(node.getRight(), str.concat("1"));
    }
  }

  public String compress(String value) {
    var chars = getChars(value);
    Arrays.sort(chars);
    var frequenciesChars = getFrequenciesChars(value); 
    this.buildHuffmanTree(chars.length, chars, frequenciesChars);
    this.codes = new String[ASCII_COUNT];
    this.buildCodes(this.getRoot(), "");
    var valueCompressed = new StringBuilder();
    for (var i = 0; i < value.length(); i++) {
      var ch = value.charAt(i);
      valueCompressed.append(this.codes[ch]);
    }
    return valueCompressed.toString();
  }

  private char[] getChars(String str) {
    var existingChars = new boolean[ASCII_COUNT];
    var uniqueChars = new StringBuilder();
    var strInArray = str.toCharArray();
    for (char ch : strInArray) {
      if (!existingChars[ch]) {
        existingChars[ch] = true;
        uniqueChars.append(ch);
      }
    }
    var chars = new char[uniqueChars.length()];
    uniqueChars.getChars(0, uniqueChars.length(), chars, 0);
    return chars;
  }

  private int[] getFrequenciesChars(String str) {
    var frequenciesChars = new int[ASCII_COUNT];
    var strInArray = str.toCharArray();
    for (char ch : strInArray) {
      if (ch >= 0 && ch <= 127) {
        if (
          Character.isLetterOrDigit(ch) ||
          Character.isWhitespace(ch)
        ) {
          frequenciesChars[ch]++;
        } else if (
          ch == '/' ||
          ch == '.' ||
          ch == '-' || 
          ch == '#'
        ) {
          frequenciesChars[ch]++;
        }
      }
    }
    return this.removeZeros(frequenciesChars);
  }

  private int[] removeZeros(int[] frequenciesChars) {
    var nonZeroList = new LinkedList<Integer>();
    for (var frequencyChar : frequenciesChars) {
      if (frequencyChar != 0) nonZeroList.add(frequencyChar);
    }
    var nonZeros = new int[nonZeroList.size()];
    for (var i = 0; i < nonZeros.length; i++) {
      nonZeros[i] = nonZeroList.get(i);
    }
    return nonZeros;
  }

  private void buildCodes(Node node, String code) {
    if (node == null) return;
    if (node.getLeft() == null && node.getRight() == null) {
      this.codes[node.getCharacter()] = code;
    }
    this.buildCodes(node.getLeft(), code + "0");
    this.buildCodes(node.getRight(), code + "1");
  }

  public String decompress(String strCompressed) {
    var strDecompressed = new StringBuilder();
    var node = this.getRoot();
    var index = 0;
    while (index < strCompressed.length()) {
      var bit = strCompressed.charAt(index);
      if (bit == '0') {
        node = node.getLeft();
      } else if (bit == '1') {
        node = node.getRight();
      }
      if (node.getLeft() == null && node.getRight() == null) {
        strDecompressed.append(node.getCharacter());
        node = this.getRoot();
      }
      index++;
    }
    return strDecompressed.toString();
  } 

  public Node getRoot() {
    return root;
  }
  public void setRoot(Node root) {
    this.root = root;
  }
  public String[] getCodes() {
    return codes;
  }
  public void setCodes(String[] codes) {
    this.codes = codes;
  }
  
}
