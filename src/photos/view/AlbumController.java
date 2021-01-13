package photos.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import photos.type.Album;
import photos.type.User;
import photos.type.photo;

public class AlbumController {
	public static String photoCaption;
	static FileInputStream input = null;
	static String address;
	static String fileName;
	private static final int REMOVE = 2;
	private static final int RENAME = 3;
	private static final int COPY = 4;
	private static final int MOVETO = 5;
	private static final int ADDTAGS = 11;
	private static final int DELETETAGS = 12;
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private int operationIndex;
	private User choiceBoxSelectedUser;
	private Album choiceBoxSelectedAlbum;
	private int photoIndex;

	@FXML
	private Label albumTitle;
	@FXML
	private Label caption;
	@FXML
	private Button addTagButton;
	@FXML
	private Button quitButton;
	@FXML
	private ListView<photo> thumbnailList;
	@FXML
	private Button removePhotoButton;
	@FXML
	private Button applyChangeButton;
	@FXML
	private Button cancelChangeButton;
	@FXML
	private Button renameButton;
	@FXML
	private ImageView captionArea;
	@FXML
	private Button addPhotoButton;
	@FXML
	private Button copyPhotoButton;
	@FXML
	private Button moveToButton;
	@FXML
	private Button deleteTagButton;
	@FXML
	private Button lastPhotoButton;
	@FXML
	private Button nextPhotoButton;
	@FXML
	private Label tag1;
	@FXML
	private Label field1;
	@FXML
	private Label tag2;
	@FXML
	private Label field2;
	@FXML
	private Label date;
	@FXML
	private Label warningMsg;
	@FXML
	private Label renameWarningMsg;
	@FXML
	private TextField renameField;
	@FXML
	private AnchorPane displayAnchor;
	@FXML
	private AnchorPane editAnchor;
	@FXML
	private ChoiceBox<String> choiceTag1;
	@FXML
	private ChoiceBox<String> choiceTag2;
	@FXML
	private TextField fillTag1;
	@FXML
	private TextField fillTag2;
	@FXML
	private TextField choiceField1;
	@FXML
	private TextField choiceField2;
	@FXML
	private TextField fillField1;
	@FXML
	private TextField fillField2;
	@FXML
	private AnchorPane copyMove;
	@FXML
	private ChoiceBox<User> copyMoveUser;
	@FXML
	private ChoiceBox<Album> copyMoveAlbum;

