import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class TokenReader {

	public static void main(String[] args){
		System.out.println("* The program starts at " + getCurrentTime());
		
		//Share
		List<String> tokensList = tokenizeFile();
		
		//Part A
//		printTokenList(tokensList);
		
		//Part B
//		Map<String, Integer> tokensFrequencies 	= computeWordFrequencies(tokensList);
//		printTokensFrequencies(tokensFrequencies, tokensList);
		
		//Part C
//		Map<String, Integer> twoGramsFrequencies = computeTwoGramsFrequencies(tokensList);
//		printTwoGramsFrequencies(twoGramsFrequencies, tokensList);
		
		//Part D
		Map<String, Integer> palindromeFrequencies = computePalindromeFrequencies(tokensList);
		printPalindromeFrequencies(palindromeFrequencies);
		
		System.out.println("* The program ends at " + getCurrentTime());
	}
	
	//*** PART A : Utilities ***
	public static List<String> tokenizeFile(){
		String text = new String();
		
		//Read text file
		System.out.println("A01 - Start reading file at " + getCurrentTime());
		try{
			text = FileUtils.readFileToString(new File("text-test.txt"));
			System.out.println("A01 - End reading file at " + getCurrentTime());
		}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}
		
		//Replace special characters with whitespace
		System.out.println("A02 - Start processing special chars at " + getCurrentTime());
		text = text.replaceAll("[^A-Za-z0-9]", " ");
		System.out.println("A02 - End processing special chars at " + getCurrentTime());
		
		//Split text into tokens
		System.out.println("A03 - Start spliting tokens at " + getCurrentTime());
		String[] tokens = text.trim().split("\\s+");		
		System.out.println("A03 - End spliting tokens at " + getCurrentTime());
		
		//Add each token to array list
		System.out.println("A04 - Start creating tokens array at " + getCurrentTime());
		List<String> tokensList = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++){
			if(tokens[i].length() > 1){
				tokensList.add( tokens[i].toLowerCase() );
			}
		}
		System.out.println("A04 - End creating tokens array at " + getCurrentTime());
		
		return tokensList;
	}

	public static void printTokenList(List<String> tokensList){
	    String textResult = new String();
	    
	    System.out.println("A05 - Start preparing result string at " + getCurrentTime());
	    textResult = "### List of tokens ###\n\n";
	    textResult += "Total number of token: " + tokensList.size() + "\n\n";
		for(int i = 0; i < tokensList.size(); i++){
//			System.out.println(tokensList.get(i));
			textResult += tokensList.get(i) + "\n";
		}
		textResult += "----- END OF RESULT -----";
		System.out.println("A05 - End preparing result string at " + getCurrentTime());
		
		//Write result to text file
		System.out.println("A06 - Start writing result file at " + getCurrentTime());
		try{
			File file = new File("result.txt");
			FileUtils.writeStringToFile(file, textResult);
			System.out.println("A06 - End writing result file at " + getCurrentTime());
			System.out.println("Result is ready!");
		}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}	
	}
	
	//*** PART B : Word Frequencies ***
	public static Map<String, Integer> computeWordFrequencies(List<String> tokensList){
		Map<String, Integer> tokensFrequencies = new HashMap<String, Integer>();
		
		//Compute token and its frequency
		System.out.println("B01 - Start computing token and frequency at " + getCurrentTime());
		for(int i = 0; i < tokensList.size(); i++){
			if(tokensFrequencies.containsKey(tokensList.get(i))){
				int currentFrequency = (int) tokensFrequencies.get(tokensList.get(i));
				currentFrequency++;
				tokensFrequencies.put(tokensList.get(i).toString(), currentFrequency);
			}else{
				//If there is no token in the hashmap, add new
				tokensFrequencies.put(tokensList.get(i).toString(), 1);
			}

		}
		System.out.println("B01 - End computing token and frequency at " + getCurrentTime());
		
		return tokensFrequencies;
	}
	
	public static void printTokensFrequencies(Map<String, Integer> tokensFrequencies, List<String> tokensList){
		//Sort by highest frequencies first
		sortByFrequency("Word Frequencies", tokensFrequencies, tokensList);
	}
	
	//*** PART C : 2-grams ***
	public static Map<String, Integer> computeTwoGramsFrequencies(List<String> tokensList){
		//Produce 2-grams
		System.out.println("C01 - Start generating 2-grams array list at " + getCurrentTime());
		List<String> twoGramsList = new ArrayList<String>();
		for(int i = 0; i < tokensList.size() - 1; i++){
			twoGramsList.add( tokensList.get(i) + " " + tokensList.get(i+1) );
		}
		System.out.println("C01 - End generating 2-grams array list at " + getCurrentTime());
		
		//Compute token and its frequency
		System.out.println("C02 - Start computing token and frequency at " + getCurrentTime());
		Map<String, Integer> twoGramsFrequencies = new HashMap<String, Integer>();
		for(int i = 0; i < twoGramsList.size(); i++){
			if(twoGramsFrequencies.containsKey(twoGramsList.get(i))){
				int currentFrequency = (int) twoGramsFrequencies.get(twoGramsList.get(i));
				currentFrequency++;
				twoGramsFrequencies.put(twoGramsList.get(i), currentFrequency);
			}else{
				//If there is no 2 grams in the hashmap, add new
				twoGramsFrequencies.put(twoGramsList.get(i), 1);
			}

		}
		System.out.println("C02 - End computing token and frequency at " + getCurrentTime());
		
		return twoGramsFrequencies;
	}
	
	public static void printTwoGramsFrequencies(Map<String, Integer> twoGramsFrequencies, List<String> tokensList){
		//Sort by highest frequencies first
		sortByFrequency("2-Grams Frequencies", twoGramsFrequencies, tokensList);
	}

	//*** PART D : Palindromes ***
	public static Map<String, Integer> computePalindromeFrequencies(List<String> tokensList){
		Map<String, Integer> palindromeFrequencies = new HashMap<String, Integer>();
		
		palindromeFrequencies = computeOneTokenPalindrome(palindromeFrequencies, tokensList);
		palindromeFrequencies = computeMultipleTokensPalindrome(palindromeFrequencies, tokensList);
		
		return palindromeFrequencies;
	}
	
	public static void printPalindromeFrequencies(Map<String, Integer> palindromeFrequencies){
		//Sort by highest frequencies first
		sortByFrequency("Palindrome Frequencies", palindromeFrequencies, null);	
	}
	
	private static Map<String, Integer> 
		computeOneTokenPalindrome(Map<String, Integer> palindromeFrequencies, 
								  List<String> tokensList){
		
		System.out.println("D01 - Start computing one token palindrome at " + getCurrentTime());
		for(int i = 0; i < tokensList.size(); i++){
			String token 	= tokensList.get(i);
			int tokenLength = token.length();
			for(int j = 0; j < tokenLength/2; j++){
				if(token.charAt(j) != token.charAt(tokenLength-j-1)){
					//Break if not a palindrome
					break;
				}
				if(j == (tokenLength/2) - 1){
					//Add to hashmap if it is a palindrome at the end
					if(palindromeFrequencies.containsKey(token)){
						int currentFrequency = (int) palindromeFrequencies.get(token);
						currentFrequency++;
						palindromeFrequencies.put(token, currentFrequency);
					}else{
						//If there is no token in the hashmap, add new
						palindromeFrequencies.put(token, 1);
					}
				}
			}
		}
		System.out.println("D01 - End computing one token palindrome at " + getCurrentTime());
		
		return palindromeFrequencies;
	}
	
	private static Map<String, Integer>
			computeMultipleTokensPalindrome(Map<String, Integer> palindromeFrequencies, 
											List<String> tokensList){
		
		System.out.println("D02 - Start computing multiple tokens palindrome at " + getCurrentTime());
		for(int i = 0; i < tokensList.size() - 1; i++){
			int tokensListSize		= tokensList.size();
			String originalToken 	= tokensList.get(i);
			String token			= originalToken;
			
			int z;
			if(tokensListSize - i > 30){
				z = 31;
			}else{
				z = tokensListSize - i;
			}
			
			for(int k = 1; k < z; k++){
				originalToken = originalToken + " " + tokensList.get(i + k);
				token += tokensList.get(i + k);
				int tokenLength = token.length();
				
				for(int j = 0; j < tokenLength / 2; j++){
					if(token.charAt(j) != token.charAt(tokenLength - j - 1)){
						//Break if not a palindrome
						break;
					}
					if(j == (tokenLength / 2) - 1){
						//Add to hashmap if it is a palindrome at the end
						if(palindromeFrequencies.containsKey(originalToken)){
							int currentFrequency = (int) palindromeFrequencies.get(originalToken);
							currentFrequency++;
							palindromeFrequencies.put(originalToken, currentFrequency);
						}else{
							//If there is no token in the hashmap, add new
							palindromeFrequencies.put(originalToken, 1);
						}
					}
				}
			}
		}
		System.out.println("D02 - End computing multiple tokens palindrome at " + getCurrentTime());
		
		return palindromeFrequencies;
	}
	
	//*** Supporting methods ***
	@SuppressWarnings("unchecked")
	private static void sortByFrequency(String title, Map<String, Integer> frequenciesMap, List<String> tokensList) { 
		//Convert Map to Array
		System.out.println("S01 - Start converting Map to Array at " + getCurrentTime());
	    Object[] frequenciesArray = frequenciesMap.entrySet().toArray();
	    System.out.println("S01 - End converting Map to Array at " + getCurrentTime());
	    
	    System.out.println("S02 - Start sorting by frequency at " + getCurrentTime());
	    Arrays.sort(frequenciesArray, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
	        	Entry<String, Integer> entry1 = (Map.Entry<String, Integer>) o1;
				Entry<String, Integer> entry2 = (Map.Entry<String, Integer>) o2;
				return entry2.getValue().compareTo(entry1.getValue());
	        }
	    });
	    System.out.println("S02 - End sorting by frequency at " + getCurrentTime());
	    
	    //Print sorted Map
	    String textResult = new String();
	    System.out.println("S03 - Start preparing result string at " + getCurrentTime());
