/**
 * @author Xiang Ao, Shijie Xu
 * @since April.10, 2019
 * 
 * This is controller of Login page.
 * .
 * CS213 Software Methodology Project 3: Photo Library.
 */
package photos.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import photos.app.Photos;
import photos.type.Album;
import photos.type.User;
import photos.type.photo;

public class Login {
	public static String usernameInput;
	static ObservableList<User> userList = FXCollections.observableArrayList();
	static ObservableList<Album> albumList = FXCollections.observableArrayList();
	static ObservableList<photo> photoList = FXCollections.observableArrayList();

	@FXML
	Button login;
	@FXML
	Button quit;
	@FXML
	Label errorLabel;
	@FXML
	javafx.scene.control.TextField username;

	/**
	 *  Get the userList
	 * @return ObservableList
	 */
	public static ObservableList<User> getUserList() {
		return userList;
	}
	
	/**
	 *  Get the albumList
	 * @return ObservableList
	 */
	public static ObservableList<Album> getAlbumList() {
		return albumList;
	}
	
	/**
	 *  Get the photoList
	 * @return ObservableList
	 */
	public static ObservableList<photo> getPhotoList() {
		return photoList;
	}
	
	/**
	 * Check if the list contain the username
	 * @param username
	 * @return
	 */
	public static boolean checkContain(String username) {
		if (userList.isEmpty()) {
			return false;
		} else {
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getUsername().equals(username)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Read the user list out
	 * @throws Exception
	 */
	public static void readUserFile() throws Exception {
		FileInputStream fis = new FileInputStream("userList");
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<User> ulist = (ArrayList) ois.readObject();
		userList.addAll(ulist);
		ois.close();
		fis.close();
		return;
	}

	/**
	 * Read the album list out
	 * @throws Exception
	 */
	public static void readAlbumFile(String user) throws Exception {
		FileInputStream fis = new FileInputStream(user + "_AlbumList");
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<Album> alist = (ArrayList) ois.readObject();
		albumList.addAll(alist);
		ois.close();
		fis.close();
		return;
	}

	/**
	 * Read the photo list out
	 * @throws Exception
	 */
	public static void readPhotoFile(String user, String album) throws Exception {
		FileInputStream fis = new FileInputStream(
				user + "_" + album + "_PhotoList");
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<photo> plist = (ArrayList) ois.readObject();
		photoList.addAll(plist);
		ois.close();
		fis.close();
		return;
	}
	
	/**
	 * Serializable the user list
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean userSerImp() throws Exception {
		List<User> userSer = new ArrayList<User>(userList);
		FileOutputStream userfis = new FileOutputStream("userList");
		ObjectOutputStream userois = new ObjectOutputStream(userfis);
		userois.writeObject(userSer);
		userois.close();
		userfis.close();
		return true;
	}

	/**
	 * Serializable the album list
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean albumSerImp(String user) throws Exception {
		List<Album> albumSer = new ArrayList<Album>(albumList);
		FileOutputStream albumfis = new FileOutputStream(user + "_AlbumList");
		ObjectOutputStream albumois = new ObjectOutputStream(albumfis);
		albumois.writeObject(albumSer);
		albumois.close();
		albumfis.close();
		return true;
	}

	/**
	 * Serializable the photo list
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean photoSerImp(String user, String album) throws Exception {
		List<photo> photoSer = new ArrayList<photo>(photoList);
		FileOutputStream photofis = new FileOutputStream(
				user + "_" + album + "_PhotoList");
		ObjectOutputStream photoois = new ObjectOutputStream(photofis);
		photoois.writeObject(photoSer);
		photoois.close();
		photofis.close();
		return true;
	}
	
	/**
	 * Delete the file with special partial filename
	 * @param type
	 * @param partialFileName
	 */
	public static void specialDelete(int type, String partialFileName) {
		String path = System.getProperty("user.dir");
		File folder = new File(path);
		File[] files = folder.listFiles();
		for(File f : files) {
			String fileName = f.toString();
			if(type == 1) {//delete user
				if(fileName.matches(partialFileName+".*AlbumList")) {
					if(f.isFile()) {
						f.delete();
					}
				}
				if(fileName.matches(partialFileName+".*PhotoList")){
					if(f.isFile()) {
						f.delete();
					}
				}
			}else if(type == 2) {
				if(fileName.matches(".*"+partialFileName+".*PhotoList")) {
					if(f.isFile()) {
						f.delete();
					}
				}
			}
		}
	}
	
	/**
	 * Click login buttob
	 * @throws Exception
	 */
	@FXML
	public void loginButtonAction() throws Exception {
		File uSI = new File("userList");
		if (!uSI.exists()) {
			userSerImp();
//			appStart();
		}
		readUserFile();
		boolean usernameExist = false;
		usernameInput = username.getText();
		if (usernameInput.equals("admin")) {
			Stage currentStage = (Stage) login.getScene().getWindow();
			currentStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
			Stage adminStage = new Stage();
			adminStage.setScene(new Scene(root));
			adminStage.setOnCloseRequest(event -> {
					try {
						userSerImp();
					} catch (Exception e) {
						e.printStackTrace();
					}
			});
			adminStage.show();
			Photos.setPrimaryStage(adminStage);

//		} else if(usernameInput.equals("stock")) {
//			Stage currentStage = (Stage) login.getScene().getWindow();
//			currentStage.close();
//			Parent root = FXMLLoader.load(getClass().getResource("/fxml/stock.fxml"));
//			Stage adminStage = new Stage();
//			adminStage.setScene(new Scene(root));
//			adminStage.setOnCloseRequest(event -> {
//					try {
//						albumSerImp("stock");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//			});
//			adminStage.show();
//			Photos.setPrimaryStage(adminStage);
		}else {
			if (userList == null) {
				System.out.println("userList is empty");
				usernameExist = false;
			} else {
				usernameExist = Login.checkContain(usernameInput);
			}
			if (usernameExist) {
				System.out.println("loginRead:"+usernameInput);
				readAlbumFile(usernameInput);
				Stage currentStage = (Stage) login.getScene().getWindow();
				currentStage.close();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml/UserIni.fxml"));
				Stage userStage = new Stage();
				userStage.setScene(new Scene(root));
				userStage.setOnCloseRequest(event -> {
					try {
						albumSerImp(usernameInput);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				userStage.show();
				Photos.setPrimaryStage(userStage);
			} else {
				errorLabel.setVisible(true);
			}
		}
	}

	/**
	 * Click quit button action
	 */
	@FXML
	public void quitButtonAction() {
		Stage currentStage = (Stage) quit.getScene().getWindow();
		currentStage.close();
	}
	
//	public void appStart() throws IOException {
//		Scanner sc = new Scanner(new File("src/photos/list/stockList.txt"));
//		sc.useDelimiter(System.getProperty("line.separator"));
//
//		List<String> photoNames = new ArrayList<String>();
//		String path = System.getProperty("user.dir");
//
//		
//		while(sc.hasNext()) {
//			photoNames.add(sc.next());
//		}
//		
//		User stockuser = new User("stock", "4/19/2019", "1");
//		Album album = new Album("stock", "4/19/2019", "6");
//		
//		for(int index = 0; index < photoNames.size(); index++){
//			String fn = photoNames.get(index);
//			
//			photo photos = new photo(fn, path+"/"+fn, "", "4/19/2019", "","","","");
//			
//			switch(index) {
//			case 0:
//				photos.setCaption("captionBB");
//				break;
//			case 1:
//				photos.setCaption("captionNDDP");
//				break;
//			case 2:
//				photos.setCaption("captionP");
//				break;
//			case 3:
//				photos.setCaption("captionTM");
//				break;
//			case 4:
//				photos.setCaption("captionTSOL");
//				break;
//			case 5:
//				photos.setCaption("captionTGW");
//				break;
//			}
//
//		}
//		
//		sc.close();
//	}
}



//Scanner sc = new Scanner(new File("src/photos/list/userList.txt"));
//sc.useDelimiter(System.getProperty("line.separator"));
//while (sc.hasNext()) {
//	User node = new User(sc.next(), sc.next(), sc.next());
//	Login.userList.add(node);
//}
//sc.close();

//public static void readAlbumFile() throws FileNotFoundException {
//	Scanner sc = new Scanner(new File(Login.usernameInput + "_AlbumList.txt"));
//	sc.useDelimiter(System.getProperty("line.separator"));
//	while (sc.hasNext()) {
//		Album node = new Album(sc.next(), sc.next(), sc.next());
//		Login.albumList.add(node);
//	}
//	sc.close();
//}

//public static void readPhotoFile() throws FileNotFoundException {
//	Scanner sc = new Scanner(
//			new File(Login.usernameInput + "_" + UserIniController.selectAlbumName + "_PhotoList.txt"));
//	sc.useDelimiter(System.getProperty("line.separator"));
//	while (sc.hasNext()) {
//		photo node = new photo(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next(),
//				sc.next());
//		Login.photoList.add(node);
//	}
//	sc.close();
//}

//public static void saveFileUser(List<User> l) throws IOException {
//	File file = new File("src/photos/list/userList.txt");
//	FileWriter fw = new FileWriter(file);
//	for (User userNode : l) {
//		fw.write(userNode.getUsername() + "\n" + userNode.getDate() + "\n" + userNode.getAlbumNum() + "\n");
//	}
//	fw.close();
//}
//
//public static void saveFileAlbum(List<Album> l) throws IOException {
//	File file = new File(Login.usernameInput + "_AlbumList.txt");
//	FileWriter fw = new FileWriter(file);
//	for (Album userNode : l) {
//		fw.write(userNode.getAlbumName() + "\n" + userNode.getAlbumDate() + "\n" + userNode.getPhotoNum() + "\n");
//	}
//	fw.close();
//}
//
//public static void saveFilePhoto(List<photo> l) throws IOException {
//	File file = new File(Login.usernameInput + "_" + UserIniController.selectAlbumName + "_PhotoList.txt");
//	FileWriter fw = new FileWriter(file);
//	for (photo userNode : l) {
//		fw.write(userNode.getURL() + "\n" + userNode.getFileName() + "\n" + userNode.getCaption() + "\n"
//				+ userNode.getDateString() + "\n" + userNode.getTagsName1() + "\n" + userNode.getTagsName2() + "\n"
//				+ userNode.getTagsField1() + "\n" + userNode.getTagsField2() + "\n");
//	}
//	fw.close();
//}
