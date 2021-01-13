/**
 * @author Xiang Ao, Shijie Xu
 * @since April.17, 2019
 * 
 * This class is stock page controller.
 * .
 * CS213 Software Methodology Project 3: Photo.
 */
package photos.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import photos.app.Photos;
import photos.type.Album;
import photos.type.User;

public class stockController {
	public static String albumnameInput;
	public static String selectAlbumName;
	private static final int ADD = 1;
	private static final int DELETE = 2;
	private static final int RENAME = 3;
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private int operationIndex;

	@FXML
	private Button createAlbumButton;
	@FXML
	private Label welcomeLable;
	@FXML
	private Button quitButton;
	@FXML
	private ListView<Album> albumNameList;
	@FXML
	private Label albumNameField;
	@FXML
	private Label albumDateField;
	@FXML
	private Label photoNumberField;
	@FXML
	private Button deleteAlbumButton;
	@FXML
	private Button applyButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button renameAlbumButton;
	@FXML
	private Button searchButton;
	@FXML
	private TextField inputAlbumNameField;
	@FXML
	private Label warningMsg;
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Listener variable, used to update info
	 */
	ChangeListener<Album> listener = (obs, oldValue, newValue) -> {
		if (newValue != null) {
			selectAlbumName = newValue.getAlbumName();
			albumNameField.setText(newValue.getAlbumName());
			albumDateField.setText(newValue.getAlbumDate());
			photoNumberField.setText(newValue.getPhotoNum());
			inputAlbumNameField.setText(newValue.getAlbumName());
			photoNumberField.setText(Login.photoList.size() + "");
		}
	};
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * initialize method. Used to set scene error label invisible and change title and username label
	 */
	@FXML
	public void initialize() throws Exception {
		welcomeLable.setText("Welcome! " + Login.usernameInput);
		inputAlbumNameField.setVisible(false);
		warningMsg.setVisible(false);
		obsL();
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #createAlbumButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void createAlbum(ActionEvent event) {
		inputAlbumNameField.setVisible(true);
		operationIndex = ADD;
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to apply create album
	 */
	private void createApply() throws Exception {
		String albumName = inputAlbumNameField.getText();
		Calendar cal = Calendar.getInstance();
		String date = sdf.format(cal.getTime());
		String photoNum = "0";
		Album newAlbum = new Album(albumName, date, photoNum);
		if (Login.albumList.contains(newAlbum)) {
			return;
		} else {
			Login.albumList.add(newAlbum);
			albumNameList.getSelectionModel().select(Login.albumList.size() - 1);
			File pSI = new File(Login.usernameInput + "_" + albumName + "_PhotoList");
			if (!pSI.exists()) {
				Login.photoSerImp(Login.usernameInput, albumName);
			}
			sortList();
		}
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #deleteAlbumButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void deleteAlbum(ActionEvent event) {
		warningMsg.setVisible(true);
		operationIndex = DELETE;
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to apply delete album
	 */
	private void deleteApply() {
		int index = lvInt();
		if (index == -1) {
			return;
		}
		Login.specialDelete(2,selectAlbumName);
		Login.albumList.remove(index);
		if (index == Login.albumList.size()) {
			select(index - 1);
		} else {
			select(index);
		}
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #renameAlbumButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void renameAlbum(ActionEvent event) {
		inputAlbumNameField.setVisible(true);
		operationIndex = RENAME;
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to apply rename album
	 */
	private void renameApply() {
		String newName = inputAlbumNameField.getText();
		int index = lvInt();
		if (index == -1) {
			return;
		}
		Login.albumList.get(index).setAlbumName(newName);
		sortList();
		select(0);
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #searchButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void search(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/SearchPage.fxml"));
		Stage searchStage = new Stage();
		searchStage.setScene(new Scene(root));
		searchStage.setTitle("Search");
		searchStage.show();
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #applyButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void applyChange(ActionEvent event) throws Exception {
		switch (operationIndex) {
		case ADD:
			createApply();
			inputAlbumNameField.setVisible(false);
			inputAlbumNameField.setText("");
			break;
		case DELETE:
			deleteApply();
			break;

		case RENAME:
			renameApply();
			break;
		}
		operationIndex = 0;
		warningMsg.setVisible(false);
		inputAlbumNameField.setVisible(false);
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #cancelButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void cancelChange(ActionEvent event) {
		switch (operationIndex) {
		case ADD:
			inputAlbumNameField.setVisible(false);
			inputAlbumNameField.setText("");
			break;
		case DELETE:
			break;
		case RENAME:
			inputAlbumNameField.setVisible(false);
			inputAlbumNameField.setText("");
			break;
		}
		operationIndex = 0;
		warningMsg.setVisible(false);
		inputAlbumNameField.setVisible(false);
	}


	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Double Click selected listview item
	 * @param event double click
	 */
	public void click(MouseEvent event) {
		albumNameList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent clickEvent) {
				if (clickEvent.getClickCount() == 2) {
					Album currentAlbumSelected = albumNameList.getSelectionModel().getSelectedItem();
					Parent root;
					albumnameInput = currentAlbumSelected.getAlbumName();
					try {
						root = FXMLLoader.load(getClass().getResource("/fxml/Album.fxml"));
						Stage albumStage = new Stage();
						albumStage.setScene(new Scene(root));
						albumStage.setTitle("album");
						albumStage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to refresh abbum list
	 */
	private void obsL() throws Exception {
		Login.albumList = FXCollections.observableArrayList();
		Login.readAlbumFile(Login.usernameInput);
		albumNameList.setItems(Login.albumList);
		albumNameList.setCellFactory(param -> new ListCell<Album>() {
			@Override
			protected void updateItem(Album item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					setText(item.getAlbumName());
				}
			}
		});
		albumNameList.getSelectionModel().selectedItemProperty().addListener(listener);
		sortList();
		select(0);
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to sort album list by album name
	 */
	private void sortList() {
		albumNameList.getSelectionModel().selectedItemProperty().removeListener(listener);
		if (Login.albumList != null || Login.albumList.size() > 0) {
			Login.albumList.sort(Comparator.comparing(Album -> Album.getAlbumName(), String.CASE_INSENSITIVE_ORDER));
		}
		albumNameList.getSelectionModel().selectedItemProperty().addListener(listener);
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to get selected album index
	 */
	private int lvInt() {
		return albumNameList.getSelectionModel().getSelectedIndex();
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to select album
	 */
	private void select(int n) {
		if (Login.albumList.size() > 0) {
			albumNameList.getSelectionModel().select(n);
		}
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #quitButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void quit(ActionEvent event) throws IOException {
		try {
			System.out.println("quitRead:" + Login.usernameInput);
			Login.albumSerImp(Login.usernameInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Stage currentStage = (Stage) welcomeLable.getScene().getWindow();
		currentStage.close();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		Stage LoginStage = new Stage();
		LoginStage.setScene(new Scene(root));
		LoginStage.show();
		Photos.setPrimaryStage(LoginStage);
	}
}
