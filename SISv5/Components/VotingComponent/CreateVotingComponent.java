import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.*;

public class CreateVotingComponent {
	
	// socket for connection to SISServer
	private static Socket universal;
	private static int port = 53217;
	// message writer
	private static MsgEncoder encoder;
	// message reader
	private static MsgDecoder decoder;
	
	// scope of this component
	private static final String SCOPE = "SIS.Scope1";
	// name of this component
	private static final String NAME = "VotingComponent";
	// messages types that can be handled by this component
	private static final List<String> TYPES = new ArrayList<String>(
			Arrays.asList(new String[] { "Setting","Confirm" }));
			//Arrays.asList(new String[] { "Vote","Count","Admin","Confirm" }));

    // shared by all kinds of records that can be generated by this component
	private static KeyValueList record = new KeyValueList();
	
	/*
	 * Main program
	 */
	public static void main(String[] args) {
		while (true) {
			try {
				// try to establish a connection to SISServer
				universal = connect();

				// bind the message reader to inputstream of the socket
				decoder = new MsgDecoder(universal.getInputStream());
				// bind the message writer to outputstream of the socket
				encoder = new MsgEncoder(universal.getOutputStream());

				/*
				 * construct a Connect message to establish the connection
				 */
				KeyValueList conn = new KeyValueList();
				conn.putPair("Scope", SCOPE);
				conn.putPair("MessageType", "Connect");
				conn.putPair("Role", "Basic");
				conn.putPair("Name", NAME);
				encoder.sendMsg(conn);

				initRecord();

				// KeyValueList for inward messages, see KeyValueList for
				// details
				KeyValueList kvList;

				while (true) {
					// attempt to read and decode a message, see MsgDecoder for
					// details
					//System.out.println("Inside inner while");
					
					kvList = decoder.getMsg();

					//System.out.println("Made it past decoder");
					// process that message
					ProcessMsg(kvList);
				}

			} catch (Exception e) {
				// if anything goes wrong, try to re-establish the connection
				try {
					// wait for 1 second to retry
					Thread.sleep(1000);
				} catch (InterruptedException e2) {
				}
				System.out.println("Try to reconnect");
				try {
					universal = connect();
				} catch (IOException e1) {
				}
			}
		}
	}

	/*
	 * used for connect(reconnect) to SISServer
	 */
	static Socket connect() throws IOException {
		Socket socket = new Socket("127.0.0.1", port);
		return socket;
	}
	
	private static void initRecord() {
		record.putPair("Scope", SCOPE);
		record.putPair("MessageType", "Register");
		record.putPair("Sender", NAME);
	}

	/*
	 * process a certain message, execute corresponding actions
	 */
	static void ProcessMsg(KeyValueList kvList) throws IOException {
		
		//System.out.println("Made it inside ProcessMsg...");

		String scope = kvList.getValue("Scope");
		String messageType = kvList.getValue("MessageType");
		
		if(!SCOPE.equals(scope)){
				System.out.println("Unknown scope.");
				return;
		}
		else if(!TYPES.contains(messageType)){
			System.out.println("Unknown messageType: " + messageType);
			return;
		}
		
		String sender = kvList.getValue("Sender");
		String receiver = kvList.getValue("Receiver");
		String purpose = kvList.getValue("Purpose");
	
		System.out.println("Message from " + sender);
		System.out.println("Message for " + receiver);
        System.out.println("Message type: " + messageType);
        System.out.println("Message Purpose: " + purpose);
		
		switch(messageType) {
			case "Confirm":
				System.out.println("Connect to SISServer successful.");
				break;
			case "Setting":
				switch(purpose) {
					case "Vote":
						System.out.println("Received vote.");
						String email = kvList.getValue("Email");
						Integer vote = Integer.parseInt(kvList.getValue("Vote"));
						System.out.println("Email: "+email+"\tVote: "+vote);
						break;
					case "Count":
						System.out.println("Counting votes. (not implemented)");
						break;
					case "Admin":
						System.out.println("Checking password. (not implemented)");
						break;
					case "Kill":
						System.out.println("Checking password. (not implemented)");
						System.exit(0);
						break;
				}
		}
		System.out.println("\n");
	}
}


