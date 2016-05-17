/**
 * 
 */
package com.licensedmanga.licensedmangaapp.db;

/**
 * @author Wildux
 *
 */

public class Volume {
	private long id;
	private long id_serie;
	private int num;
	private String location;
	private int read;
	private String title;
	private String release_date;
	
	/**
	 * @return the release_date
	 */
	public String getRelease_date() {
		return release_date;
	}

	/**
	 * @param release_date the release_date to set
	 */
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}	
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return the id_serie
	 */
	public long getId_serie() {
		return id_serie;
	}
	
	/**
	 * @param id_serie the id_serie to set
	 */
	public void setId_serie(long id_serie) {
		this.id_serie = id_serie;
	}
	
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * @return the read
	 */
	public int getRead() {
		return read;
	}
	
	/**
	 * @param read the read to set
	 */
	public void setRead(int read) {
		this.read = read;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param set the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Volume [id=" + id + ", id_serie=" + id_serie + ", num=" + num + ", location=" + location + ", read="
				+ read + "]";
	}

	
	

}
