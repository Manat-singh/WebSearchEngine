package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	public String link = "";
	public ArrayList<String> visitedUrls;
	
	public WebCrawler(String link) {
		this.link = link;
		this.visitedUrls = new ArrayList<String>();
	}
	
	public WebCrawler() {
		this.link = "www.bbc.com";
	}
	
	private Document crawlPage(String link) {
		try {
			System.out.println("link to be parsed: " +link);
			Document doc = Jsoup.connect(link).get();
//			System.out.println(doc.title());
			this.visitedUrls.add(link);
			return doc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return null;
		}
	}
	
	public void crawl(String link, int level) {
		try {
			if (level <=10) {
				System.out.println("-------------------------------");
				Document doc = this.crawlPage(link);
//				System.out.println("is doc null?" + (doc == null));
				if (doc != null) {
					this.saveToFile(doc, doc.title());
					System.out.println("-------------------------------");
					Elements ele = doc.select("a[href");
					for (Element e: ele) {
						String url = e.absUrl("href");
//						System.out.println("subUrl: " +url);
						if (url.contains("mailto:")) {
							url = "";
						}
						if (this.visitedUrls.contains(url) == false && url != "") {
//							System.out.println("level: " +level);
							crawl(url, level+1);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	/*
	 * save the parsed text to file with the given title.
	 * NOTE: while testing, some links have a '|'. That causes issues saving.
	 * So replacing | with space
	 */
	public void saveToFile(Document doc, String title) throws FileNotFoundException {
		String[] titles = title.split("/|\\|");
		String fileTitle = "";
		for (String t: titles) {
//			System.out.println(t);
			fileTitle += " " +t;
		}
		fileTitle = "CrawledPages/" + fileTitle + ".txt";
		fileTitle = fileTitle.replace("?", "");
		System.out.println("title: " +fileTitle);
		PrintWriter out = new PrintWriter(fileTitle);
		out.println(doc.text());
		out.close();
//		File newFile = new File("CrawledPages/" + fileTitle + ".txt");
	}
	
	public void crawlLink() {
		try {
			System.out.println("crawling");
			this.crawl(link, 1);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
//	public static void main(String args[]) {
//		WebCrawler c = new WebCrawler("https://www.cbc.ca");
//		c.crawlLink();
//	}

}