//	    System.out.println("### Result by highest to lowest frequency ###");
	    textResult = "### Result of " + title + " by highest to lowest frequency ###\n\n";
	    
	    if(title.equals("Word Frequencies")){
	    	textResult += "Total number of word: " + tokensList.size() + "\n";
	    	textResult += "Total number of distinct word: " + frequenciesMap.size() + "\n\n";
	    }else if(title.equals("2-Grams Frequencies")){
	    	textResult += "Total number of 2-Grams word: " + (tokensList.size() - 1) + "\n";
	    	textResult += "Total number of distinct 2-Grams word: " + frequenciesMap.size() + "\n\n";
	    }else if(title.equals("Palindrome Frequencies")){
	    	int totalPalindrome = 0;
			for (int i : frequenciesMap.values()) {
	    		totalPalindrome += i;
	    	}
	    	textResult += "Total number of palindrome: " + totalPalindrome + "\n";
	    	textResult += "Total number of distinct palindrome: " + frequenciesMap.size() + "\n\n";
	    }
	    
	    for (Object e : frequenciesArray) {
	        Entry<String, Integer> entry = (Map.Entry<String, Integer>) e;
//			System.out.println(entry.getKey() + " - " + entry.getValue());	
	        textResult += entry.getKey() + " - " + entry.getValue() + "\n";
	    }
	    textResult += "\n----- END OF RESULT -----";
	    System.out.println("S03 - End preparing result string at " + getCurrentTime());
	    
		//Write result to text file
	    System.out.println("S04 - Start writing result file at " + getCurrentTime());
		try{
			File file = new File("result.txt");
			FileUtils.writeStringToFile(file, textResult);
			System.out.println("Result is ready!");
			System.out.println("S04 - End writing result file at " + getCurrentTime());
		}catch (IOException e){
			System.err.println("Caught IOException: " + e.getMessage());
		}	
	}
	
	private static String getCurrentTime(){
		String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
}