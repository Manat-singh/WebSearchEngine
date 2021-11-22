package project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class InvertedIndexing {
	public Map<Integer,String> Values = new HashMap<Integer,String>();
	public HashMap<String, HashSet<Integer>> Key = new HashMap<String, HashSet<Integer>>();
	
	//Insert data from file using Inverted Indexing 
	public InvertedIndexing()
	{
		//Values = new HashMap<Integer,String>();
		//Key = new HashMap<String, HashSet<Integer>>();
		File Folder = new File("CrawledPages");
  		File fileArray[] = Folder.listFiles();
  		
  		for(int i =0; i<fileArray.length;i++) 
  		{  			
  			String[] words = fetchWords(fileArray[i]);
  			Values.put(i,fileArray[i].getName());
  			//System.out.println("files" + Values);
  			create(words,i,fileArray[i].getName());
  	  		}
	}
	
	//Insert values 
	public void create(String[] words , int i, String fileName) {
		for(String word:words) {
			word = word.toLowerCase();
            if (!Key.containsKey(word))
                Key.put(word, new HashSet<Integer>());
            Key.get(word).add(i);
            //System.out.println(Key.get("W3C") + "key");
		}
	}
		
	
	
	//Returns words from selected file
	public static String[] fetchWords(File f) 
	{
		In in = new In(f);
		String text = in.readAll();
		text = text.replaceAll("[^a-zA-Z0-9\\s]", ""); 
		String[] word = text.split(" ");		
		
		
		return word;
		
	}
	
	//Return the search value occurrences
	public ArrayList<String> searchKey(String phrase){
    	ArrayList<String> fNames;
    	try {
    		phrase = phrase.toLowerCase();
    		fNames = new ArrayList<String>();
	        String[] words = phrase.split("\\W+");
	        String hashKey = words[0].toLowerCase();
	        if(Key.get(hashKey) == null) {
	        	 System.out.println("Not found!!!");
	        	return null;
	        }
	        HashSet<Integer> res = new HashSet<Integer>(Key.get(hashKey));	        
	        for(String word: words){
	            res.retainAll(Key.get(word));
	        }
	
	        if(res.size()==0) {
	            System.out.println("Not found!!!");
	            return null;
	        }
	        for(int num : res){
	        	fNames.add(Values.get(num));
	        }
    	}catch(Exception e) {
    		 System.out.println("Not found!!!");
    		 System.out.println("Exception Found:" + e.getMessage());
    		 return null;
    	}  
    return fNames;
    }
	
}
