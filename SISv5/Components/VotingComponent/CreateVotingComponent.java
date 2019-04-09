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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CreateVotingComponent {

	// socket for connection to SISServer
	private static Socket universal;
	private static int port = 53217;
	// message writer
	private static MsgEncoder encoder;
	// message reader
	private static MsgDecoder decoder;

	private static final String HASH = "0d94d92e3dc096f64213a5b34fa9d098";

	// scope of this component
	private static final String SCOPE = "SIS.Scope1";
	// name of this component
	private static final String NAME = "VotingComponent";

	// messages types that can be handled by this component
	private static final List<String> TYPES = new ArrayList<String>(
			Arrays.asList(new String[]{"Setting", "Confirm"}));
	//Arrays.asList(new String[] { "Vote","Count","Admin","Confirm" }));

	// shared by all kinds of records that can be generated by this component
	private static KeyValueList record = new KeyValueList();
	// shared by all kinds of alerts that can be generated by this component
	private static KeyValueList alert = new KeyValueList();

	//ID, count
	private static HashMap<Integer, Integer> tallyTable = null;

	//email, in table?
	private static HashMap<String, Boolean> voterTable = new HashMap<String, Boolean>();

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

		alert.putPair("Scope", SCOPE);
		alert.putPair("MessageType", "Alert");
		alert.putPair("Sender", NAME);
		alert.putPair("Purpose", "VoteAlert");
	}

	/*
	 * process a certain message, execute corresponding actions
	 */
	static void ProcessMsg(KeyValueList kvList) throws IOException {

		//System.out.println("Made it inside ProcessMsg...");

		String scope = kvList.getValue("Scope");
		String messageType = kvList.getValue("MessageType");

		if (!SCOPE.equals(scope)) {
			System.out.println("Unknown scope.");
			return;
		} else if (!TYPES.contains(messageType)) {
			System.out.println("Unknown messageType: " + messageType);
			return;
		}

		String sender = kvList.getValue("Sender");
		String receiver = kvList.getValue("Receiver");
		String purpose = kvList.getValue("Purpose");
		String msgID = kvList.getValue("msgID");

		System.out.println("Message from " + sender);
		System.out.println("Message for " + receiver);
		System.out.println("Message type: " + messageType);
		System.out.println("Message Purpose: " + purpose);
		System.out.println("Message ID: " + msgID);

		switch (messageType) {
			case "Confirm":
				System.out.println("Connect to SISServer successful.");
				break;

			case "Setting":

				switch (purpose) {
					case "Vote":
						if(msgID.equals("701")) {
							String email = kvList.getValue("Email");
							Integer voteID = new Integer(Integer.parseInt(kvList.getValue("VoteID")));
							KeyValueList out = new KeyValueList();

							//check tallyTable init
							if (tallyTable != null) {
								System.out.println("Casting vote.");
								if (!voterTable.containsKey(email)) {
									//System.out.println("tallyTable keys: " + tallyTable.keySet() + " vote id: " + voteID);
									if (tallyTable.containsKey(voteID)) {
										System.out.println("Email: " + email + "\tVoteID: " + voteID);
										voterTable.put(email, true);
										tallyTable.put(voteID, tallyTable.get(voteID) + 1);
										out.putPair("MsgID", "711");
										out.putPair("Status","3");
										out.putPair("Receiver", "GUI");
										out.putPair("MessageType", "Reading");
										out.putPair("Scope", "SIS.Scope1");
										out.putPair("Sender", NAME);
										encoder.sendMsg(out);
									} else {
										out.putPair("MsgID", "711");
										out.putPair("Status","2");
										out.putPair("Receiver", "GUI");
										out.putPair("MessageType", "Reading");
										out.putPair("Scope", "SIS.Scope1");
										out.putPair("Sender", NAME);
										encoder.sendMsg(out);
										System.out.println(voteID + " not in table.");
									}
								} else {
									out.putPair("MsgID", "711");
									out.putPair("Status","1");
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
									encoder.sendMsg(out);
									System.out.println(email + " already casted vote.");
								}
							} else {
								System.out.println("Tally table not initialized.");
								out.putPair("MsgID", "711");
								out.putPair("Status","2");
								out.putPair("Receiver", "GUI");
								out.putPair("MessageType", "Reading");
								out.putPair("Scope", "SIS.Scope1");
								out.putPair("Sender", NAME);
								encoder.sendMsg(out);
							}
						}
						break;

					case "Admin":
						System.out.println("Checking password.");
						KeyValueList out = new KeyValueList();

						boolean passwordAccepted = true;

						// For any admin functionality, password must be valid, if not break
						String password = kvList.getValue("Password");

						if (password.equals(HASH)) {
							System.out.println("Password Accepted.");
						} else {
							System.out.println("Password Denied.");
							passwordAccepted=false;
						}

						//password good
						switch (msgID) {
							case "702": //REQUEST REPORT
								System.out.println("Request Report");
								out.putPair("MsgID", "712");

								// parse N from the message and error check
								int N;
								try {
									N = Integer.parseInt(kvList.getValue("N"));
								} catch (Exception E) {
									N = 1;
								}
								if (tallyTable == null) {
									System.out.println("Tally table not initialized.");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Status", "Error");
									break;
								}
								if(tallyTable.size() == 0) {
									System.out.println("No votes cast.");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Status", "Error");
									break;
								}
								else if(N < 0 || N >= tallyTable.size()) {
									System.out.println("Invalid size for 'N'.");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Status", "Success");
									break;
								}

								if(passwordAccepted) {
									// get the N max vote numbers
									Collection<Integer> votes = new ArrayList<Integer>();
									Set<Integer> set = new HashSet<Integer>(tallyTable.values());
									votes.addAll(set);
									Collection<Integer> maxes = new ArrayList<Integer>();
									int max;

									for (int i = N; i > 0; i--) {
										max = Collections.max(votes);
										votes.remove(max);
										maxes.add(max);
									}

									// Print the poster IDs and poster Counts of the top N
									StringBuilder rankedReportSB = new StringBuilder();
									boolean first = true;
									for (Integer m : maxes) {
										for (Map.Entry<Integer, Integer> entry : tallyTable.entrySet()) {
											if (entry.getValue().equals(m)) {
												if (!first) {
													rankedReportSB.append(';');
												}
												rankedReportSB.append(entry.getKey());
												rankedReportSB.append(',');
												rankedReportSB.append(entry.getValue());
												first = false;
											}
										}
									}
									String rankedReport = rankedReportSB.toString();
									System.out.println("RankedReport: " + rankedReport);
									out.putPair("RankedReport", rankedReport);
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
								}
								else {
									out.putPair("RankedReport", "null");
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
								}

								encoder.sendMsg(out);

								break;

							case "703": //INITIALIZE TALLY TABLE
								System.out.println("Initialize Tally Table");

                if(passwordAccepted) {
                  try {
                    String[] candidates = kvList.getValue("CandidateList").split(";");
                    tallyTable = new HashMap<Integer, Integer>();
                    for (String c : candidates) {
                      tallyTable.put(new Integer(Integer.parseInt(c)), 0);
                    }
                    //System.out.println("Tally table success " + tallyTable.toString());
                    out.putPair("MsgID", "26");
                    out.putPair("AckMsgID", "703");
                    out.putPair("YesNo", "Yes");
                    out.putPair("Name", NAME);
										out.putPair("Receiver", "GUI");
										out.putPair("MessageType", "Reading");
										out.putPair("Scope", "SIS.Scope1");
										out.putPair("Sender", NAME);
                  }
                  catch(Exception E) {
                    //tallyTable = new HashMap<Integer, Integer>();
                    System.out.println("Tally table not initialized - error");
                    out.putPair("MsgID", "26");
                    out.putPair("AckMsgID", "703");
                    out.putPair("YesNo", "No");
                    out.putPair("Name", NAME);
										out.putPair("Receiver", "GUI");
										out.putPair("MessageType", "Reading");
										out.putPair("Scope", "SIS.Scope1");
										out.putPair("Sender", NAME);
                  }
                } else {
                  System.out.println("Tally table not initialized - incorrect password");
                  out.putPair("MsgID", "26");
                  out.putPair("AckMsgID", "703");
                  out.putPair("YesNo", "No");
                  out.putPair("Name", NAME);
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
                }
								encoder.sendMsg(out);
								break;

							// case "704": // admin terminate voting
							// 	try{
							// 		showResults(tallyTable.size());
							// 		System.out.println("Admin Terminating Voting");
							//
							// 		// Reset Voter table and show results of all voting
							// 		voterTable = new HashMap<String, Boolean>();
							// 		tallyTable = null;
							// 		break;
							// 	} catch(Exception e){
							// 		System.out.println("error while terminating");
							// 		break;
							// 	}

							case "22":	// KILL
								if(passwordAccepted) {
									voterTable = new HashMap<String, Boolean>();
									tallyTable = null;
									System.out.println("killing...");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Status", "Success");
									encoder.sendMsg(out);
									System.exit(0);
									break;
								} else {
									System.out.println("Kill error - password not accepted.");
									out.putPair("Scope", "SIS.Scope1");
									out.putPair("Sender", NAME);
									out.putPair("Receiver", "GUI");
									out.putPair("MessageType", "Reading");
									out.putPair("Status", "Error");
								}
						} // end switch msgID
						break;
				} // end switch purpose
				break;
		} // end switch message type

		System.out.println("\n");
	}


	private static void showResults(int N){
		// get the N max vote numbers
		Collection<Integer> votes = new ArrayList<Integer>();
		Set<Integer> set = new HashSet<Integer>(tallyTable.values());
		votes.addAll(set);
		Collection<Integer> maxes = new ArrayList<Integer>();
		int max;

		for (int i = N; i > 0; i--) {
			max = Collections.max(votes);
			votes.remove(max);
			maxes.add(max);
		}

		// Print the poster IDs and poster Counts of the top N
		StringBuilder rankedReportSB = new StringBuilder();
		boolean first = true;
		for(Integer m : maxes) {
			for (Map.Entry<Integer, Integer> entry : tallyTable.entrySet()) {
				if(entry.getValue().equals(m)) {
					if(!first) {
						rankedReportSB.append(';');
					}
					rankedReportSB.append(entry.getKey());
					rankedReportSB.append(',');
					rankedReportSB.append(entry.getValue());
					first = false;
				}
			}
		}
		String rankedReport = rankedReportSB.toString();
		System.out.println("RankedReport: " + rankedReport);
		// if (tallyTable != null && N > 0 && N <= tallyTable.size())
		// {
		// 	Collection<Integer> votes = new ArrayList<Integer>();
		// 	Set<Integer> set = new HashSet<Integer>(tallyTable.values());
		// 	votes.addAll(set);
		// 	Collection<Integer> maxes = new ArrayList<Integer>();
		// 	int max;

		// 	for(int i=N; i>0; i--) {
		// 		max = Collections.max(votes);
		// 		votes.remove(max);
		// 		maxes.add(max);
		// 	}

		// 	for(Map.Entry<Integer, Integer> entry : tallyTable.entrySet()) {
		// 		if(maxes.contains(entry.getValue())) {
		// 			System.out.println(entry.getKey() + " " + entry.getValue());
		// 		}
		// 	}
		// }
	}


} // end CreateVotingComponent Class
