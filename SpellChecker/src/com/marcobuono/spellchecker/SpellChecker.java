package com.marcobuono.spellchecker;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;

/**
 * The spellChecker class recognizes some misspelled words.
 * For those words, it provides a list of possible correct spellings.
 *
 */
public class SpellChecker {
	
	public static void main(String[] args) {
		
		HashSet<String> dictionary = new HashSet<>();
		HashSet<String> badWords = new HashSet<>();
		
		try {
			Scanner dictIn = new Scanner(getInputFileNameFromUser("dictionary")); // select the words.txt file
			while(dictIn.hasNext()) {
				String word = dictIn.next().toLowerCase();
				dictionary.add(word);
			}
			
			Scanner fileIn = new Scanner(getInputFileNameFromUser("input")); // select an input file to check
			fileIn.useDelimiter("[^a-zA-Z]+");
			while(fileIn.hasNext()) {
				String wordToCheck = fileIn.next().toLowerCase();
				if(!dictionary.contains(wordToCheck) &&     // check if the word is in the dictionary
						!badWords.contains(wordToCheck)) { // and if it is already verified
					badWords.add(wordToCheck);
					TreeSet<String> suggestions = corrections(wordToCheck, dictionary);
					System.out.print(wordToCheck + ": ");
					if(suggestions.size() > 0) {
						String firstSuggestion = suggestions.first();  // Find out the first page number in the set.
						for(String item : suggestions) {
							if(item != firstSuggestion) {
								System.out.print(", "); // Output comma only if this is not the first page.
							}
							System.out.print(item);
						}
						System.out.print("\n");
					} else {
						System.out.println("(no suggestions)");
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary file not found, pleace check the path");
		}
	}
	
	/**
     * Lets the user select an input file using a standard file
     * selection dialog box.  If the user cancels the dialog
     * without selecting a file, the return value is null.
     */
    static File getInputFileNameFromUser(String fileFor) {
    	JFileChooser fileDialog = new JFileChooser();
    	fileDialog.setDialogTitle("Select File for " + fileFor);
    	int option = fileDialog.showOpenDialog(null);
    	if (option != JFileChooser.APPROVE_OPTION)
    		return null;
    	else
    		return fileDialog.getSelectedFile();
    }
    
    /**
     * The method try to propose suggestions for misspelled word.
     * 
     * @param badWord, the word misspelled
     * @param dictionary, where to find suggestions
     * @return corrections, an ordered set of possible suggestions
     */
    static TreeSet<String> corrections(String badWord, HashSet<String> dictionary) {
    	TreeSet<String> corrections = new TreeSet<>();
    	
    	String subBadWord1;
    	String subBadWord2;
    	String newWordToCheck;
    	
    	for(int i = 0; i< badWord.length(); i++) {
    		// To try all possible corrections, need to traverse the word for its length
    		
    		subBadWord1 = badWord.substring(0,i); // split the word removing the ith character
    		subBadWord2 = badWord.substring(i+1);
    		
    		// 1. Delete any one of the letters from the misspelled word.
    		newWordToCheck = subBadWord1 + subBadWord2; // recomposing the word without one char
    		if(dictionary.contains(newWordToCheck)) {
				corrections.add(newWordToCheck);
			}
    		
    		// 2. Change any letter in the misspelled word to any other letter.
    		for (char ch = 'a'; ch <= 'z'; ch++) {
    			newWordToCheck = subBadWord1 + ch + subBadWord2; // recomposing the word adding the 
    															 // ch in the ith position
    			if(dictionary.contains(newWordToCheck)) {
    				corrections.add(newWordToCheck);
    			}
    		}
    		
    		// 3. Insert any letter at any point in the misspelled word.
    		subBadWord2 = badWord.substring(i); // split the word without removing any char
    		for (char ch = 'a'; ch <= 'z'; ch++) {
    			for(int j = 0; j <= badWord.length(); j++) {
    				// try to compose a new word adding the ch in the jth position
    				
    				newWordToCheck = subBadWord1 + subBadWord2;
    				List<Character> charList = newWordToCheck.chars()  // convert the word in a char list
                            .mapToObj(e -> (char)e)
                            .collect(Collectors.toList());
    				charList.add(j, ch); // add the ch in the jth position
    				
    				// reconvert the char list into a string
    				newWordToCheck = charList.stream().map(e->e.toString()).collect(Collectors.joining());
    				if(dictionary.contains(newWordToCheck)) {
        				corrections.add(newWordToCheck);
        			}
    			}
    		}
    		
    		
    		// 4. Insert a space at any point in the misspelled word (and check
    		//    that both of the words that are produced are in the dictionary)
    		if(dictionary.contains(subBadWord1) && 
    				dictionary.contains(subBadWord2)) {
    			// if both the splitted words are in the dictionary
    			// add them separeted by a space
    			corrections.add(subBadWord1 + " " + subBadWord2);
    		}
    	}
    	
    	// 5. Swap any two neighboring characters in the misspelled word.
		for(int i = 1; i < badWord.length(); i++) {
    		char[] charList = badWord.toCharArray(); // convert the string in a char list
    		char previousChar = charList[i - 1]; // store the previous char
    		charList[i-1] = charList[i]; // substitute the previous char with the char on the right
    		charList[i] = previousChar; // substitute the ith char with the char on the left
    		if(dictionary.contains(new String(charList))) {
				corrections.add(new String(charList));
			}
    	}
		return corrections;
    }

}
