/*
 *  Anagram.java
 *  TCSS 342 - Autumn 2015
 *
 *  Assignment 4 - Anagram
 *  Alex Terikov (teraliv@uw.edu)
 *  12/17/15
 */

import java.util.Arrays;

/**
 * This class represents anagram of a give word.
 */
public class Anagram {

    // Original word.
    private String myWord;

    // Anagram of orginal word.
    private String myAnagram;

    /**
     * Constructs an anagram object for the given word.
     *
     * @param theWord - the word to make an anagram.
     */
    public Anagram(String theWord) {
        myWord = theWord;
        myAnagram = makeAnagram();
    }

    // getters and setters
    public String getOriginal() {
        return myWord;
    }


    public String getAnagram() {
        return myAnagram;
    }

    /**
     * Makes an anagram.
     * @return - anagram of the word.
     */
    private String makeAnagram() {

        char[] chars = myWord.toCharArray();
        Arrays.sort(chars);
        String anagram = new String(chars);

        return anagram;
    }
}
