
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CustomControl extends VBox {

	Socket universal;
	static int port = 53217;
	MsgEncoder encoder;

	//private final String SCOPE = "SIS.Scope1";

	@FXML
	private TitledPane title;
	@FXML
	private TextArea console;
	@FXML
	private TextField posterId;
	@FXML
	private TextField min;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	@FXML
	private TextField refreshRate;
	@FXML
	private Button kill;
	@FXML
	private ToggleButton active;

	public CustomControl() {
		// TODO Auto-generated constructor stub
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
				"CustomControl.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();

			//setCache(true);
			//setCacheShape(true);
			//setCacheHint(CacheHint.SPEED);

			setMin(0 + "");
			setMax(0 + "");
			setStartDate(LocalDate.now());
			LocalDate tomorrow = LocalDate.now().withDayOfMonth(LocalDate.now().getDayOfMonth() + 1);
			setEndDate(tomorrow);
			setRefreshRate(100 + "");

			min.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try {

						int mi = Integer.parseInt(newValue);

						int ma = Integer.parseInt(posterId.textProperty().get());

						if (mi > ma) {
							posterId.setText(mi + "");
						}

						min.setText(mi + "");

					} catch (NumberFormatException e) {
						min.setText(oldValue);
					}
				}
			});

			posterId.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					try {

						int ma = Integer.parseInt(newValue);

						int mi = Integer.parseInt(min.textProperty().get());

						if (ma < mi) {
							min.setText(ma + "");
						}
						posterId.setText(ma + "");

					} catch (NumberFormatException e) {
						posterId.setText(oldValue);
					}
				}
			});

			startDate.valueProperty().addListener(
					new ChangeListener<LocalDate>() {

						@Override
						public void changed(
								ObservableValue<? extends LocalDate> observable,
								LocalDate oldValue, LocalDate newValue) {
							// TODO Auto-generated method stub

							LocalDate end = endDate.valueProperty().get();

							if (newValue.isBefore(LocalDate.now())) {
								startDate.setValue(oldValue);
							}
							if (newValue.isAfter(end) || newValue.isEqual(end)) {
								LocalDate tomorrow = newValue.withDayOfMonth(newValue.getDayOfMonth() + 1);
								endDate.setValue(tomorrow);
							}
						}
					});

			endDate.valueProperty().addListener(
					new ChangeListener<LocalDate>() {

						@Override
						public void changed(
								ObservableValue<? extends LocalDate> observable,
								LocalDate oldValue, LocalDate newValue) {
							// TODO Auto-generated method stub

							LocalDate start = startDate.valueProperty().get();

							///if (newValue.isBefore(LocalDate.now())) {
							///	endDate.setValue(oldValue);
							///}
							if (newValue.isBefore(start) || newValue.isEqual(start)) {
								endDate.setValue(oldValue);
							}
						}
					});

			refreshRate.textProperty().addListener(
					new ChangeListener<String>() {

						@Override
						public void changed(
								ObservableValue<? extends String> observable,
								String oldValue, String newValue) {
							try {
								int val = Integer.parseInt(newValue);
								if (val > 100) {
									refreshRate.setText(val + "");	
								} else {
									refreshRate.setText(100 + "");
								}
							} catch (NumberFormatException e) {
								refreshRate.setText(oldValue);
							}
						}
					});

			// title.setOnMouseClicked(new EventHandler<MouseEvent>() {
			//
			// @Override
			// public void handle(MouseEvent event) {
			// // TODO Auto-generated method stub
			// MouseButton button = event.getButton();
			// if (button == MouseButton.SECONDARY) {
			// title.setTextFill(Color.BLACK);
			// }
			// }
			// });
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void handlerAlertResolved(MouseEvent event) {
		MouseButton button = event.getButton();
		if (button == MouseButton.SECONDARY) {
			title.setTextFill(Color.BLACK);
		}
	}

	public void handlerKill(MouseEvent event) {
		// if (selected) {
		// boot.setText("Alive");
		// boot.setTextFill(Color.GREEN);
		// active.setDisable(false);
		// active.setSelected(true);
		// handlerActiveInactive(null);
		//
		// try {
		// if (universal == null) {
		// universal = new Socket("127.0.0.1", port);
		// }
		// if (encoder == null) {
		// encoder = new MsgEncoder(universal.getOutputStream());
		// }
		//
		// KeyValueList bootMsg = new KeyValueList();
		// bootMsg.putPair("Scope", SCOPE);
		// bootMsg.putPair("MessageType", "Boot");
		// bootMsg.putPair("Name", getTitle());
		//
		// encoder.sendMsg(bootMsg);
		//
		// } catch (UnknownHostException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {

			if (universal == null) {
				universal = new Socket("127.0.0.1", port);
			}
			if (encoder == null) {
				encoder = new MsgEncoder(universal.getOutputStream());
			}

			KeyValueList kill = new KeyValueList();
			kill.putPair("Scope", CreateGUI.SCOPE);
			kill.putPair("MessageType", "Setting");
			kill.putPair("Sender", CreateGUI.NAME);
			kill.putPair("Receiver", getTitle());
			kill.putPair("Purpose", "Admin");//"Kill");
			kill.putPair("Password", "ironman");
			kill.putPair("msgId", "22")

			encoder.sendMsg(kill);

			active.setSelected(false);
			handlerActiveInactive(null);
			active.setDisable(true);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handlerActiveInactive(MouseEvent event) {
		boolean selected = active.selectedProperty().get();
		if (selected) {
			active.setText("Active");
			active.setTextFill(Color.GREEN);

			try {
				if (universal == null) {
					universal = new Socket("127.0.0.1", port);
				}
				if (encoder == null) {
					encoder = new MsgEncoder(universal.getOutputStream());
				}

				KeyValueList act = new KeyValueList();
				act.putPair("Scope", CreateGUI.SCOPE);
				act.putPair("MessageType", "Setting");
				act.putPair("Sender", CreateGUI.NAME);
				act.putPair("Receiver", getTitle());

				// modified
				act.putPair("Purpose", "Vote); "//"Activate");

				// modified
				act.putPair("Vote", getMax());
//				act.putPair("Min", getMin());
//
//				LocalDate st = getStartDate();
//				Instant instantS = st.atStartOfDay()
//						.atZone(ZoneId.systemDefault()).toInstant();
//				Date dateS = Date.from(instantS);
//
//				LocalDate ed = getEndDate();
//				Instant instantE = ed.atStartOfDay()
//						.atZone(ZoneId.systemDefault()).toInstant();
//				Date dateE = Date.from(instantE);
//
//				act.putPair("StartDate", dateS.getTime() + "");
//				act.putPair("EndDate", dateE.getTime() + "");
//				act.putPair("RefreshRate", getRefreshRate());

				encoder.sendMsg(act);

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			active.setText("Inactive");
			active.setTextFill(Color.RED);

			try {
				if (universal == null) {
					universal = new Socket("127.0.0.1", port);
				}
				if (encoder == null) {
					encoder = new MsgEncoder(universal.getOutputStream());
				}

				KeyValueList dact = new KeyValueList();
				dact.putPair("Scope", CreateGUI.SCOPE);
				dact.putPair("MessageType", "Setting");
				dact.putPair("Sender", CreateGUI.NAME);
				dact.putPair("Receiver", getTitle());
				dact.putPair("Purpose", "Deactivate");

				encoder.sendMsg(dact);

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void setAlert() {
		title.setTextFill(Color.RED);
	}

	public void setTitle(String t) {
		titleProperty().set(t);
	}

	public String getTitle() {
		return titleProperty().get();
	}

	public StringProperty titleProperty() {
		return title.textProperty();
	}

	public String getConsole() {
		return consoleProperty().get();
	}

	public void setConsole(String value) {
		consoleProperty().set(value);

	}

	public void setActive() {
		active.setDisable(false);
		active.setSelected(true);
		active.setText("Active");
		active.setTextFill(Color.GREEN);
	}


	// modified
	public String getMax() {
		return posteridProperty().get();
	}
	// modified
	public void setMax(String value) {
		posteridProperty().set(value);
	}
	// modified
	public StringProperty posteridProperty() {
		return posterId.textProperty();
	}


	public void setEnable() {
		active.setDisable(false);
	}

	public StringProperty consoleProperty() {
		return console.textProperty();
	}

	public String getMin() {
		return minProperty().get();
	}

	public void setMin(String value) {
		minProperty().set(value);
	}

	public StringProperty minProperty() {
		return min.textProperty();
	}

	public String getRefreshRate() {
		return refreshRateProperty().get();
	}

	public void setRefreshRate(String value) {
		refreshRateProperty().set(value);
	}

	public StringProperty refreshRateProperty() {
		return refreshRate.textProperty();
	}

	public void setStartDate(LocalDate date) {
		startDateProperty().set(date);
	}

	public LocalDate getStartDate() {
		return startDateProperty().get();
	}

	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate.valueProperty();
	}

	public void setEndDate(LocalDate date) {
		endDateProperty().set(date);
	}

	public LocalDate getEndDate() {
		return endDateProperty().get();
	}

	public ObjectProperty<LocalDate> endDateProperty() {
		return endDate.valueProperty();
	}

	public void setSorAItems(){
		// modified
		posterId.setDisable(true);


		min.setDisable(true);
		startDate.setDisable(true);
		endDate.setDisable(true);
		refreshRate.setDisable(true);
		
		active.setSelected(true);
		handlerActiveInactive(null);
		active.setDisable(true);
	}

}
