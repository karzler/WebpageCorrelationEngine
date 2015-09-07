import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import javax.xml.ws.ProtocolException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Parser {
	    // Site URL
		private URL url;	
		
		// Set containing words to ignore
		private HashSet<String> stopwords;
		
		/*
		 * Wrapper Method to parse the given page
		 */
		public LinkedList<String> parse(PageDoc page){
			this.url = page.getURL();
			stopwords = new HashSet<String>();
			return initParsing(page);
		}
		
	    /*
		 *  initParsing method initiates parsing of stop-words and keywords
		 */
		private LinkedList<String> initParsing(PageDoc page) {
			parseStopwords();
			return parseKeywords(page);
		}
		
		/*
		 * parseStopWords produces stopwords that help in "extracting" keywords 
		 */
		private void parseStopwords() {
			Scanner scan = null;
			File file = new File("stop-words_english_1_en.txt");
			//scan = new Scanner(getClass().getResourceAsStream("stop-words_english_1_en.txt"));
			try {
				scan = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (scan.hasNext())
				stopwords.add(scan.next());
		}
		
		/*
		 * Returns true if a string is a stopword
		 */
		private boolean stopword(String word) {
			return word.endsWith(",") || stopwords.contains(word); 
		}
		
		/*
		 * Returns true if a string begins with an upper case character
		 */
		private boolean isUpper(String s)
		{
		    for (char c : s.toCharArray()) {
		        if(!Character.isUpperCase(c))
		            return false;
		    }
		    return true;
		}
		
		/*
		 * Returns a string with all unnecessary symbols trimmed
		 */
		private String trimSymbols(String inputText) {
			
			// Trim extra whitespace
			String text = inputText.trim();
			
			// Remove plurals
			text = text.replace("'s", "");
			
			// Remove other symbols
			text = text.replace("!", "");
			text = text.replace("?", "");
			text = text.replace("\"", "");
			text = text.replace(",", "");
			text = text.replace(":", "");
			text = text.replace("(", "");
			text = text.replace(")", "");
			text = text.replace("|", "");
			
			// Remove apostrophes
			if (text.startsWith("'") || text.endsWith("'"))
				text = text.replace("'", "");
			
			// Remove periods
			if (text.endsWith(".") && !isUpper(text.substring(text.length()-2, text.length()-1)))
				text = text.replace(".", "");
			
			return text;
		}
		
		/*
		 * Connects to the site URL and parses the entire content of the page. 
		 * Constructs a TreeNode with every keyword phrase found.
		 */
		private LinkedList<String> parseKeywords(PageDoc page) {
			LinkedList<String> keywords = new LinkedList<String>();
			// Connect to site
			org.jsoup.nodes.Document doc = null;
			doc = page.getDocument();
			
			if (doc == null)
			{
				System.out.println ("Could not retrieve Page :(");
				System.exit (1);
			}
			// Scanner for parsing each individual string in document
			Scanner sc = new Scanner(doc.text());
			
			// Next string in document text
			String word = "";
			
			// Longest keyword phrase found before stopword or punctuation
			String topic = "";
			
			// Temporary string to hold next value of 'word'
			String temp = ""; 
			
			// True if the last keyword phrase ended with punctuation
			boolean endKeyword = false;
			
			// Iterate through entire document text
			while (sc.hasNext()) {

				// Stores next word in document
				word = sc.next().toLowerCase();
				
				// True if the word ends with punctuation
				//boolean endPunc = (word.endsWith(".") || word.endsWith(",") || word.endsWith("!") || word.endsWith("?"));
				
				// True if the word is a stopword
				boolean stopword = stopword(trimSymbols(word));
				
				/*
				 *  If the next word is the end of a topic phrase then the topic is broken down into individual
				 *  keywords and added to the WordTree
				 */
				if (!stopword) {
					keywords.add(trimSymbols(word));
				}
			
			}
			
			return keywords;
		}
		
}
