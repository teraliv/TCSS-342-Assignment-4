/*
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  For the hashing function I am using pretty much the same algorithm as Java's API did. I am using the
 *  example that Joshua Bloch recommends in his popular Java book Effective Java. I have optimized his example to
 *  work with string. The main idea behind this hashing function is to make initial hash code equal to prime number
 *  in my case it is 17. Then multiply hash code by another prime 37 and add the ASCII code value of the current
 *  character and follow this sequence till the last character of the word. Then mod the calculated value with hash
 *  table size. it gives pretty much stable hash code. It seem to operate a bit better compare to Java's API O(log2n).
 *  The collision is handled by the Quadratic Probing Method which helps to eliminate primary clustering
 *  and follow the quadratic formula of F(i) = i^2.
 *
 *  Hash function:
 *  int hash = 17
 *  for (int i = 0; i < string.length(); i++)
 *      hash = 37 * hash + string.charAt(i);
 *
 *  Work cited:
 *  Joshua Bloch. Effective Java Second Edition. Chapter 3, page 48. Methods common to all objects.
 *
 *  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *  HashTable.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 4 - Anagram Queries using Hashing
 *  Alex Terikov (teraliv@uw.edu)
 *  12/17/15
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;


public class HashTester {

    // hash table for anagrams.
    private static HashTable ht;


    public static void main(String[] args) {
        ht = new HashTable(199967);
        //ht = new HashTable(17);

        readDictionaryWords();
        readInputsAndWriteResults(args[0], args[1]);

        System.out.println("Total words: " + ht.getWordsCount());
        System.out.println("Total collisions: " + ht.getCollisioinsCount());

    }


    /**
     * A method to read dictionary file with words to be added to
     * the hash table.
     */
    private static void readDictionaryWords() {

        Scanner fs = null;

        String line;
        Anagram a;

        try {
            //fs = new Scanner(new FileInputStream("words_simple.txt"));
            fs = new Scanner(new FileInputStream("words.txt"));

            while (fs.hasNextLine()) {
                line = fs.nextLine().toLowerCase();

                // ignore words with punctuations.
                if (!(line.contains("'") || line.contains("."))) {
                    a = new Anagram(line);
                    ht.add(a);
                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Error reading the file " + ex.getMessage());

        } finally {
            if (fs != null)
                fs.close();
        }
    }

    /**
     * A method to read test words to find the anagrams of it, and write
     * output results to the file.
     *
     * @param inputFile - A file containing test words.
     * @param outputFile - A file to write output results.
     */
    private static void readInputsAndWriteResults(String inputFile, String outputFile) {
        Scanner fs = null;
        PrintWriter writer = null;

        try {
            fs = new Scanner(new FileInputStream(inputFile));
            writer = new PrintWriter(new FileOutputStream(outputFile));

            while (fs.hasNextLine()) {
                //System.out.println(ht.getAnagramResult(fs.nextLine()));
                writer.println(ht.getAnagramResult(fs.nextLine()));
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Error reading the file " + ex.getMessage());

        } finally {
            if (fs != null)
                fs.close();
            if (writer != null)
                writer.close();
        }

    }
}