	/**
	 * Listen the change of the photo list
	 */
	ChangeListener<photo> listener = (obs, oldValue, newValue) -> {
		if (newValue != null) {
			// how to refresh the list?
			caption.setText(newValue.getCaption());
			date.setText(newValue.getDateString());
			tag1.setText(newValue.getTagsName1());
			tag2.setText(newValue.getTagsName2());
			field1.setText(newValue.getTagsField1());
			field2.setText(newValue.getTagsField2());
			renameField.setText(newValue.getCaption());
			photoCaption = newValue.getCaption();
			address = newValue.getURL();
			fileName = newValue.getFileName();
			
			try {
				input = new FileInputStream(newValue.getURL());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Image image = new Image(input);
			captionArea.setImage(image);
		}
	};

	/**
	 * initialize the window
	 * hide warning msgs, tags add/delete windows and copy windows
	 * @throws Exception 
	 */
	@FXML
	public void initialize() throws Exception {
		albumTitle.setText(UserIniController.selectAlbumName);
		editAnchor.setVisible(false);
		renameField.setVisible(false);
		warningMsg.setVisible(false);
		renameWarningMsg.setVisible(false);
		copyMove.setVisible(false);
		copyMoveUser.setItems(Login.getUserList());
		obsL();
	}

	// Event Listener on Button[#addPhotoButton].onAction
	/**
	 * add photo from filechoose
	 * @param event
	 */
	@FXML
	public void addPhoto(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Add new photo");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"),
				new ExtensionFilter("All Files", "*.*"));
		File selectedFile = fileChooser.showOpenDialog(null);
		String URL = selectedFile.getAbsolutePath();
		String caption = selectedFile.getName();
		Calendar cal = Calendar.getInstance();
		String date = sdf.format(cal.getTime());
		photo newPhoto = new photo(URL, caption, caption, date, "", "", "", "");
		if (Login.photoList.contains(newPhoto)) {

		} else {
			Login.photoList.add(newPhoto);
			thumbnailList.getSelectionModel().select(Login.photoList.size() - 1);
			sortList();
		}
	}

	// Event Listener on Button[#removePhotoButton].onAction
	/**
	 * Remove current selected photo
	 * @param event
	 */
	@FXML
	public void removePhoto(ActionEvent event) {
		warningMsg.setVisible(true);
		operationIndex = REMOVE;
	}

	/**
	 * apply the remove action
	 */
	private void removeApply() {
		int index = lvInt();
		if (index == -1) {
			return;
		}
		Login.photoList.remove(index);
		if (index == Login.photoList.size()) {
			select(index - 1);
		} else {
			select(index);
		}
	}

	// Event Listener on Button[#renameButton].onAction
	/**
	 * rename the photo/ recaption the photo
	 * @param event
	 */
	@FXML
	public void renamePhoto(ActionEvent event) {
		renameField.setVisible(true);
		renameWarningMsg.setVisible(true);
		renameField.setText(photoCaption);
		operationIndex = RENAME;
	}

	/**
	 * apply the rename/recaption action
	 */
	public void renameApply() {
		String newCaption = renameField.getText();
		int index = lvInt();
		if (index == -1) {
			return;
		}
		Login.photoList.get(index).setCaption(newCaption);
		sortList();
		select(0);
	}

	// Event Listener on Button[#copyPhotoButton].onAction
	/**
	 * Copy the photo from this album to other user's album without deleting this photo
	 * @param event
	 */
	@FXML
	public void copyPhoto(ActionEvent event) {
		operationIndex = COPY;
		copyMove.setVisible(true);
		displayAnchor.setVisible(false);
	}

	/**
	 * Apply the copy action
	 * @throws Exception
	 */
	public void copyApply() throws Exception {
		copyMoveUser.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
			System.out.println(newVal.getUsername());
		}); 
		
		choiceBoxSelectedUser = copyMoveUser.getSelectionModel().getSelectedItem();
		Login.readAlbumFile(choiceBoxSelectedUser.getUsername());
		copyMoveAlbum.setItems(Login.albumList);
		choiceBoxSelectedAlbum = copyMoveAlbum.getSelectionModel().getSelectedItem();
		System.out.println(choiceBoxSelectedUser.getUsername()+" "+choiceBoxSelectedAlbum.getAlbumName());
	}

	// Event Listener on Button[#moveToButton].onAction
	/**
	 * Move the photo from this album to other user's album with deleting this photo
	 * @param event
	 */
	@FXML
	public void moveTo(ActionEvent event) {
		operationIndex = MOVETO;
		copyMove.setVisible(true);
		displayAnchor.setVisible(false);
	}

	public void moveToApply() {

	}

	// Event Listener on Button[#addTagButton].onAction
	/**
	 * Adding Tags for this photo
	 * @param event
	 */
	@FXML
	public void addTag(ActionEvent event) {
		displayAnchor.setVisible(false);
		editAnchor.setVisible(true);

	}

	// Event Listener on Button[#deleteTagButton].onAction
	/**
	 * delete the tags of this photo
	 * @param event
	 */
	@FXML
	public void deleteTag(ActionEvent event) {
		warningMsg.setVisible(true);
	}

	// Event Listener on Button[#lastPhotoButton].onAction
	/**
	 * show previous photo like slidshow
	 * @param event
	 */
	@FXML
	public void lastPhoto(ActionEvent event) {
		photoIndex--;
		updatePhotoDetails();
	}

	// Event Listener on Button[#nextPhotoButton].onAction
	/**
	 * show next photo like slidshow
	 * @param event
	 */
	@FXML
	public void nextPhoto(ActionEvent event) {
		photoIndex++;
		updatePhotoDetails();
	}

	// Event Listener on Button[#applyChangeButton].onAction
	/**
	 * click apply button for confirm change
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void applyChange(ActionEvent event) throws Exception {
		switch (operationIndex) {
		case REMOVE:
			removeApply();
			warningMsg.setVisible(false);
			break;
		case RENAME:
			renameApply();
			renameWarningMsg.setVisible(false);
			renameField.setText("");
			renameField.setVisible(false);
			break;
		case COPY:
			copyApply();
			copyMove.setVisible(false);
			displayAnchor.setVisible(true);
			break;
		case MOVETO:
			moveToApply();
			copyMove.setVisible(false);
			displayAnchor.setVisible(true);
			break;
		case ADDTAGS:
			break;
		case DELETETAGS:
			warningMsg.setVisible(false);
			renameWarningMsg.setVisible(false);
			break;
		}
	}

	// Event Listener on Button[#cancelChangeButton].onAction
	/*
	 * Click cancel button for canceling change
	 */
	@FXML
	public void cancelButton(ActionEvent event) {
		switch (operationIndex) {
		case REMOVE:
			warningMsg.setVisible(false);
			break;
		case RENAME:
			warningMsg.setVisible(false);
			renameWarningMsg.setVisible(false);
			renameField.setText("");
			renameField.setVisible(false);
			break;
		case COPY:
			copyMove.setVisible(false);
			displayAnchor.setVisible(true);
			break;
		case MOVETO:
			copyMove.setVisible(false);
			displayAnchor.setVisible(true);
			break;
		case ADDTAGS:
			fillTag1.setText("");
			fillTag2.setText("");
			choiceField1.setText("");
			choiceField2.setText("");
			fillField1.setText("");
			fillField2.setText("");
			displayAnchor.setVisible(true);
			editAnchor.setVisible(false);
			break;
		case DELETETAGS:
			warningMsg.setVisible(false);
			renameWarningMsg.setVisible(false);
			break;
		}
	}

	/**
	 * Click thumbnail list view for selection property.
	 * @param event
	 */
	public void click(MouseEvent event) {
		thumbnailList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent clickEvent) {
				if (clickEvent.getClickCount() == 2) {
					photo currentPhotoSelected = thumbnailList.getSelectionModel().getSelectedItem();
					Parent root;
					photoCaption = currentPhotoSelected.getCaption();
					Image image = new Image(currentPhotoSelected.getURL());
					captionArea.setImage(image);
				}
			}
		});
	}

	/**
	 * Observable List method
	 * @throws Exception
	 */
	private void obsL() throws Exception {
		Login.photoList = FXCollections.observableArrayList();
		Login.readPhotoFile(Login.usernameInput, UserIniController.selectAlbumName);
		thumbnailList.setItems(Login.photoList);
		thumbnailList.setCellFactory(param -> new ListCell<photo>() {
			private ImageView img = new ImageView();

			@Override
			protected void updateItem(photo item, boolean empty) {
				super.updateItem(item, empty);
				img.setFitHeight(70);
				img.setPreserveRatio(true);

				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					String Url = item.getURL();
					try {
						input = new FileInputStream(Url);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					img.setImage(new Image(input));
					setText(null);
					setGraphic(img);
				}
			}
		});
		thumbnailList.getSelectionModel().selectedItemProperty().addListener(listener);
		sortList();
		select(0);
	}

	/**
	 * method that get the list view index
	 * @return index of listview selection.
	 */
	private int lvInt() {
		return thumbnailList.getSelectionModel().getSelectedIndex();
	}

	/**
	 * Select of listview
	 * @param n
	 */
	private void select(int n) {
		if (Login.photoList.size() > 0) {
			thumbnailList.getSelectionModel().select(n);
		}
	}
	
	/**
	 * the method that sort the List
	 */
	private void sortList() {
		thumbnailList.getSelectionModel().selectedItemProperty().removeListener(listener);
		if (Login.photoList != null || Login.photoList.size() > 0) {
			Login.photoList.sort(Comparator.comparing(photo -> photo.getCaption(), String.CASE_INSENSITIVE_ORDER));
		}
		thumbnailList.getSelectionModel().selectedItemProperty().addListener(listener);
	}
	
	public void updatePhotoDetails() {
		try {
			input = new FileInputStream(Login.photoList.get(photoIndex).getURL());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image image = new Image(input);
		captionArea.setImage(image);
		caption.setText("Caption: " + Login.photoList.get(photoIndex).getCaption());
		date.setText("Photo Date: " + Login.photoList.get(photoIndex).getDateString());
		lastPhotoButton.setDisable(photoIndex == 0);
		nextPhotoButton.setDisable(photoIndex == Login.photoList.size()-1);
	}

	// Event Listener on Button[#quitButton].onAction
	/**
	 * Button for quit
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void quit(ActionEvent event) throws IOException {
		try {
			Login.photoSerImp(Login.usernameInput, UserIniController.selectAlbumName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Stage currentStage = (Stage) warningMsg.getScene().getWindow();
		currentStage.close();
	}
}
