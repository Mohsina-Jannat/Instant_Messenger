import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class Client2 extends JFrame{

	private JTextField userText;
	private  JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public Client2(String host) {
		super("Client Here!");
		serverIP = host;
		userText = new JTextField();
		//userText.setEditable(false);
		userText.addActionListener(
				new ActionListener() {
public void actionPerformed(ActionEvent event) {
						
						sendMessage(event.getActionCommand());
						userText.setText("");
}
				}
				
				
				);
		   add(userText,BorderLayout.NORTH);
		   chatWindow = new JTextArea();
		   add(new JScrollPane(chatWindow),BorderLayout.CENTER);
		   setSize(400,250);
		   setVisible(true);
	   
}
				
	
	// connect to server
	
	public void startRunning() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofException) {
			showMessage("\n Client terminated connection");
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}finally {
			closeCrap();
			
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		showMessage("Attempting connection....\n");
		connection = new Socket(InetAddress.getByName(serverIP),6789);
		showMessage("Connected to: " + connection.getInetAddress().getHostName());
	}
	
	//set up streams to send and receive messages
	private void setupStreams() throws IOException{
		
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Your streams are ready to go! \n");
		
	}
	
	//while chatting with server
	
	private void whileChatting() throws IOException{
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
			    showMessage("\n" + message);
			}catch(ClassNotFoundException classnotfoundException) {
				showMessage("\n I don't know that object type");
			}
		}while(!message.equals("SERVER - END"));
		
	}
	
	//close the streams and sockets
	private void closeCrap() {
		showMessage("\n closing chatting down...");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
		
		
	}

	//send messages to server
	private void sendMessage(String message) {
		try {
			output.writeObject("CLIENT - "+ message);
			output.flush();
			showMessage("\n CLIENT - " + message);
		}catch(IOException ioException) {
			chatWindow.append("\n Something went wrong while sending msg ");
		}
	}
	
	//change/update chatWindow
	private void showMessage(final String m) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						chatWindow.append(m);
					}
				}
				
				);
	}
	
	//gives user permission to type msg into the text box
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						userText.setEditable(tof);
					}
				}
				
				);
	}
 }
