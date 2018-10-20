import javax.swing.JFrame;
public class ServerTest {

	public static void main(String[] args) {
		Server mim = new Server(6789);
		mim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mim.startRunning();

	}

}
