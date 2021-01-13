/**
 * @author Xiang Ao, Shijie Xu
 * @since April.10, 2019
 * 
 * This is controller of admin page.
 * .
 * CS213 Software Methodology Project 3: Photo Library.
 */
package photos.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.app.Photos;
import photos.type.User;
import java.util.Calendar;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class adminController {
	public static final int ADD = 1;
	public static final int DELETE = 2;
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	public static String selectUsername;
	private int operationIndex;

	@FXML
	private Button createButton;
	@FXML
	private Button quitButton;
	@FXML
	private ListView<User> usernameList;
	@FXML
	private Label usernameField;
	@FXML
	private Label date;
	@FXML
	private Label albumNumber;
	@FXML
	private Button deleteButton;
	@FXML
	private Button applyButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField inputUsernameField;
	@FXML
	private Label warningMsg;

	/**
	 * Listen the change of the user list
	 */
	ChangeListener<User> listener = (obs, oldValue, newValue) -> {
		if (newValue != null) {
			usernameField.setText(newValue.getUsername());
			selectUsername = newValue.getUsername();
			date.setText(newValue.getDate());
			albumNumber.setText(newValue.getAlbumNum());
			inputUsernameField.setText(newValue.getUsername());
		}
	};
	
	/**
	 * initialize the window
	 * hide warning msgs
	 * @throws Exception 
	 */
	public void initialize() throws Exception {
		inputUsernameField.setVisible(false);
		warningMsg.setVisible(false);
		obsL();
	}

	// Event Listener on Button[#createButton].onAction
	/**
	 * Button for creating new User
	 * @param event 
	 */
	@FXML
	public void createUser(ActionEvent event) {
		inputUsernameField.setVisible(true);
		operationIndex = ADD;
	}

	/**
	 * confirm the create while press apply button
	 * @throws Exception
	 */
	public void createApply() throws Exception {
		String username = inputUsernameField.getText();
		Calendar cal = Calendar.getInstance();
		String date = sdf.format(cal.getTime());
		String numOfAlbum = "0";
		User newUser = new User(username, date, numOfAlbum);
		if (Login.userList.contains(newUser)) {
			return;
		} else {
			Login.userList.add(newUser);
			usernameList.getSelectionModel().select(Login.userList.size() - 1);
			File aSI = new File(username + "_AlbumList");
			if(!aSI.exists()) {
				Login.albumSerImp(username);
			}
			sortList();
		}
	}

	// Event Listener on Button[#deleteButton].onAction
	@FXML
	/**
	 * Button for deleting a selected user
	 * @param event
	 */
	public void deleteUser(ActionEvent event) {
		warningMsg.setVisible(true);
		operationIndex = DELETE;
	}

	/**
	 * confirm deleting action
	 */
	public void deleteApply() {
		int index = lvInt();
		if (index == -1) {
			return;
		}
		Login.specialDelete(1, selectUsername);
		Login.userList.remove(index);
		if (index == Login.userList.size()) {
			select(index - 1);
		} else {
			select(index);
		}
	}

	// Event Listener on Button[#applyButton].onAction
	/**
	 * button for apply action
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void applyChange(ActionEvent event) throws Exception {
		switch (operationIndex) {
		case ADD:
			createApply();
			inputUsernameField.setVisible(false);
			inputUsernameField.setText("");
			break;
		case DELETE:
			deleteApply();
			break;
		}
		operationIndex = 0;
		warningMsg.setVisible(false);
		inputUsernameField.setVisible(false);
	}

	// Event Listener on Button[#cancelButton].onAction
	/**
	 * Button for cancel action.
	 * @param event
	 */
	@FXML
	public void cancelChange(ActionEvent event) {
		switch (operationIndex) {
		case ADD:
			inputUsernameField.setVisible(false);
			inputUsernameField.setText("");
			break;
		case DELETE:
			break;
		}
		operationIndex = 0;
		warningMsg.setVisible(false);
		inputUsernameField.setVisible(false);
	}

	/**
	 * the method of observableList
	 * @throws Exception
	 */
	public void obsL() throws Exception {
		Login.userList = FXCollections.observableArrayList();
		Login.readUserFile();
		usernameList.setItems(Login.userList);
		usernameList.setCellFactory(param -> new ListCell<User>() {
			@Override
			protected void updateItem(User item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item.getUsername());
				}
			}
		});
		usernameList.getSelectionModel().selectedItemProperty().addListener(listener);
		sortList();
		select(0);
	}

	/**
	 * the method that sort the List
	 */
	private void sortList() {
		usernameList.getSelectionModel().selectedItemProperty().removeListener(listener);
		if (Login.userList != null || Login.userList.size() > 0) {
			Login.userList.sort(Comparator.comparing(User -> User.getUsername(), String.CASE_INSENSITIVE_ORDER));
		}
		usernameList.getSelectionModel().selectedItemProperty().addListener(listener);
	}
	
	/**
	 * method that get the list view index
	 * @return index of listview selection.
	 */
	private int lvInt() {
		return usernameList.getSelectionModel().getSelectedIndex();
	}

	/**
	 * Select of listview
	 * @param n
	 */
	private void select(int n) {
		if (Login.userList.size() > 0) {
			usernameList.getSelectionModel().select(n);
		}
	}
	
	// Event Listener on Button[#quitButton].onAction
	/**
	 * Button for quit
	 * @param event
	 * @throws Exception
	 */
		@FXML
		public void quit(ActionEvent event) throws Exception {
			try {
				Login.userSerImp();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Stage currentStage = (Stage) warningMsg.getScene().getWindow();
			currentStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			Stage adminStage = new Stage();
			adminStage.setScene(new Scene(root));
			adminStage.show();
			Photos.setPrimaryStage(adminStage);
		}
}
