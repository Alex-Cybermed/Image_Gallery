/**
 * @author Xiang Ao, Shijie Xu
 * @since April.10, 2019
 * 
 * This is photo type.
 * .
 * CS213 Software Methodology Project 3: Photo Library.
 */
package photos.type;

import java.io.Serializable;

public class User implements Serializable{
	private String username;
	private String date;
	private String albumNum;
	
	/**
	 * Constructor of User
	 * @param username
	 * @param date
	 * @param albumNum
	 */
	public User(String username, String date, String albumNum) {
		this.username = username;
		this.date = date;
		this.albumNum = albumNum;
	}
	
	/**
	 * Get the username of User
	 * @return String username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Get the date of User create
	 * @return String date
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Get the album number of User
	 * @return String albumNum
	 */
	public String getAlbumNum() {
		return albumNum;
	}
	
	/**
	 * Set the username of User
	 * @param usernameString
	 */
	public void setUsername(String usernameString) {
		username = usernameString;
	}
	
	/**
	 * Set the Date of create user
	 * @param inputdate
	 */
	public void setDate(String inputdate) {
		date = inputdate;
	}
	
	/**
	 * Set the album number of User
	 * @param inputAlbumNum
	 */
	public void setAlbumNum(String inputAlbumNum) {
		albumNum = inputAlbumNum;
	}
	
	@Override
	/**
	 * toString the username
	 */
	public String toString() {
		return username;
	}
}
