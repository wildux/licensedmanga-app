/**
 * 
 */
package com.licensedmanga.licensedmangaapp.db;

/**
 * @author joan
 *
 */
public class SerieCol {
	
	private long id;	
	private String title;
	private int own;
	private int total_vol;
	
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the own
	 */
	public int getOwn() {
		return own;
	}
	/**
	 * @param own the own to set
	 */
	public void setOwn(int own) {
		this.own = own;
	}
	/**
	 * @return the total_vol
	 */
	public int getTotal_vol() {
		return total_vol;
	}
	/**
	 * @param total_vol the total_vol to set
	 */
	public void setTotal_vol(int total_vol) {
		this.total_vol = total_vol;
	}
	

}
