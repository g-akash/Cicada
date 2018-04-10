package HoneyBee;

public class HoneyBeeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HoneyBee bee = new HoneyBee();
		Summary summ = bee.getSummary("http://www.imsdb.com/scripts/Star-Wars-The-Phantom-Menace.html");
		if (summ != null) {
			System.out.println(summ.getHeading()+ "\n " +summ.getDetails()+ "\n " +
					summ.getAddress() + "\n "+summ.getFile());
		} else {
			System.out.println("summary is null");
		}
	}
}
