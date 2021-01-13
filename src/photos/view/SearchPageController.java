/**
 * @author Xiang Ao, Shijie Xu
 * @since April.17, 2019
 * 
 * This class is search page controller.
 * .
 * CS213 Software Methodology Project 3: Photo.
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
import photos.type.photo;

import java.io.IOException;
import java.sql.CallableStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.omg.CORBA.INITIALIZE;

import javafx.event.ActionEvent;

import javafx.scene.control.CheckBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;

public class SearchPageController {
	private static final int CAPTION = 1;
	private static final int DATE = 2;
	private static final int TAG = 3;
	static int searchType=0;
	static int resultNum=0;
	static String searchString; 
	static ArrayList<String> photoURL;
	static ArrayList<String> photoDate;
	static boolean moreResult = false;

	@FXML
	private Button BackButton;
	@FXML
	private TextField captionInput;
	@FXML
	private DatePicker dateStart;
	@FXML
	private DatePicker dateEnd;
	@FXML
	private Button captionSearchButton;
	@FXML
	private Button searchByDateButton;
	@FXML
	private Label dateillegalTag;
	@FXML
	private CheckBox FirstTagCheckBox;
	@FXML
	private CheckBox SecondTagCheckBox;
	@FXML
	private CheckBox matchAllCheckBox;
	@FXML
	private ChoiceBox FirstChoiceBox;
	@FXML
	private ChoiceBox SecondChoiceBox;
	@FXML
	private TextField FirstTagInput;
	@FXML
	private TextField SecondTagInput;
	@FXML
	private Button SearchByTagButton;
	@FXML
	private Button clearButton;
	@FXML
	private Label noresultTag;

	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * initialize method. Used to set scene error label invisible
	 */
	@FXML
	public void initialize() {
		noresultTag.setVisible(false);
		dateillegalTag.setVisible(false);
	}


	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #BackButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void Back(ActionEvent event) throws IOException {
		Stage currentStage = (Stage) BackButton.getScene().getWindow();
		currentStage.close();

	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #captionSearchButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void searchByCaption(ActionEvent event) throws IOException {
		searchType = CAPTION;
		String searchInput = captionInput.getText(); // input string
		searchString = searchInput;
		int photoList_size = Login.photoList.size();
		photo target = null;
		for(int i = 0 ; i < photoList_size; i++) {
			photo currentIterator = Login.photoList.get(i);
			String currentCaption = currentIterator.getCaption();
			if(searchInput.equals(currentCaption)) {
				//find
				target = currentIterator;
				photoURL.add(target.getURL());
			}
		}
		resultNum = photoURL.size();
		if(resultNum >= 1) {
			if(noresultTag.isVisible()) {
				noresultTag.setVisible(false);
			}
			if(resultNum > 1) {
				moreResult = true;
			}
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SearchResult.fxml"));
			Stage searchStage = new Stage();
			searchStage.setScene(new Scene(root));
			searchStage.setTitle("Search by Caption");
			searchStage.show();
		}
		else if(resultNum < 1) {
			noresultTag.setVisible(true);
		}
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #searchByDateButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void searchByDate(ActionEvent event) throws IOException {
		searchType = DATE;
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String start = sdf.format(dateStart.getUserData());
		String end = sdf.format(dateEnd.getUserData());
		if(!dateSelectCheck(start, end)) {
			dateillegalTag.setVisible(true);
			return;
		}else {
			dateillegalTag.setVisible(false);
		}
		int photoList_size = Login.photoList.size();
		photo target = null;
		for(int i = 0 ; i < photoList_size; i++) {
			photo currentIterator = Login.photoList.get(i);
			String currentDate = currentIterator.getDateString();
			if(dateCheck(start, end, currentDate)) {
				target = currentIterator;
				photoURL.add(target.getURL());
				photoDate.add(target.getDateString());
			}
		}
		resultNum = photoURL.size();
		if(resultNum >= 1) {
			if(noresultTag.isVisible()) {
				noresultTag.setVisible(false);
			}
			if(resultNum > 1) {
				moreResult = true;
			}
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SearchResult.fxml"));
			Stage searchStage = new Stage();
			searchStage.setScene(new Scene(root));
			searchStage.setTitle("Search by Caption");
			searchStage.show();
		}
		else if(resultNum < 1) {
			noresultTag.setVisible(true);
		}
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #SearchByTagButton}].onAction
	 * @param event button clicked
	 */
	@FXML
	public void searchByTag(ActionEvent event) throws IOException {
		searchType = TAG;



		// result gained; open searchResult window
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/SearchResult.fxml"));
		Stage searchStage = new Stage();
		searchStage.setScene(new Scene(root));
		searchStage.setTitle("Search by Tag");
		searchStage.show();
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * Event Listener on Button[{@link #clearButton}].onAction
	 * @param event button clicked
	 */
	@SuppressWarnings("unchecked")
	@FXML
	public void Clear(ActionEvent event) {
		captionInput.setText(null);
		dateStart.setValue(null);
		dateEnd.setValue(null);
		FirstTagCheckBox.setSelected(false);
		SecondTagCheckBox.setSelected(false);
		FirstChoiceBox.setValue(null);
		SecondChoiceBox.setValue(null);
		FirstTagInput.setText(null);
		SecondTagInput.setText(null);
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to check if current photo's date is in user selected date range
	 * @param start Start date string in SimpleDateFormat
	 * @param end end date string in SimpleDateFormat
	 * @param target current date string in SimpleDateFormat
	 * @return true if current photo date legal
	 */
	private boolean dateCheck(String start, String end, String target) {
		// Date format: "MM/dd/yyyy"
		if(getYear(target) >= getYear(start) && getYear(target) <= getYear(end)) { // year in duration
			if(getYear(start) == getYear(end)) {
				if(getMonth(target) >= getMonth(start) && getMonth(target) <= getMonth(end)) {
					if(getMonth(start) == getMonth(end)) {
						if(getDay(target) >= getDay(start) && getDay(target) <= getDay(end)) {
							return true;
						}
						else {
							return false;
						}
					}
					else {
						if(getMonth(target) == getMonth(start)) {
							if(getDay(target) >= getDay(start)) {
								return true;
							}
							else {
								return false;
							}
						}
						else if (getMonth(target) == getMonth(end)){
							if(getDay(target) <= getDay(end)) {
								return true;
							}
							else {
								return false;
							}
						}
					}
				}
				else {
					return false;
				}
			}
			else {
				// floor compare
				if(getYear(target) == getYear(start)) { 
					if(getMonth(target) > getYear(start)) {
						return true;
					}
					else if(getMonth(target) == getYear(start)) {
						if(getDay(target)>=getDay(start)) {
							return true;
						}
						else {
							return false;
						}
					}
					else { // getMonth(target) < getMonth(start)
						return false;
					}
				}
				// ceiling compare
				else if(getYear(target) == getYear(end)) {
					if(getMonth(target) < getMonth(end)) {
						return true;
					}
					else if(getMonth(target) == getMonth(end)) {
						if(getDay(target) <= getDay(end)) {
							return true;
						}
						else {
							return false;
						}
					}
					else { //getMonth(target) > getMonth(end){
						return false;

					}
				}
				// year between ; satisfy 
				else {
					return true;
				}
			}

		}
		return false;
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to check if user selected date is legal
	 * @param start Start date string in SimpleDateFormat
	 * @param end end date string in SimpleDateFormat
	 * @return true if user selected date legal
	 */
	private boolean dateSelectCheck(String start, String end) {
		if(getYear(start) <= getYear(end)) {
			if(getMonth(start) <= getMonth(end)) {
				if(getDay(start) <= getDay(end)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to get month in date stored as SimpleDateFormat String
	 * @param date input date string stored as SimpleDateFormat string
	 * @return int month return integer month
	 */
	private int getMonth(String date) {
		int month;
		String monthString;
		monthString = date.substring(0, 2);
		month = Integer.parseInt(monthString);
		return month;
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to get day in date stored as SimpleDateFormat String
	 * @param date input date string stored as SimpleDateFormat string
	 * @return int return integer day
	 */
	private int getDay(String date) {
		int day;
		String dayString;
		dayString = date.substring(3, 5);
		day = Integer.parseInt(dayString);
		return day;
	}
	/**
	 * 
	 * @author Xiang Ao, Shijie Xu
	 * @since April.17, 2019
	 * helping method: used to get year in date stored as SimpleDateFormat String
	 * @param date input date string stored as SimpleDateFormat string
	 * @return int year return integer year
	 */
	private int getYear(String date) {
		int year;
		String yearString;
		yearString = date.substring(6);
		year = Integer.parseInt(yearString);
		return year;
	}
}
