package HoneyBee;

import java.awt.Image;
import java.awt.font.ImageGraphicAttribute;
import java.io.*;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import FangTooth.FangTooth;


public class HoneyBee {
	// honeybee is class which returns summary of a webpage
	// requires a webpage link as an input
	// returns a summary returns multiple types of summary of webpage
	// short summary contains a heading and an image 
	// little detail summary contains heading, small detail and image
	// detailed summary contained heading, multi-line details and image
	// works with both URLs and URIs
	
	
	HoneyBee() {
	}
	
	private static Summary makeSummaryFromHtml(String html, URL url) {
		String heading = null;
		String details = null;
		String filePath = null;
		String address = null;
		try {
			Document htmlDocument = Jsoup.parse(html);
			if (htmlDocument == null) {
				throw new IOException("Not valid html document");
			}
			Element head = htmlDocument.head();
			if (head != null) {
				for(Element elem : head.select("meta")) {
					
					if (elem.attr("name").equals("title")
							|| elem.attr("property").equals("og:title")) {
						if (heading == null || heading.equals("")) 
							heading = elem.attr("content");
					}
					
					if (elem.attr("name").equals("description")
							|| elem.attr("property").equals("og:description")) {
						if (details == null || details.equals("")) 
							details = elem.attr("content");
					}
					if (elem.attr("name").equals("image")
							|| elem.attr("property").equals("og:image")) {
						if (filePath == null || filePath.equals("")) 
							filePath = elem.attr("content");
					}
					if (elem.attr("name").equals("url")
							||elem.attr("property").equals("og:url")) {
						if (address == null || address.equals("")) 
							try {
								address = new URL(makeGoodAddress(elem.attr("content")))
										.getHost();
							} catch (Exception e) {
								e.printStackTrace();
							}
					}
				}
				
				if (heading == null || heading.equals("")) {
					if (head.selectFirst("title") != null)
						heading = head.selectFirst("title").text();
				}
			}
			
			if (filePath == null || filePath.equals("")) {
				Element filePathElem = htmlDocument.selectFirst("img");
				if (filePathElem != null) {
					filePath = filePathElem.attr("src");
				}
			}
			if (details == null || details.equals("")) {
				details = "Tap here to open the link.";
			}
			if (address == null || address.equals("")) {
				address = url.getHost();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (heading != null && !heading.equals("")) {
			Summary summary = new Summary(heading,details,filePath, address);
			return summary;
		}
		return null;
	}
	
	public static String makeGoodAddress(String address) {
		if (! address.matches("^\\w+://.*")) {
			address = "http://"+address.toLowerCase();
		}
		return address;
	}
	
	public static Summary getSummary(URL url) {
		FangTooth tooth = new FangTooth();
		if (tooth.hasRedirection(url)) {
			url = tooth.getFinalURL(url);
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("User-Agent", 
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 "
					+ "(KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			int status = conn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = conn.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String html = "";
				String line = "";
				while ((line = reader.readLine()) != null) {
					html += line;
				}
				return makeSummaryFromHtml(html,url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
		return null;
		
	}
	
	
	public static Summary getSummary(String address) {
		address = makeGoodAddress(address);
		try {
			URL url = new URL(address);
			return getSummary(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
