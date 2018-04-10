package HoneyBee;

import java.net.URL;

public class Summary {
	
	// class used to store the result 
	// of the webpage
	private String heading = null;
	private String details = null;
	private String filePath = null;
	private String address = null;
	
	
	
	Summary(String heading, String details, String filePath, String address) {
		this.heading = heading;
		this.details = details;
		if (filePath!=null && filePath.startsWith("/") && address != null) {
			filePath=HoneyBee.makeGoodAddress(address+filePath);
		}
		this.filePath = filePath;
		this.address=address;
	}
	
	public String getHeading() {
		return heading;
	}
	
	public String getDetails() {
		return details;
	}
	
	public String getFile() {
		return filePath;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public void setFile(String filePath) {
		this.filePath = filePath;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

}
