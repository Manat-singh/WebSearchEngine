package project;

import java.util.Scanner;

public class Search {
	
	public static void startCrawling() {
		Scanner sc = new Scanner(System.in);
		Scanner search = new Scanner(System.in);
		boolean show = true;
		while (show) {
			System.out.println("Please enter a website to crawl: ");
			String website = search.next();
			WebCrawler crawler = new WebCrawler(website);
			crawler.crawlLink();
			System.out.println("Do you want to continue crawling?(y/n): ");
			String doSearch = sc.next();
			if (doSearch.equalsIgnoreCase("n")) {
				show = false;
			}
		}
		sc.close();
		search.close();
	}
	
	public static void showMenu() {
		try {
			Scanner sc = new Scanner(System.in);
			boolean show = true;
			while (show) {
				System.out.println("Type crawl to start crawling");
				System.out.println("Type search to start searching a word");
				System.out.println("Type exit to exit");
				System.out.println("---------------------------------------");
	//			System.out.println();
				String option = sc.nextLine();
				switch (option) {
					case "crawl":
						System.out.println("Starting crawling!");
						startCrawling();
						show = false;
						break;
					case "search":
						Autocorrect.searchWords();
						break;
					case "exit":
						System.out.println("Exiting now!");
						System.exit(0);
						break;
					default:
						System.out.println("Wrong input, please enter correct input");
				}
			}
			sc.close();
		} catch (Exception e) {
			
		}
	}
	
	public static void main(String args[]) {
		showMenu();
	}

}
