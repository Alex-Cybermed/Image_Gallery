/**
 * @author Xiang Ao, Shijie Xu
 * @since April.17, 2019
 * 
 * This class is Main file contains main stage.
 * .
 * CS213 Software Methodology Project 3: Photo.
 */
package photos.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Xiang Ao, Shijie Xu
 * @since April.17, 2019
 * main method
 */
public class Photos extends Application {
	
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static Stage primaryStage;
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void setPrimaryStage(Stage primaryStage) {
        Photos.primaryStage = primaryStage;
    }

	
	@Override
	public void start(Stage primaryStage ) {
		try {
			setPrimaryStage(primaryStage);
			Parent login = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
			primaryStage.setScene(new Scene(login));
			primaryStage.show();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
