/*
 *  HashTable.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 4 - Anagram
 *  Alex Terikov (teraliv@uw.edu)
 *  12/17/15
 */

import java.util.LinkedList;
import java.util.List;


public class HashTable {

    // The size of the hash table.
    private Integer myTableSize;

    // Hash table is represented as array of Linked list
    // with same anagram words
    private List<Anagram>[] myHashTable;

    // Count hash table collistions.
    private int myCollisionsCount;

    // Count total number of added words.
    private int myWordsCount;


    /**
     * Constructs a new hash table.
     *
     * @param theSize - the size of a hash table.
     */
    public HashTable(int theSize) {
        myTableSize = theSize;
        myHashTable = new LinkedList[theSize];
        myCollisionsCount = 0;
        myWordsCount = 0;
    }

    /**
     * Method to add a new item to a hash table.
     *
     * @param a - anagram to add to a hash table.
     */
    public void add(Anagram a) {

        int hashCode = hash(a.getAnagram());
        myWordsCount++;

        // If its first item to be added at this particular hash code
        // then we need to instantiate linked list
        if (myHashTable[hashCode] == null)
            myHashTable[hashCode] = new LinkedList<>();

        // first anagram to add at this hash location
        if (myHashTable[hashCode].size() == 0) {
            myHashTable[hashCode].add(a);
        }
        // words with equal anagrams at the same hash location
        else if (myHashTable[hashCode].get(0).getAnagram().equals(a.getAnagram()))
            myHashTable[hashCode].add(a);

        // collision occurred
        else {
            addWithQuadProbing(hashCode, a);
            myCollisionsCount++;
        }
    }


    /**
     * Method to add an anagram to a hash table if the collistion
     * has occurred.
     *
     * @param hash - current hash value of an anagram.
     * @param a - current anagram.
     * @return returns a new good hash code for the given anagram.
     */
    private int addWithQuadProbing(int hash, Anagram a) {
        int i = 1;
        int hashCode = hash;

        // Find the next available bucket for the anagram, if the anagram is not in the hash table.
        while (myHashTable[hashCode] != null) {

            // if the anagram is in the hash table, just get the hash code of this bucket.
            if (myHashTable[hashCode].get(0).getAnagram().equals(a.getAnagram())) {
                break;
            }

            hashCode = hashCode + (int) Math.pow(i, 2);
            hashCode %= myTableSize;
            i++;
        }

        // found new bucket for an anagram.
        if (myHashTable[hashCode] == null)
            myHashTable[hashCode] = new LinkedList<>();

        // add it to the new bucket.
        myHashTable[hashCode].add(a);

        return hashCode;
    }


    /**
     * A method to find anagram's hash code if anagram is in the hash table.
     *
     * @param word - A anagram's word to find hash.
     * @return - returns anagram's hash code if found, otherwise -1.
     */
    private int getHashCode(String word) {
        Anagram a = new Anagram(word);

        int hash = hash(a.getAnagram());
        int count = 1;

        // anagram hash code exists
        if (myHashTable[hash] != null) {
            while (!myHashTable[hash].get(0).getAnagram().equals(a.getAnagram())) {
                hash = hash + (int) Math.pow(count, 2);
                hash %= myTableSize;
                count++;

                if (myHashTable[hash] == null) {
                    hash = -1;
                    break;
                }
            }
        }
        // hash code not in the table
        else
            hash = -1;

        return hash;
    }

    /**
     * A method to make anagram results.
     *
     * @param word - A word to find anagram results.
     * @return - string representation of anagram results.
     */
    public String getAnagramResult(String word) {
        StringBuilder results = new StringBuilder();
        int hash = getHashCode(word);

        if (hash != -1) {
            int size = findSimilarAnagrams(word, hash); // number of anagrams
            String current;

            results.append(word + " " + size);

            for (int i = 0; i < myHashTable[hash].size(); i++) {
                current = myHashTable[hash].get(i).getOriginal();
                if (!current.equals(word))
                    results.append(" " + current);

            }

        }
        else
            results.append(word + " 0");

        return results.toString();
    }

    /**
     * A method to find similar anagrams.
     * @param word - A word to find it's anagrams.
     * @param hash - Hash code for the given word.
     * @return - Returns the number of similar anagrams.
     */
    private int findSimilarAnagrams(String word, int hash) {

        String current;
        int size = myHashTable[hash].size();
        int result = 0;

        for (int i = 0; i < size; i++) {
            current = myHashTable[hash].get(i).getOriginal();
            if (!current.equals(word))
                result++;
        }

        return result;
    }


    /**
     * Hash function to calculate a hash code for the give string.
     *
     * @param key - string to hash.
     * @return returns hash code for the given string key.
     */
    private int hash(String key) {

        int hash = 17;
        for (int i = 0; i < key.length(); i++)
            hash = 37 * hash + key.charAt(i);

        hash %= myTableSize;

        if (hash < 0)
            hash += myTableSize;

        return hash;
    }

    /**
     * Total number of collisions.
     */
    public int getCollisioinsCount() {
        return myCollisionsCount;
    }

    /**
     * Total number of added words.
     */
    public int getWordsCount() {
        return myWordsCount;
    }

}
