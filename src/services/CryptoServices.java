package services;

import services.core.huffman.Huffman;

public class CryptoServices {
 private static Huffman huffman = new Huffman();

 public static String compress(String input) {
  return huffman.compress(input);
 }

 public static String decompress(String input) {
  return huffman.decompress(input);
 }
}
