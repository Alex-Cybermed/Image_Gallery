/**
 * @author Xiang Ao, Shijie Xu
 * @since April.10, 2019
 * 
 * This is Album type.
 * .
 * CS213 Software Methodology Project 3: Photo Library.
 */
package photos.type;

import java.io.Serializable;

public class Album implements Serializable {
	private String albumName;
	private String albumDate;
	private String photoNum;
	
	/**
	 * Album constructor
	 * @param albumName the name of Album
	 * @param albumdate the date of Album
	 * @param photoNum the photo number of Album
	 */
	public Album(String albumName, String albumdate, String photoNum) {
		this.albumName = albumName;
		this.albumDate = albumdate;
		this.photoNum = photoNum;
	}
	
	/**
	 * Get album name
	 * @return String albumName
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * Get album date
	 * @return String albumDate
	 */
	public String getAlbumDate() {
		return albumDate;
	}
	
	/**
	 * Get album number
	 * @return String photoNum
	 */
	public String getPhotoNum() {
		return photoNum;
	}
	
	/**
	 * Set the name of album
	 * @param albumnameString
	 */
	public void setAlbumName(String albumnameString) {
		albumName = albumnameString;
	}
	
	/**
	 * Set the Date of album
	 * @param inputdate
	 */
	public void setDate(String inputdate) {
		albumDate = inputdate;
	}
	
	/**
	 * Set the Number of album's photo
	 * @param inputPhotoNum
	 */
	public void setPhotoNum(String inputPhotoNum) {
		photoNum = inputPhotoNum;
	}
}
