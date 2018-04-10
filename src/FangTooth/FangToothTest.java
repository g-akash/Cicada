package FangTooth;

import java.net.URL;

public class FangToothTest {

	public static void text(String[] args) {
		// TODO Auto-generated method stub
		String address = "http://www.google.com";
		URL url = null;
		try {
			url = new URL(address);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		FangTooth tooth = new FangTooth();
		System.out.println(tooth.getFinalURL(url));
	}

}
