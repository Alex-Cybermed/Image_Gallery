/**
 * @author Xiang Ao, Shijie Xu
 * @since April.10, 2019
 * 
 * This is User type.
 * .
 * 
 * CS213 Software Methodology Project 3: Photo Library.
 */
package photos.type;

import java.io.Serializable;

public class photo implements Serializable{
	private String URL;
	private String fileName;
	private String caption;
	private String dateString;
	private String tagsName1;
	private String tagsField1;
	private String tagsName2;
	private String tagsField2;

	/**
	 * Constructor of photo
	 * @param URL
	 * @param fileName
	 * @param caption
	 * @param dateString
	 * @param tagsName1
	 * @param tagsField1
	 * @param tagsName2
	 * @param tagsField2
	 */
	public photo(String URL, String fileName, String caption, String dateString, String tagsName1, String tagsField1, String tagsName2,
			String tagsField2) {
		this.URL = URL;
		this.fileName = fileName;
		this.caption = caption;
		this.dateString = dateString;
		this.tagsName1 = tagsName1;
		this.tagsField1 = tagsField1;
		this.tagsName2 = tagsName2;
		this.tagsField2 = tagsField2;
	}

	/**
	 * Get the URL of photo
	 * @return String URL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * Set the URL of the photo
	 * @param uRL
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * Get the caption of the photo
	 * @return String caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Set the Caption of the photo
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Get the date of String
	 * @return String dateString
	 */
	public String getDateString() {
		return dateString;
	}

	/**
	 * Set the Date of String 
	 * @param dateString
	 */
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	/**
	 * Get the tags name 1
	 * @return String tagsNames1
	 */
	public String getTagsName1() {
		return tagsName1;
	}

	/**
	 * Set the tags of name 1
	 * @param tagsName1
	 */
	public void setTagsName1(String tagsName1) {
		this.tagsName1 = tagsName1;
	}

	/**
	 * Get the Tags of field 1
	 * @return
	 */
	public String getTagsField1() {
		return tagsField1;
	}

	/**
	 * Set the tags of field 1
	 * @param tagsField1
	 */
	public void setTagsField1(String tagsField1) {
		this.tagsField1 = tagsField1;
	}

	/**
	 * Get the tags of name 2
	 * @return String tagsName2
	 */
	public String getTagsName2() {
		return tagsName2;
	}

	/**
	 * Set the tags of name 2
	 * @param tagsName2
	 */
	public void setTagsName2(String tagsName2) {
		this.tagsName2 = tagsName2;
	}

	/**
	 * Get the tags of field2
	 * @return String tagsField2
	 */
	public String getTagsField2() {
		return tagsField2;
	}

	/**
	 * Set the Tags of field 2
	 * @param tagsField2
	 */
	public void setTagsField2(String tagsField2) {
		this.tagsField2 = tagsField2;
	}

	/**
	 * Get the file name
	 * @return String fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set the file name
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
