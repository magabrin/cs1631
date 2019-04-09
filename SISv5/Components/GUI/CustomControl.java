

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.PasswordField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomControl extends VBox {

	Socket universal;
	static int port = 53217;
	MsgEncoder encoder;

	@FXML
  private TextArea output;
	@FXML
  private Label title;
  @FXML
  private PasswordField password1;
  @FXML
  private PasswordField password2;
  @FXML
  private PasswordField password3;
  @FXML
  private TextField candidateList;
  @FXML
  private TextField email;
  @FXML
  private TextField candidate;
  @FXML
  private TextField winners;
  @FXML
  private Button initButton;
  @FXML
  private Button castButton;
  @FXML
  private Button requestButton;
  @FXML
  private Button killButton;

	public CustomControl() {
		// TODO Auto-generated constructor stub
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"VotingForm.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private StringProperty emailProperty() {
    return email.textProperty();
  }

  private String getEmail() {
    return emailProperty().get();
  }

  private StringProperty candidateProperty() {
    return candidate.textProperty();
  }

  private String getCandidate() {
    return candidateProperty().get();
  }

  private StringProperty winnersProperty() {
    return winners.textProperty();
  }

  private String getWinners() {
    return winnersProperty().get();
  }

  private void clearEmail() {
    emailProperty().set("");
  }

  private void clearCandidate() {
    candidateProperty().set("");
  }

  private void clearWinners() {
    winnersProperty().set("");
  }

  private void clearPassword1() {
    password1Property().set("");
  }

  private StringProperty password1Property() {
    return password1.textProperty();
  }

  private String getPassword1() {
    return HashBrowns(password1Property().get());
  }

	private StringProperty password2Property() {
		return password2.textProperty();
	}

	private String getPassword2() {
		return HashBrowns(password2Property().get());
	}

	private void clearPassword2() {
		password2Property().set("");
	}

	private StringProperty password3Property() {
		return password3.textProperty();
	}

	private String getPassword3() {
		return HashBrowns(password3Property().get());
	}

	private void clearPassword3() {
		password3Property().set("");
	}


  private StringProperty candidateListProperty() {
    return candidateList.textProperty();
  }

  private String getCandidateList() {
    return candidateListProperty().get();
  }

  private void clearCandidateList() {
    candidateListProperty().set("");
  }

	private StringProperty outputProperty() {
		return output.textProperty();
	}

	private String getOutput() {
		return outputProperty().get();
	}

	private void clearOutput() {
		outputProperty().set("");
	}

	public void setOutput(String input) {
		outputProperty().set(input);
	}


  public void init(MouseEvent event) {

    try {

			if (universal == null) {
				universal = new Socket("127.0.0.1", port);
			}
			if (encoder == null) {
				encoder = new MsgEncoder(universal.getOutputStream());
			}

			KeyValueList init_kvl = new KeyValueList();
			init_kvl.putPair("Scope", CreateGUI.SCOPE);
		  init_kvl.putPair("MessageType", "Setting");
			init_kvl.putPair("Sender", CreateGUI.NAME);
			init_kvl.putPair("Receiver", "VotingComponent");
			init_kvl.putPair("Purpose", "Admin");
      init_kvl.putPair("Password", getPassword1());
			init_kvl.putPair("CandidateList", getCandidateList());
			init_kvl.putPair("msgID", "703");

			encoder.sendMsg(init_kvl);
			clearInit();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearInit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearInit();
		}

	}

	public void clearInit() {
		clearPassword1();
		clearCandidateList();
	}

	public void cast(MouseEvent event) {

    try {

			if (universal == null) {
				universal = new Socket("127.0.0.1", port);
			}
			if (encoder == null) {
				encoder = new MsgEncoder(universal.getOutputStream());
			}

			KeyValueList init_kvl = new KeyValueList();
			init_kvl.putPair("Scope", CreateGUI.SCOPE);
		  init_kvl.putPair("MessageType", "Setting");
			init_kvl.putPair("Sender", CreateGUI.NAME);
			init_kvl.putPair("Receiver", "VotingComponent");
			init_kvl.putPair("Purpose", "Vote");
			init_kvl.putPair("Email", getEmail());
			init_kvl.putPair("VoteID", getCandidate());
      init_kvl.putPair("msgID", "701");

			encoder.sendMsg(init_kvl);
			clearCast();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearCast();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearCast();
		}

	}

	public void clearCast() {
		clearCandidate();
		clearEmail();
	}

	public void request(MouseEvent event) {

		try {

			if (universal == null) {
				universal = new Socket("127.0.0.1", port);
			}
			if (encoder == null) {
				encoder = new MsgEncoder(universal.getOutputStream());
			}

			KeyValueList init_kvl = new KeyValueList();
			init_kvl.putPair("Scope", CreateGUI.SCOPE);
			init_kvl.putPair("MessageType", "Setting");
			init_kvl.putPair("Sender", CreateGUI.NAME);
			init_kvl.putPair("Receiver", "VotingComponent");
			init_kvl.putPair("Purpose", "Admin");
			init_kvl.putPair("Password", getPassword2());
			init_kvl.putPair("N", getWinners());
			init_kvl.putPair("msgID", "702");

			encoder.sendMsg(init_kvl);
			clearRequest();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearRequest();
		}

	}

	public void clearRequest() {
		clearPassword2();
		clearWinners();
	}

	public void kill(MouseEvent event) {

		title.setTextFill(Color.web("#ff0000"));
		setOutput("Bye!");

		try {

			if (universal == null) {
				universal = new Socket("127.0.0.1", port);
			}
			if (encoder == null) {
				encoder = new MsgEncoder(universal.getOutputStream());
			}

			KeyValueList init_kvl = new KeyValueList();
			init_kvl.putPair("Scope", CreateGUI.SCOPE);
			init_kvl.putPair("MessageType", "Setting");
			init_kvl.putPair("Sender", CreateGUI.NAME);
			init_kvl.putPair("Receiver", "VotingComponent");
			init_kvl.putPair("Purpose", "Admin");
			init_kvl.putPair("Password", getPassword3());
			init_kvl.putPair("Name", "Kill");
			init_kvl.putPair("msgID", "22");

			encoder.sendMsg(init_kvl);
			clearRequest();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clearRequest();
		}

	}

	public void clearKill() {
		clearPassword3();
	}

	private String HashBrowns(String passwordToHash) {
		String generatedPassword = null;
		try {
				// Create MessageDigest instance for MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				//Add password bytes to digest
				md.update(passwordToHash.getBytes());
				//Get the hash's bytes
				byte[] bytes = md.digest();
				//This bytes[] has bytes in decimal format;
				//Convert it to hexadecimal format
				StringBuilder sb = new StringBuilder();
				for(int i=0; i< bytes.length ;i++)
				{
						sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				//Get complete hashed password in hex format
				generatedPassword = sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
				e.printStackTrace();
		}
		return generatedPassword;
	}

	// public void setAlert() {
	// 	title.setTextFill(Color.RED);
	// }
  //
	// public void setTitle(String t) {
	// 	titleProperty().set(t);
	// }
  //
	// public String getTitle() {
	// 	return titleProperty().get();
	// }
  //
	// public StringProperty titleProperty() {
	// 	return title.textProperty();
	// }
  //
	// public String getConsole() {
	// 	return consoleProperty().get();
	// }
  //
	// public void setConsole(String value) {
	// 	consoleProperty().set(value);
  //
	// }
  //
	// public void setActive() {
	// 	active.setDisable(false);
	// 	active.setSelected(true);
	// 	active.setText("Active");
	// 	active.setTextFill(Color.GREEN);
	// }
  //
	// public void setEnable() {
	// 	active.setDisable(false);
	// }
  //
	// public StringProperty consoleProperty() {
	// 	return console.textProperty();
	// }
  //
	// public String getMax() {
	// 	return maxProperty().get();
	// }
  //
	// public void setMax(String value) {
	// 	maxProperty().set(value);
	// }
  //
	// public StringProperty maxProperty() {
	// 	return max.textProperty();
	// }
  //
	// public String getMin() {
	// 	return minProperty().get();
	// }
  //
	// public void setMin(String value) {
	// 	minProperty().set(value);
	// }
  //
	// public StringProperty minProperty() {
	// 	return min.textProperty();
	// }
  //
	// public String getRefreshRate() {
	// 	return refreshRateProperty().get();
	// }
  //
	// public void setRefreshRate(String value) {
	// 	refreshRateProperty().set(value);
	// }
  //
	// public StringProperty refreshRateProperty() {
	// 	return refreshRate.textProperty();
	// }
  //
	// public void setStartDate(LocalDate date) {
	// 	startDateProperty().set(date);
	// }
  //
	// public LocalDate getStartDate() {
	// 	return startDateProperty().get();
	// }
  //
	// public ObjectProperty<LocalDate> startDateProperty() {
	// 	return startDate.valueProperty();
	// }
  //
	// public void setEndDate(LocalDate date) {
	// 	endDateProperty().set(date);
	// }
  //
	// public LocalDate getEndDate() {
	// 	return endDateProperty().get();
	// }
  //
	// public ObjectProperty<LocalDate> endDateProperty() {
	// 	return endDate.valueProperty();
	// }
  //
	// public void setSorAItems(){
	// 	max.setDisable(true);
	// 	min.setDisable(true);
	// 	startDate.setDisable(true);
	// 	endDate.setDisable(true);
	// 	refreshRate.setDisable(true);
  //
	// 	active.setSelected(true);
	// 	handlerActiveInactive(null);
	// 	active.setDisable(true);
	// }

}
