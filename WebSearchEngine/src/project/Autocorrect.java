package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Autocorrect {
			
	public static void searchWords() {

//		System.out.println("AUTOCORRECT");
		Autocorrect ac = new Autocorrect();

		Hashtable<String, Integer> ht = new Hashtable<String, Integer>();		
		Scanner sc = new Scanner(System.in);
		String s = "yes";
		do {	
		
		System.out.println("Enter search query : ");
		String input = sc.nextLine();
		long file_count = 0;
		int count = 0;
		int word_found = 0; 
		try {
			
			File loc = new File("CrawledPages");

			File[] fileArray = loc.listFiles();

			int a=0;
			
			while(a < fileArray.length) {
				
				count = ac.searchWord(fileArray[a], input); // pattern searching

				ht.put(fileArray[a].getName(), count); 
				
				if (count != 0) {
					word_found++; // counts number of files in which frequency of word != 0 
				}

				file_count++; // count total number of files
				a++;

			}

			System.out.println("\nTotal No. of Files for input " + input + " is : " + word_found);
			
			if (word_found == 0) { // no match found

				System.out.println("\nSearching Suggestions...");
				System.out.println("\nPlease wait...");
				ac.suggestions(input);

				Dictionary.creatDictionary();
				
				

			}
			
			Sorting.rankFiles(ht, word_found); // word_found == occurences of the word
			System.out.println("\n\nDo you want to continue(yes/no)??");
			s = sc.nextLine();	
			if(s.equals("no")) {
				sc.close();
				System.out.println("Exited");
			}
			
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		
		}while(s.equals("yes"));
	}

	// Find Positions and Occurrences of the words
	public int searchWord(File filePath, String s1) throws IOException {
		
		String my_data = "";
		
		try {
			BufferedReader my_Object = new BufferedReader(new FileReader(filePath));
			String line = null;

			while ((line = my_Object.readLine()) != null) {

				my_data = my_data + line;

			}
			my_Object.close();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		char txt[] = my_data.toCharArray();
		char pat[] = s1.toCharArray();
		 
		int result = BoyerMoore.search(txt, pat);

		if (result != 0) {
			System.out.println("\nFile that contains above query in list = " + filePath.getName());
			System.out.println("--------------------------------------\n");
		}
		
		return result;
	}
	
//	public static void spellCheck(String pattern) throws IOException{
//		//Scanner sc= new Scanner(System.in);
////		System.out.println("Enter Word to search:");
////		String sword=sc.nextLine();
//	
//		String filename="dictionary.txt";
//		File file = new File(filename);
//		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//			ArrayList<String> dictWords = new ArrayList<String>();
//			String str=null;
//			while((str= reader.readLine())!=null)
//			{
//				dictWords.add(str);
//			}
//			
//			int ed,minED=10, secMinED=10;
//			int sugWordOne=0;
//			int sugWordTwo=0;
//			for(int i = 0; i<dictWords.size();i++){
//				String dw=dictWords.get(i);
//				ed = EditDistance.editDistance(dw, pattern);
////				System.out.println(dw+"  "+ed);
//				if(ed<secMinED) {
//					if(ed<minED) {
//						minED=ed;
//						sugWordOne=i;
//					}
//					else {
//						secMinED=ed;
//						sugWordTwo=i;
//					}
//				}
//			}
//			System.out.println("Did you mean: "+dictWords.get(sugWordOne)+" or "+dictWords.get(sugWordTwo)+"?");
//		}	
//	}
	
	private int wordSearch(File file, String input) throws IOException{
		// TODO Auto-generated method stub
		
		String data = "";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;

			while ((line = br.readLine()) != null) {

				data = data + line;

			}
			br.close();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		char txt[] = data.toCharArray();
		char pat[] = input.toCharArray();
		 
		int result = BoyerMoore.search(txt, pat);

		if (result != 0) {
			System.out.println("\nFile that contains above query is = " + file.getName());
			System.out.println("\n");
		}
		
		return result;
	}
	
	private void checkspelling(String S) throws IOException{
		// TODO Auto-generated method stub
//		System.out.println("heree");
		String filename="dictionary.txt";
		File file = new File(filename);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			ArrayList<String> dictWords = new ArrayList<String>();
			String str=null;
			while((str= reader.readLine())!=null)
			{
				dictWords.add(str);
			}
			
			int ed,ed1=10, ed2=10;
			int sugword1=0;
			int sugword2=0;
			for(int i = 0; i<dictWords.size();i++){
				String dw=dictWords.get(i);
				ed = EditDistance.editDistance(dw, S);
//				System.out.println(dw+"  "+ed);
				if(ed<ed2) {
					if(ed<ed1) {
						ed1=ed;
						sugword1=i;
					}
					else {
						ed2=ed;
						sugword2=i;
					}
				}
			}
			System.out.println("Did you mean: "+dictWords.get(sugword1)+" or "+dictWords.get(sugword2)+"?");
		}	
		
	}
	
	//Using Regular Expressions for pattern matching
	public void suggestions(String pattern) {
		try {
			checkspelling(pattern);
		}catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

	
}
