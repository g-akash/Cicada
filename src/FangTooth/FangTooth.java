package FangTooth;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.io.*;
import java.net.*;


public class FangTooth {
	// FangTooth find whether urls are redirected
	// provides an interface which gives the final
	// url which actually contains the content
	
	// the api consists of two things
	// one to check if url has re-direction
	
	// other to get the final link url
	
	public static boolean hasRedirection(URL url) {
		try {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("User-Agent", 
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 "
				+ "(KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		int status = conn.getResponseCode();
		conn.disconnect();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_PERM
					|| status == HttpURLConnection.HTTP_MOVED_TEMP) {
				return true;
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static boolean hasRedirection(String address) {
		try {
			URL url = new URL(address);
			return hasRedirection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static URL getFinalURL(URL url) {
		if (!hasRedirection(url)) return url;
		HashMap<String,Boolean> urlsVisited = new HashMap<String,Boolean>();
		while (true) {
			if (urlsVisited.containsKey(url.getPath())==true) {
				if (hasRedirection(url)) {
					return null;
				} else {
					return url;
				}
			}
			urlsVisited.put(url.getPath(), true);
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("User-Agent", 
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 "
						+ "(KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				int status = conn.getResponseCode();
				if (status != HttpURLConnection.HTTP_OK
						&& (status == HttpURLConnection.HTTP_MOVED_PERM
						|| status == HttpURLConnection.HTTP_MOVED_TEMP)) {
					String newAddress = conn.getHeaderField("Location");
					url = new URL(newAddress);
					conn.disconnect();
				} else {
					conn.disconnect();
					return url;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
	
	public static URL getFinalURL(String address) {
		try {
			URL url = new URL(address);
			return getFinalURL(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
