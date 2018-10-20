import javax.swing.JFrame;
public class Client2Test {

	public static void main(String[] args) {
		Client sally ;
		sally = new Client("192.168.0.101");
        sally.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sally.startRunning();
	}

}
