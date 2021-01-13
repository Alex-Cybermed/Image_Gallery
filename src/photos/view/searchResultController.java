/**
 * @author Xiang Ao, Shijie Xu
 * @since April.17, 2019
 * 
 * This class is search result controller.
 * .
 * CS213 Software Methodology Project 3: Photo.
 */
package photos.view;

import javafx.fxml.FXML;

import javafx.scene.control.Button;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class searchResultController {
	private static final int CAPTION = 1;
	private static final int DATE = 2;
	private static final int TAG = 3;
	private int currentPhoto = 0;
	private String address;
	private FileInputStream fileInput;
	private Image currentImage;
	@FXML
	private ImageView photo_imageview;
	@FXML
	private Button quitButton;
	@FXML
	private Label searchType;
	@FXML
	private Button lastPhotoButton;
	@FXML
	private Button nextPhotoButton;
	@FXML
	private Label searchType2;
	@FXML
	private Label searchString; //feedback
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * initialize method. Used to set scene label to show useful information and print out first result
	 */
	@FXML
	public void initialize() throws FileNotFoundException {
		if(!SearchPageController.moreResult) {
			lastPhotoButton.setVisible(false);
			nextPhotoButton.setVisible(false);
		}
		lastPhotoButton.setDisable(true);
		switch (SearchPageController.searchType) {
		case CAPTION:
			searchType.setText("Caption");
			searchType2.setText("Caption:");
			searchString.setText(SearchPageController.searchString);
			break;
		case DATE:
			searchType.setText("Date");
			searchType2.setText("Date:");
			searchString.setText(SearchPageController.photoDate.get(currentPhoto));
			break;
		case TAG:
			searchType.setText("Tag");
			searchType2.setText("Tag:");
			break;
		
		default:
			break;
		}
		
		address = SearchPageController.photoURL.get(currentPhoto);
		fileInput = new FileInputStream(address);
		currentImage = new Image(fileInput);
		photo_imageview.setImage(currentImage);
	}

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #quitButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void quit(ActionEvent event) {
		SearchPageController.resultNum = 0;
		SearchPageController.searchType = 0;
		SearchPageController.photoURL = null;
		SearchPageController.photoDate = null;
		//
		Stage currentStage = (Stage) quitButton.getScene().getWindow();
		currentStage.close();
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #lastPhotoButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void lastPhoto(ActionEvent event) throws FileNotFoundException {
		currentPhoto--;
		if(currentPhoto < SearchPageController.resultNum - 1 && nextPhotoButton.isDisable()) {
			nextPhotoButton.setDisable(false);
		}
		address = SearchPageController.photoURL.get(currentPhoto);
		fileInput = new FileInputStream(address);
		currentImage = new Image(fileInput);
		photo_imageview.setImage(currentImage);
		if(currentPhoto == 0) {
			lastPhotoButton.setDisable(true);
		}
		
		if(SearchPageController.searchType == DATE) {
			searchString.setText(SearchPageController.photoDate.get(currentPhoto));
		}
		
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #nextPhotoButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void nextPhoto(ActionEvent event) throws FileNotFoundException {
		currentPhoto++;
		if(currentPhoto>0 && lastPhotoButton.isDisable()) {
			lastPhotoButton.setDisable(false);
		}
		address = SearchPageController.photoURL.get(currentPhoto);
		fileInput = new FileInputStream(address);
		currentImage = new Image(fileInput);
		photo_imageview.setImage(currentImage);
		if(currentPhoto == SearchPageController.resultNum-1) {
			nextPhotoButton.setDisable(true);
		}
		if(SearchPageController.searchType == DATE) {
			searchString.setText(SearchPageController.photoDate.get(currentPhoto));
		}
	}

}
