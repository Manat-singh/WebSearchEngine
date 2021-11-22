/**
 * 
 */
package project;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Priyanka-PC
 *
 */
public class search1 {
	
		//Search entered value and occurrences in the file using inverted indexing
		public static void searchValue(String key,int occurrence) {
			InvertedIndexing data = new InvertedIndexing();
			String keyword = key;
			//System.out.println(keyword);
			keyword = keyword.toLowerCase();
			ArrayList<String> sValue = data.searchKey(keyword);
				
			if(sValue==null) {			
			System.out.println("Entered values does not found");
			}
			else {		
			Map<String,Integer> sortedMap = sortByRank(sValue, keyword);
			print(sortedMap,occurrence);
			}
		}
	
		//Prints given entered value along with frequency of occurrence.
		public static void print(Map<String,Integer> result, int occurrences) {
			Iterator<Entry<String, Integer>> iterator = (result).entrySet().iterator();  
			int i = 0;
			while(iterator.hasNext() && occurrences>i++)   
			{  			
			Map.Entry<String, Integer> me = (Map.Entry<String, Integer>)iterator.next();
			String fileName = me.getKey().toString();			
			File f = new File("CrawledPages\\"+fileName);
			In in = new In(f);
			String url = in.readLine();			
			System.out.println("-----------------------------------------");
			System.out.println(fileName.substring(0,fileName.length()-4)+"\t\tOccurences : "+me.getValue());
			System.out.println(url);  
			}
		}
	
		//Sorts search output based on occurrence rank.
		public static Map<String,Integer> sortByRank(ArrayList<String> as, String phrase) {
					HashMap<String,Integer> wordCount = new HashMap<String,Integer>();
					
					for(String fileName : as) {
						String[] words = InvertedIndexing.fetchWords(new File("CrawledPages\\"+fileName));
						for(String word:words) {
							if(word.toLowerCase().equals(phrase.split("\\W+")[0])) {
								if(wordCount.containsKey(fileName)) {
									wordCount.put(fileName, wordCount.get(fileName)+1);					
								}
								else {
									wordCount.put(fileName, 1);
								}
							}			
						}
					}
					Map<String,Integer> sortedMap = sortValue(wordCount);
					return sortedMap;
				}
				
				//Sorts values of given HashMap in descending order
				@SuppressWarnings({ "unchecked", "rawtypes" })
				private static HashMap<String,Integer> sortValue(HashMap<String,Integer> map){		
					List list = new LinkedList(map.entrySet());  
					//Custom Comparator  
					Collections.sort(list, new Comparator(){public int compare(Object o1, Object o2){return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());}});  
					//copying the sorted list in HashMap to preserve the iteration order  
					HashMap<String,Integer> sortedHashMap = new LinkedHashMap<String,Integer>();  
					for (Iterator it = list.iterator(); it.hasNext();)   
					{  
					 Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) it.next();  
					sortedHashMap.put(entry.getKey(), entry.getValue());  
					}   
					return sortedHashMap;  
				}


}
